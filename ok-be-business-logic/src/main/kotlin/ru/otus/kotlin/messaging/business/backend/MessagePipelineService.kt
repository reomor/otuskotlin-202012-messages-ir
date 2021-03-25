package ru.otus.kotlin.messaging.business.backend

import ru.otus.kotlin.messaging.business.backend.pipeline.MessageCreatePipeline
import ru.otus.kotlin.messaging.business.backend.pipeline.MessageDeletePipeline
import ru.otus.kotlin.messaging.business.backend.pipeline.MessageEditPipeline
import ru.otus.kotlin.messaging.business.backend.pipeline.MessageGetPipeline
import ru.otus.kotlin.messaging.mapper.context.TransportContext

class MessagePipelineService {

    suspend fun create(context: TransportContext) {
        MessageCreatePipeline.execute(context)
    }

    suspend fun delete(context: TransportContext) {
        MessageDeletePipeline.execute(context)
    }

    suspend fun edit(context: TransportContext) {
        MessageEditPipeline.execute(context)
    }

    suspend fun get(context: TransportContext) {
        MessageGetPipeline.execute(context)
    }
}
