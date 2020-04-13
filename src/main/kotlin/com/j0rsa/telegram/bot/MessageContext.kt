package com.j0rsa.telegram.bot

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Chat
import me.ivmg.telegram.entities.ParseMode

data class MessageContext(
    val bot: Bot,
    val chat: Chat
) {
    fun send(text: String) {
        bot.sendMessage(chat.id, text, parseMode = ParseMode.HTML)
    }
}