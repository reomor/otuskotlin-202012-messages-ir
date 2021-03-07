package ru.otus.kotlin.messaging.mapper

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class TransportContextTest {

    @Test
    fun nonEmptyContexts() {
        val context = TransportContext()
        assertNotNull(context.messagingContext)
        assertNotNull(context.commonContext)
        assertNotNull(context.openApiContext)

        assertNull(context.commonContext.request)
        assertNull(context.commonContext.response)

        assertNull(context.openApiContext.request)
        assertNull(context.openApiContext.response)
    }

    @Test
    fun emptyMessagingContext() {
        val context = TransportContext()
        assertNotNull(context.messagingContext)
        val messagingContext = context.messagingContext
        assertThrows<IllegalArgumentException> {
            messagingContext.instantMessage
        }
        assertThrows<IllegalArgumentException> {
            messagingContext.channelMessage
        }
        assertThrows<IllegalArgumentException> {
            messagingContext.channel
        }
        assertThrows<IllegalArgumentException> {
            messagingContext.page
        }
        assertThrows<IllegalArgumentException> {
            messagingContext.profile
        }
    }
}