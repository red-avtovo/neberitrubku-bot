import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version Versions.kotlin
    id(Plugins.gitProperties) version Versions.gitProperties
    id(Plugins.docker) version Versions.docker
    id(Plugins.shadow) version Versions.shadow
}

group = "com.j0rsa.telegram"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(Libs.telegramApi) {
        exclude("com.squareup.okhttp3","logging-interceptor")
    }
    // workaround for https://github.com/kotlin-telegram-bot/kotlin-telegram-bot/pull/50
    implementation("com.squareup.okhttp3:logging-interceptor:4.4.0")

    implementation(Libs.config4k)
    implementation(Libs.jsoup)
    implementation(Libs.slf4jApi)
    implementation(Libs.logbackClassic)
    implementation(Libs.logbackCore)

    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.koTest)

    runtimeClasspath(Libs.janino)
    testRuntimeClasspath(Libs.janino)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = App.compileVersion
        kotlinOptions.freeCompilerArgs = listOf("-XXLanguage:+InlineClasses", "-Xopt-in=kotlin.RequiresOptIn")
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = App.compileVersion
        kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }

    named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        @Suppress("UnstableApiUsage")
        manifest {
            attributes(mapOf("Main-Class" to "com.j0rsa.telegram.bot.App"))
        }
    }

    test {
        useJUnitPlatform()
    }

    docker {
        dependsOn(test.get())
    }
}

val hash = Runtime.getRuntime().exec("git rev-parse --short=6 HEAD").inputStream.reader().use { it.readText() }.trim()
val projectTag = hash
val baseDockerName = "j0rsa/${project.name}"
val taggedDockerName = "$baseDockerName:$projectTag"

val baseDockerFile = file("$projectDir/src/main/docker/Dockerfile")
docker {
    val shadowJar: ShadowJar by tasks
    name = taggedDockerName
    setDockerfile(baseDockerFile)
    tag("DockerTag", "$baseDockerName:$projectTag")
    @Suppress("UnstableApiUsage")
    buildArgs(mapOf("JAR_FILE" to shadowJar.archiveFileName.get()))
    files(shadowJar.outputs)
}
