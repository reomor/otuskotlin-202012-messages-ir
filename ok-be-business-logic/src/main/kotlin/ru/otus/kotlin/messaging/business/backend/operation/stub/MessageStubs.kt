package ru.otus.kotlin.messaging.business.backend.operation.stub

import ru.otus.kotlin.messaging.mapper.context.ContextStubCase
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.pipeline.IOperation
import ru.otus.kotlin.messaging.pipeline.operation
import ru.otus.kotlin.messaging.pipeline.pipeline

object MessageCreateStub : IOperation<TransportContext> by pipeline({

    startIf { stubCase != ContextStubCase.NONE }

    operation {

        startIf { stubCase == ContextStubCase.MESSAGE_CREATE_SUCCESS }

        execute {
            responseId = MessageStubs.createChannelMessageResponse.responseId!!
            responseTime = MessageStubs.createChannelMessageResponse.responseTime!!
            messagingContext.errors = emptyList()
        }
    }
})
