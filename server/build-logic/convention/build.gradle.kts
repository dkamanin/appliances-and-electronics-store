plugins {
    `kotlin-dsl`
}

group = "io.hexlet.maconi.buildlogic"

dependencies {
    implementation(libs.spring.plugin)
    compileOnly(libs.spotless)
}

gradlePlugin {
    plugins {
        register("jvmLibrary") {
            id = libs.plugins.maconi.jvm.library.asProvider().get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmLibraryJacoco") {
            id = libs.plugins.maconi.jvm.library.jacoco.get().pluginId
            implementationClass = "JvmLibraryJacocoConventionPlugin"
        }
        register("jvmLibraryTest") {
            id = libs.plugins.maconi.jvm.library.test.get().pluginId
            implementationClass = "JvmLibraryTestConventionPlugin"
        }
        register("springLibrary") {
            id = libs.plugins.maconi.spring.library.asProvider().get().pluginId
            implementationClass = "SpringLibraryConventionPlugin"
        }
        register("springLibraryTest") {
            id = libs.plugins.maconi.spring.library.test.get().pluginId
            implementationClass = "SpringLibraryTestConventionPlugin"
        }
        register("root") {
            id = libs.plugins.maconi.root.get().pluginId
            implementationClass = "RootPlugin"
        }
    }
}
