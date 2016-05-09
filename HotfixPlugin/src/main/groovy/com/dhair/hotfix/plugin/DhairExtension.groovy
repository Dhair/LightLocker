package com.dhair.hotfix.plugin

import org.gradle.api.Project

class DhairExtension {
    HashSet<String> includePackage = []
    HashSet<String> excludeClass = []
    boolean debugOn = true

    DhairExtension(Project project) {
    }
}
