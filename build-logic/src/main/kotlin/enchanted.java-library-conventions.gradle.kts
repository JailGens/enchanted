import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByName

plugins {
    `java-library`
    `maven-publish`
    jacoco
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "enchanted"

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "sparky"
            url = if (project.version.toString().endsWith("-SNAPSHOT")) {
                uri("https://repo.sparky983.me/snapshots")
            } else {
                uri("https://repo.sparky983.me/releases")
            }
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

tasks.getByName<JavaCompile>("compileTestJava") {
    options.compilerArgs.add("-parameters")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
