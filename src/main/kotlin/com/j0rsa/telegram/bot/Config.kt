package com.j0rsa.telegram.bot

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract

object Config {
    val app: AppConfig = ConfigFactory.load().extract("app")
}

data class AppConfig(
    val token: String,
    val url: String,
    val sdkLoggingLevel: String
)