plugins {
    id("enchanted.java-conventions")
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
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
