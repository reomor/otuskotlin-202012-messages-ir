package ru.otus.kotlin.messaging.business.backend

import ru.otus.kotlin.messaging.business.backend.operation.stub.ChannelCreateStub
import ru.otus.kotlin.messaging.mapper.context.TransportContext

class ChannelPipelineService {

    suspend fun create(context: TransportContext) {
        ChannelCreateStub.execute(context)
    }

    suspend fun delete(context: TransportContext) {
    }

    suspend fun get(context: TransportContext) {
    }
}
