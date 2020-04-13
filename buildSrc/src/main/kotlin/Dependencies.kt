object App {
    const val compileVersion = "1.8"
}

object Plugins {
    const val gitProperties = "com.gorylenko.gradle-git-properties"
    const val docker = "com.palantir.docker"
    const val shadow = "com.github.johnrengelman.shadow"
}

object Versions {
    const val kotlin = "1.3.70"
    const val telegramApi = "4.6.0"
    const val config4k = "0.4.2"
    const val slf4j = "1.7.25"
    const val logback = "1.2.3"
    const val jsoup = "1.13.1"
    /* plugins */
    const val gitProperties = "1.4.17"
    const val docker = "0.24.0"
    const val shadow = "5.2.0"

    const val janino = "3.0.6"
    /* test */
    const val junit = "4.12"
    const val koTest = "4.0.1"
}

object Libs {
    const val telegramApi = "io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:${Versions.telegramApi}"
    const val config4k = "io.github.config4k:config4k:${Versions.config4k}"
    const val slf4jApi = "org.slf4j:slf4j-api:${Versions.slf4j}"
    const val logbackClassic = "ch.qos.logback:logback-classic:${Versions.logback}"
    const val logbackCore = "ch.qos.logback:logback-core:${Versions.logback}"
    const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
    // logback conditions
    const val janino = "org.codehaus.janino:janino:${Versions.janino}"
}


object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val koTest = "io.kotest:kotest-runner-junit5-jvm:${Versions.koTest}"
}