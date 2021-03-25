package ru.otus.kotlin.messaging.business.backend.operation.stub

import ru.otus.kotlin.messaging.business.backend.operation.stub.ChannelStubs.channelStub
import ru.otus.kotlin.messaging.business.backend.operation.stub.ChannelStubs.createChannelResponse
import ru.otus.kotlin.messaging.business.backend.operation.stub.ChannelStubs.deleteChannelResponse
import ru.otus.kotlin.messaging.business.backend.operation.stub.ChannelStubs.getChannelResponse
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
                responseId = createChannelResponse.responseId!!
                responseTime = createChannelResponse.responseTime!!
                channel = channelStub
            }
        }
    }
})

object ChannelDeleteStub : IOperation<TransportContext> by pipeline({

    startIf { stubCase != ContextStubCase.NONE }

    operation {

        startIf { stubCase == ContextStubCase.CHANNEL_DELETE_SUCCESS }

        execute {
            messagingContext.apply {
                responseId = deleteChannelResponse.responseId!!
                responseTime = deleteChannelResponse.responseTime!!
                channel = channelStub
            }
        }
    }
})

object ChannelGetStub : IOperation<TransportContext> by pipeline({

    startIf { stubCase != ContextStubCase.NONE }

    operation {

        startIf { stubCase == ContextStubCase.CHANNEL_GET_SUCCESS }

        execute {
            messagingContext.apply {
                responseId = getChannelResponse.responseId!!
                responseTime = getChannelResponse.responseTime!!
                channels = listOf(channelStub)
            }
        }
    }
})
