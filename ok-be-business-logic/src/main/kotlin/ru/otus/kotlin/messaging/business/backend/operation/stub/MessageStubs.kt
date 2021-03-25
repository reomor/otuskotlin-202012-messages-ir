package ru.otus.kotlin.messaging.business.backend.operation.stub

import ru.otus.kotlin.messaging.business.backend.operation.stub.MessageStubs.createChannelMessageResponse
import ru.otus.kotlin.messaging.business.backend.operation.stub.MessageStubs.deleteChannelMessageResponse
import ru.otus.kotlin.messaging.business.backend.operation.stub.MessageStubs.editChannelMessageResponse
import ru.otus.kotlin.messaging.business.backend.operation.stub.MessageStubs.getChannelMessageResponse
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
            responseId = createChannelMessageResponse.responseId!!
            responseTime = createChannelMessageResponse.responseTime!!
            messagingContext.errors = emptyList()
        }
    }
})

object MessageDeleteStub : IOperation<TransportContext> by pipeline({

    startIf { stubCase != ContextStubCase.NONE }

    operation {

        startIf { stubCase == ContextStubCase.MESSAGE_DELETE_SUCCESS }

        execute {
            responseId = deleteChannelMessageResponse.responseId!!
            responseTime = deleteChannelMessageResponse.responseTime!!
            messagingContext.errors = emptyList()
        }
    }
})

object MessageEditStub : IOperation<TransportContext> by pipeline({

    startIf { stubCase != ContextStubCase.NONE }

    operation {

        startIf { stubCase == ContextStubCase.MESSAGE_EDIT_SUCCESS }

        execute {
            responseId = editChannelMessageResponse.responseId!!
            responseTime = editChannelMessageResponse.responseTime!!
            messagingContext.errors = emptyList()
        }
    }
})

object MessageGetStub : IOperation<TransportContext> by pipeline({

    startIf { stubCase != ContextStubCase.NONE }

    operation {

        startIf { stubCase == ContextStubCase.MESSAGE_GET_SUCCESS }

        execute {
            responseId = getChannelMessageResponse.responseId!!
            responseTime = getChannelMessageResponse.responseTime!!
            messagingContext.messages = listOf(MessageStubs.channelMessageStub)
            messagingContext.errors = emptyList()
        }
    }
})
