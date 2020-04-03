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