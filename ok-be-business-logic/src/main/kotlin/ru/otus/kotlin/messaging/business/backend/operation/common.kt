package ru.otus.kotlin.messaging.business.backend.operation

import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.TransportContextStatus
import ru.otus.kotlin.messaging.pipeline.IOperation
import ru.otus.kotlin.messaging.pipeline.operation

object InitializePipeline : IOperation<TransportContext> by operation({
    startIf { status == TransportContextStatus.NONE }
    execute { status = TransportContextStatus.RUNNING }
})
