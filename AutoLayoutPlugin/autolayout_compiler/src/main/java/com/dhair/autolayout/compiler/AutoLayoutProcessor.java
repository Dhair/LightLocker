package com.dhair.autolayout.compiler;

import com.dhair.autolayout.api.AutoLayout;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AutoLayoutProcessor extends AbstractProcessor {

    private static final String PACKAGE_NAME = "com.dhair.autolayout";
    private static final String VIEW_CREATOR_IMPL = "ExtViewCreator";
    private static final String VIEW_CREATOR = "com.dhair.autolayout.ViewCreator";

    private Messager mMessager;

    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();

        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(AutoLayout.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        List<ParsedInfo> infos = new ArrayList<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(AutoLayout.class)) {
            TypeElement typeElement = (TypeElement) element;

            String name = element.getSimpleName().toString();
            String canonicalName = typeElement.getQualifiedName().toString();

            String superCanonicalName = ((TypeElement) element).getSuperclass().toString();
            String superName = superCanonicalName.substring(superCanonicalName.lastIndexOf('.') + 1);

            if (superCanonicalName.startsWith("android.widget.")) {
                superCanonicalName = superCanonicalName.substring(superCanonicalName.lastIndexOf('.') + 1);
            }

            ParsedInfo info = new ParsedInfo();
            info.name = name;
            info.packageName = getPackageName(typeElement);
            info.canonicalName = canonicalName;
            info.superName = superName;
            info.superCanonicalName = superCanonicalName;
            infos.add(info);

//            TypeName type = TypeName.get(elementType);
//
//
//            info("kind:" + element.getKind());
//            info("typekind:" + elementType.getKind());
//            info("class:" + element.getClass());
//
//            info("super:" + typeElement.getSuperclass().toString());
//            info("superName:" + superName);
//            info("enclosingElement:" + typeElement);
//            String targetType = typeElement.getQualifiedName().toString();
//            String classPackage = getPackageName(typeElement);
//            String className = getClassName(typeElement, classPackage) + "$$ViewBinder";
//
//            info("targetType:" + targetType);
//            info("classPackage:" + classPackage);
//            info("className:" + className);

        }

        if (!infos.isEmpty()) {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("create")
                    .addAnnotation(Override.class)
                    .addModifiers(PUBLIC)
                    .addParameter(String.class, "name")
//                    .addParameter(Context.class, "context")
//                    .addParameter(AttributeSet.class, "attrs")
//                    .returns(View.class)
                    .addParameter(ClassName.get("android.content", "Context"), "context")
                    .addParameter(ClassName.get("android.util", "AttributeSet"), "attrs")
                    .returns(ClassName.get("android.view", "View"))
                    .beginControlFlow("switch (name)");

            for (ParsedInfo info : infos) {
//                info("name:" + info.name);
//                info("pk:" + info.packageName);
//                info("canonicalName:" + info.canonicalName);
//                info("superName:" + info.superName);
//                info("superCanonicalName:" + info.superCanonicalName);

                ClassName className = ClassName.get(info.packageName, info.name);
//                info("className:" + className);
                methodBuilder.addStatement("case $S: return new $T(context, attrs)", info.superCanonicalName, className);
            }
            methodBuilder.endControlFlow();
            methodBuilder.addStatement("return null");

            TypeSpec.Builder result = TypeSpec.classBuilder(VIEW_CREATOR_IMPL)
                    .addSuperinterface(ClassName.bestGuess(VIEW_CREATOR))
                    .addModifiers(PUBLIC)
                    .addMethod(methodBuilder.build());

            try {
                JavaFile.builder(PACKAGE_NAME, result.build())
                        .addFileComment("Generated code from AutoLayout. Do not modify!")
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                error("Unable to write %s.", VIEW_CREATOR_IMPL);
            }
        }

        return true;
    }

    private void info(String s) {
        mMessager.printMessage(NOTE, s);
    }

    private void error(String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mMessager.printMessage(ERROR, message);
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    static class ParsedInfo {
        String name;
        String packageName;
        String canonicalName;

        String superName;
        String superCanonicalName;
    }
}
