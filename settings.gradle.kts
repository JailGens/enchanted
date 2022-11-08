rootProject.name = "enchanted"

dependencyResolutionManagement {
    includeBuild("build-logic")
}

sequenceOf(
        "enchanted-api",
        "enchanted-shared",
        "enchanted-paper"
).forEach { include(it) }
