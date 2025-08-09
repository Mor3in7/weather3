pluginManagement {
    repositories {

        gradlePluginPortal()
        google()
        mavenCentral()
        maven(
            "https://maven.myket.ir"
        )
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(
        "https://maven.myket.ir"
    )
        google()
        mavenCentral()

    }}
include(":app")
