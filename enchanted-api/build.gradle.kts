plugins {
    id("enchanted.java-library-conventions")
}

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    testImplementation(libs.mockito)
}
