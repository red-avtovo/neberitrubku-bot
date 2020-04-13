package com.j0rsa.telegram.bot

import org.jsoup.Jsoup

object InfoFinder : WithLogger() {
    fun getPhoneInfo(message: String): String {
        return validPhone(message.trim()) {
            fetchPhoneCategories(it) ?: "По телефону Ничего не найдено!"
        }
    }

    private fun validPhone(message: String, function: (String) -> String): String =
        if (message.startsWith("+7") || message.startsWith("8")) {
            val numbers = message
                .replace("+7", "8")
                .replace("[^0-9]".toRegex(), "")
            if (numbers.length == 11) {
                function(numbers)
            } else {
                "Неверная длина номера телефона. Телефон должен содержать 11 цифр"
            }
        } else {
            "Неверный формат номера телефона"
        }

    fun fetchPhoneCategories(validPhone: String): String? {
        val jsoup = Jsoup.connect(Config.app.url + validPhone).get()
        return jsoup.select(".categories")
            .firstOrNull()?.let {
                it.getElementsByTag("li")
                    .joinToString("\n") { it.text() }
            }
    }
}