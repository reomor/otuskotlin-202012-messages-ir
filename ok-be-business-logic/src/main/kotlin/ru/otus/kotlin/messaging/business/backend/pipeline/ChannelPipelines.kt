package ru.otus.kotlin.messaging.business.backend.pipeline

import ru.otus.kotlin.messaging.business.backend.operation.FinishPipeline
import ru.otus.kotlin.messaging.business.backend.operation.InitializePipeline
import ru.otus.kotlin.messaging.business.backend.operation.stub.ChannelCreateStub
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.pipeline.IOperation
import ru.otus.kotlin.messaging.pipeline.pipeline

object ChannelCreatePipeline : IOperation<TransportContext> by pipeline({
    execute(InitializePipeline)
    execute(ChannelCreateStub)
    execute(FinishPipeline)
})
