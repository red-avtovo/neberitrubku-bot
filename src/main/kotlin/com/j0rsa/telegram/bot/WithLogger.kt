package com.j0rsa.telegram.bot

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class WithLogger {
    val logger: Logger = logger()
    private fun logger() = LoggerFactory.getLogger(this::class.qualifiedName!! + " w/Logging")
}
