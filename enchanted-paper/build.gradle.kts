plugins {
    id("enchanted.java-library-conventions")
}

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.sparky983.me/releases")
}

dependencies {
    api(project(":enchanted-api"))
    implementation(project(":enchanted-shared"))
    implementation(libs.paper)
    // implementation to avoid exposing a later version of paper API than the plugin is using
    testImplementation(libs.mockito)
}
