import io.hexlet.maconi.JacocoCoverageExtension
import io.hexlet.maconi.configureJacoco
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.testing.jacoco.plugins.JacocoPlugin

abstract class JvmLibraryJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<JacocoPlugin>()

            val extension = extensions.create<JacocoCoverageExtension>("jacocoCoverage")
            extension.moduleName.convention(name)
            configureJacoco(extension)
        }
    }
}
