plugins {
    id("enchanted.java-library-conventions")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.jailgens.net/releases")
}

dependencies {
    api(project(":enchanted-api"))
    implementation(project(":enchanted-shared"))
    implementation(libs.paper)
    // implementation to avoid exposing a later version of paper API than the plugin is using
    testImplementation(libs.mockito)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
