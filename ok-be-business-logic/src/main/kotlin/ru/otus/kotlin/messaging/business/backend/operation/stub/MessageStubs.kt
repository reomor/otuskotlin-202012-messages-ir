package ru.otus.kotlin.messaging.business.backend.operation.stub

import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
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
            commonContext.response = CreateChannelMessageResponse(
                responseId = "123456",
                responseTime = "2021-03-21T18:16:55.351733200",
                request = commonContext.request
            )
        }
    }
})
