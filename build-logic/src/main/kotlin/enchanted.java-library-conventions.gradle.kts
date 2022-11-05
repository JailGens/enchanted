import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByName

plugins {
    `java-library`
}

val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")

    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.junit.params)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
