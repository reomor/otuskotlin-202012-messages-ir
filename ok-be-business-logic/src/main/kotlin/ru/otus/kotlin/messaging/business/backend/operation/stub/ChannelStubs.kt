package ru.otus.kotlin.messaging.business.backend.operation.stub

import ru.otus.kotlin.messaging.mapper.context.ContextStubCase
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.pipeline.IOperation
import ru.otus.kotlin.messaging.pipeline.operation
import ru.otus.kotlin.messaging.pipeline.pipeline

object ChannelCreateStub : IOperation<TransportContext> by pipeline({

    startIf { stubCase != ContextStubCase.NONE }

    operation {

        startIf { stubCase == ContextStubCase.CHANNEL_CREATE_SUCCESS }

        execute {
            messagingContext.apply {
                responseId = Stubs.createChannelResponse.responseId!!
                responseTime = Stubs.createChannelResponse.responseTime!!
                channel = Stubs.channel
            }
        }
    }
})
