plugins {
    id("enchanted.java-library-conventions")
}

repositories {
    maven("https://repo.jailgens.net/releases")
}

dependencies {
    api(project(":enchanted-api"))
    api(libs.mirror)
    testImplementation(libs.mockito)
}
