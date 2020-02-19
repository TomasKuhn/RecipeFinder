buildscript {

    repositories {
        google()
        jcenter()
        maven(url = "https://www.jitpack.io")
    }
    dependencies {
        classpath(Dependencies.BuildPlugins.gradlePlugin)
        classpath(Dependencies.BuildPlugins.kotlinPlugin)
        classpath(Dependencies.BuildPlugins.safeArgsPlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://www.jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}