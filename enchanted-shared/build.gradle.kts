plugins {
    id("enchanted.java-library-conventions")
}

repositories {
    maven("https://repo.sparky983.me/releases")
}

dependencies {
    api(project(":enchanted-api"))
    api(libs.mirror)
    testImplementation(libs.mockito)
}
