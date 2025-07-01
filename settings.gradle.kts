pluginManagement {
    repositories {
        maven(
        "https://maven.myket.ir"
    )
        gradlePluginPortal()
        google()
        mavenCentral()

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
