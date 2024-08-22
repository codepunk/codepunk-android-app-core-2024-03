pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.mozilla.org/maven2")
    }
}

rootProject.name = "Skeleton"
include(":app")

// This line is needed to prevent "Unable to make progress running work" build error
gradle.startParameter.excludedTaskNames.addAll(listOf(":buildSrc:testClasses"))
