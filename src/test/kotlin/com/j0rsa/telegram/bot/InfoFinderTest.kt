package com.j0rsa.telegram.bot

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class InfoFinderTest: ShouldSpec() {
    init {
        should("have categories") {
            val phone = "89207681427"
            InfoFinder.fetchPhoneCategories(phone) shouldNotBe null
        }
    }
}