plugins {
    id("enchanted.java-library-conventions")
}

java {
    withSourcesJar()
}

repositories {
    maven("https://repo.sparky983.me/releases")
}

dependencies {
    api(project(":enchanted-api"))
    api(libs.mirror)
    testImplementation(libs.mockito)
}
