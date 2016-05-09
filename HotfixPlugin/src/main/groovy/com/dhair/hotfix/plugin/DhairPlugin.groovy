package com.dhair.hotfix.plugin

import com.dhair.hotfix.plugin.util.*
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

class DhairPlugin implements Plugin<Project> {
    HashSet<String> includePackage
    HashSet<String> excludeClass
    def debugOn
    def patchList = []
    def beforeDexTasks = []
    private static final String DHAIR_DIR = "DhairDir"
    private static final String DHAIR_PATCHES = "DhairPatches"

    private static final String MAPPING_TXT = "mapping.txt"
    private static final String HASH_TXT = "hash.txt"

    private static final String DEBUG = "debug"


    @Override
    void apply(Project project) {

        project.extensions.create("dhair", DhairExtension, project)



        project.afterEvaluate {
            def extension = project.extensions.findByName("dhair") as DhairExtension
            includePackage = extension.includePackage
            excludeClass = extension.excludeClass
            debugOn = extension.debugOn

            project.android.applicationVariants.each { variant ->

                if (!variant.name.contains(DEBUG) || (variant.name.contains(DEBUG) && debugOn)) {

                    Map hashMap
                    File dhairDir
                    File patchDir

                    def preDexTask = project.tasks.findByName("preDex${variant.name.capitalize()}")
                    def dexTask = project.tasks.findByName("dex${variant.name.capitalize()}")
                    def proguardTask = project.tasks.findByName("proguard${variant.name.capitalize()}")

                    def processManifestTask = project.tasks.findByName("process${variant.name.capitalize()}Manifest")
                    def manifestFile = processManifestTask.outputs.files.files[0]

                    def oldDhairDir = DhairFileUtils.getFileFromProperty(project, DHAIR_DIR)
                    if (oldDhairDir) {
                        def mappingFile = DhairFileUtils.getVariantFile(oldDhairDir, variant, MAPPING_TXT)
                        DhairAndroidUtils.applyMapping(proguardTask, mappingFile)
                    }
                    if (oldDhairDir) {
                        def hashFile = DhairFileUtils.getVariantFile(oldDhairDir, variant, HASH_TXT)
                        hashMap = DhairMapUtils.parseMap(hashFile)
                    }

                    def dirName = variant.dirName
                    dhairDir = new File("${project.buildDir}/outputs/dhair")
                    def outputDir = new File("${dhairDir}/${dirName}")
                    def hashFile = new File(outputDir, "hash.txt")

                    Closure dhairPrepareClosure = {
                        def applicationName = DhairAndroidUtils.getApplication(manifestFile)
                        if (applicationName != null) {
                            excludeClass.add(applicationName)
                        }

                        outputDir.mkdirs()
                        if (!hashFile.exists()) {
                            hashFile.createNewFile()
                        }

                        if (oldDhairDir) {
                            patchDir = new File("${dhairDir}/${dirName}/patch")
                            patchDir.mkdirs()
                            patchList.add(patchDir)
                        }
                    }

                    def dhairPatch = "dhair${variant.name.capitalize()}Patch"
                    project.task(dhairPatch) << {
                        if (patchDir) {
                            DhairAndroidUtils.dex(project, patchDir)
                        }
                    }
                    def dhairPatchTask = project.tasks[dhairPatch]

                    Closure copyMappingClosure = {
                        if (proguardTask) {
                            def mapFile = new File("${project.buildDir}/outputs/mapping/${variant.dirName}/mapping.txt")
                            def newMapFile = new File("${dhairDir}/${variant.dirName}/mapping.txt");
                            FileUtils.copyFile(mapFile, newMapFile)
                        }
                    }

                    if (preDexTask) {
                        def dhairJarBeforePreDex = "dhairJarBeforePreDex${variant.name.capitalize()}"
                        project.task(dhairJarBeforePreDex) << {
                            Set<File> inputFiles = preDexTask.inputs.files.files
                            inputFiles.each { inputFile ->
                                def path = inputFile.absolutePath
                                if (DhairProcessor.shouldProcessPreDexJar(path)) {
                                    DhairProcessor.processJar(hashFile, inputFile, patchDir, hashMap, includePackage, excludeClass)
                                }
                            }
                        }
                        def dhairJarBeforePreDexTask = project.tasks[dhairJarBeforePreDex]
                        dhairJarBeforePreDexTask.dependsOn preDexTask.taskDependencies.getDependencies(preDexTask)
                        preDexTask.dependsOn dhairJarBeforePreDexTask

                        dhairJarBeforePreDexTask.doFirst(dhairPrepareClosure)

                        def dhairClassBeforeDex = "dhairClassBeforeDex${variant.name.capitalize()}"
                        project.task(dhairClassBeforeDex) << {
                            Set<File> inputFiles = dexTask.inputs.files.files
                            inputFiles.each { inputFile ->
                                def path = inputFile.absolutePath
                                if (path.endsWith(".class") && !path.contains("/R\$") && !path.endsWith("/R.class") && !path.endsWith("/BuildConfig.class")) {
                                    if (DhairSetUtils.isIncluded(path, includePackage)) {
                                        if (!DhairSetUtils.isExcluded(path, excludeClass)) {
                                            def bytes = DhairProcessor.processClass(inputFile)
                                            path = path.split("${dirName}/")[1]
                                            def hash = DigestUtils.shaHex(bytes)
                                            hashFile.append(DhairMapUtils.format(path, hash))

                                            if (DhairMapUtils.notSame(hashMap, path, hash)) {
                                                DhairFileUtils.copyBytesToFile(inputFile.bytes, DhairFileUtils.touchFile(patchDir, path))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        def dhairClassBeforeDexTask = project.tasks[dhairClassBeforeDex]
                        dhairClassBeforeDexTask.dependsOn dexTask.taskDependencies.getDependencies(dexTask)
                        dexTask.dependsOn dhairClassBeforeDexTask

                        dhairClassBeforeDexTask.doLast(copyMappingClosure)

                        dhairPatchTask.dependsOn dhairClassBeforeDexTask
                        beforeDexTasks.add(dhairClassBeforeDexTask)
                    } else {
                        def dhairJarBeforeDex = "dhairJarBeforeDex${variant.name.capitalize()}"
                        project.task(dhairJarBeforeDex) << {
                            Set<File> inputFiles = dexTask.inputs.files.files
                            inputFiles.each { inputFile ->
                                def path = inputFile.absolutePath
                                if (path.endsWith(".jar")) {
                                    DhairProcessor.processJar(hashFile, inputFile, patchDir, hashMap, includePackage, excludeClass)
                                }
                            }
                        }
                        def dhairJarBeforeDexTask = project.tasks[dhairJarBeforeDex]
                        dhairJarBeforeDexTask.dependsOn dexTask.taskDependencies.getDependencies(dexTask)
                        dexTask.dependsOn  dhairJarBeforeDexTask

                        dhairJarBeforeDexTask.doFirst(dhairPrepareClosure)
                        dhairJarBeforeDexTask.doLast(copyMappingClosure)

                        dhairPatchTask.dependsOn  dhairJarBeforeDexTask
                        beforeDexTasks.add( dhairJarBeforeDexTask)
                    }

                }
            }

            project.task(DHAIR_PATCHES) << {
                patchList.each { patchDir ->
                    DhairAndroidUtils.dex(project, patchDir)
                }
            }
            beforeDexTasks.each {
                project.tasks[DHAIR_PATCHES].dependsOn it
            }
        }
    }
}


