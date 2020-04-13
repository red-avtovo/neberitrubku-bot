package com.j0rsa.telegram.bot

import me.ivmg.telegram.bot
import me.ivmg.telegram.dispatch
import me.ivmg.telegram.dispatcher.inlineQuery
import me.ivmg.telegram.dispatcher.message
import me.ivmg.telegram.entities.ParseMode
import me.ivmg.telegram.entities.inlinequeryresults.InlineQueryResult
import me.ivmg.telegram.entities.inlinequeryresults.InputMessageContent
import me.ivmg.telegram.extensions.filters.Filter.Command
import me.ivmg.telegram.extensions.filters.Filter.Text
import okhttp3.logging.HttpLoggingInterceptor

class App : WithLogger() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            App().run()
        }
    }

    private fun run() {
        bot {
            token = Config.app.token
            logLevel = HttpLoggingInterceptor.Level.valueOf(Config.app.sdkLoggingLevel)
            dispatch {
                inlineQuery { bot, inline ->
                    val queryText = inline.query
                    if (queryText.isBlank() or queryText.isEmpty()) return@inlineQuery
                    val title = "Телефон: $queryText"
                    val message = InfoFinder.getPhoneInfo(queryText)
                    val inlineResults =
                        InlineQueryResult.Article(
                            id = queryText,
                            title = title,
                            inputMessageContent = InputMessageContent.Text("<b>$title</b>\n$message", ParseMode.HTML),
                            description = message
                        )
                    bot.answerInlineQuery(inline.id, inlineResults)
                }
                message(Text and Command.not()) { bot, update ->
                    val ctx = MessageContext(bot, update.message?.chat ?: return@message)
                    val message = InfoFinder.getPhoneInfo(
                        update.message?.text ?: return@message
                    )
                    ctx.send(message)
                }
            }
        }.startPolling()
        logger.info("Bot started listening for updates")
    }
}