package ru.otus.kotlin.messaging.business.backend.pipeline

import ru.otus.kotlin.messaging.business.backend.operation.FinishPipeline
import ru.otus.kotlin.messaging.business.backend.operation.InitializePipeline
import ru.otus.kotlin.messaging.business.backend.operation.stub.ChannelCreateStub
import ru.otus.kotlin.messaging.business.backend.operation.stub.ChannelDeleteStub
import ru.otus.kotlin.messaging.business.backend.operation.stub.ChannelGetStub
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.pipeline.IOperation
import ru.otus.kotlin.messaging.pipeline.pipeline

object ChannelCreatePipeline : IOperation<TransportContext> by pipeline({
    execute(InitializePipeline)
    execute(ChannelStubPipeline)
    execute(FinishPipeline)
})

object ChannelDeletePipeline : IOperation<TransportContext> by pipeline({
    execute(InitializePipeline)
    execute(ChannelStubPipeline)
    execute(FinishPipeline)
})

object ChannelGetPipeline : IOperation<TransportContext> by pipeline({
    execute(InitializePipeline)
    execute(ChannelStubPipeline)
    execute(FinishPipeline)
})

object ChannelStubPipeline : IOperation<TransportContext> by pipeline({
    execute(ChannelCreateStub)
    execute(ChannelDeleteStub)
    execute(ChannelGetStub)
})
