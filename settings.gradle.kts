rootProject.name = "enchanted"

dependencyResolutionManagement {
    includeBuild("build-logic")
}

sequenceOf(
        "enchanted-api",
        "enchanted-shared"
).forEach { include(it) }
