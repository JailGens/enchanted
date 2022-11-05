rootProject.name = "enchanted"

dependencyResolutionManagement {
    includeBuild("build-logic")
}


sequenceOf(
        "enchanted-api"
).forEach { include(it) }
