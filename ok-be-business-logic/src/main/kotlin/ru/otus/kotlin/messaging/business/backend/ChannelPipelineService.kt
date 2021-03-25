package ru.otus.kotlin.messaging.business.backend

import ru.otus.kotlin.messaging.business.backend.pipeline.ChannelCreatePipeline
import ru.otus.kotlin.messaging.business.backend.pipeline.ChannelDeletePipeline
import ru.otus.kotlin.messaging.business.backend.pipeline.ChannelGetPipeline
import ru.otus.kotlin.messaging.mapper.context.TransportContext

class ChannelPipelineService {

    suspend fun create(context: TransportContext) {
        ChannelCreatePipeline.execute(context)
    }

    suspend fun delete(context: TransportContext) {
        ChannelDeletePipeline.execute(context)
    }

    suspend fun get(context: TransportContext) {
        ChannelGetPipeline.execute(context)
    }
}
