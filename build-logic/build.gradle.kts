plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files(libs::class.java.superclass.protectionDomain.codeSource.location))
}
