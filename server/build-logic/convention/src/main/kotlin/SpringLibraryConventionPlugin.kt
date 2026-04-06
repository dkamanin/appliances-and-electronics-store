import io.hexlet.maconi.configureSpring
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

abstract class SpringLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("maconi.jvm.library") {
                apply(plugin = "org.springframework.boot")

                configureSpring()
            }
        }
    }
}
