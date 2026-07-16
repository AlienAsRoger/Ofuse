// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.detekt) apply false
}

val detektVersionCatalog = extensions.getByType<org.gradle.api.artifacts.VersionCatalogsExtension>()
    .named("libs")
val detektVersion = detektVersionCatalog.findVersion("detekt").get().requiredVersion

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom(rootProject.file("config/detekt/detekt.yml"))
        source.setFrom(files("src/main/kotlin", "src/main/java"))
    }

    dependencies {
        "detektPlugins"("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    }
}