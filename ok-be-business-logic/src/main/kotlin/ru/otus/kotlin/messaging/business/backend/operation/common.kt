package ru.otus.kotlin.messaging.business.backend.operation

import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.TransportContextStatus
import ru.otus.kotlin.messaging.pipeline.IOperation
import ru.otus.kotlin.messaging.pipeline.operation
import ru.otus.kotlin.messaging.pipeline.pipeline

object InitializePipeline : IOperation<TransportContext> by operation({
    startIf { status == TransportContextStatus.NONE }
    execute { status = TransportContextStatus.RUNNING }
})

object FinishPipeline : IOperation<TransportContext> by pipeline({
    operation {
        startIf { status in setOf(TransportContextStatus.RUNNING, TransportContextStatus.FINISHING) }
        execute { status = TransportContextStatus.SUCCESS }
    }

    operation {
        startIf { status != TransportContextStatus.SUCCESS }
        execute { status = TransportContextStatus.ERROR }
    }
})
