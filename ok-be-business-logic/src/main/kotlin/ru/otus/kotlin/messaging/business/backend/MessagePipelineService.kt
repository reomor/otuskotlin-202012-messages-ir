package ru.otus.kotlin.messaging.business.backend

import ru.otus.kotlin.messaging.business.backend.pipeline.MessageCreatePipeline
import ru.otus.kotlin.messaging.mapper.context.TransportContext

class MessagePipelineService {

    suspend fun create(context: TransportContext) {
        MessageCreatePipeline.execute(context)
    }

    suspend fun delete(context: TransportContext) {
    }

    suspend fun edit(context: TransportContext) {
    }

    suspend fun get(context: TransportContext) {
    }
}