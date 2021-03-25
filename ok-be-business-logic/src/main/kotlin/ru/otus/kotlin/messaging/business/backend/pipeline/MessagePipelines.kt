package ru.otus.kotlin.messaging.business.backend.pipeline

import ru.otus.kotlin.messaging.business.backend.operation.FinishPipeline
import ru.otus.kotlin.messaging.business.backend.operation.InitializePipeline
import ru.otus.kotlin.messaging.business.backend.operation.stub.MessageCreateStub
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.TransportContextStatus
import ru.otus.kotlin.messaging.pipeline.IOperation
import ru.otus.kotlin.messaging.pipeline.operation
import ru.otus.kotlin.messaging.pipeline.pipeline

object MessageCreatePipeline : IOperation<TransportContext> by pipeline({

    execute(InitializePipeline)
// the same
//    operation {
//        startIf { status == TransportContextStatus.NONE }
//        execute { status = TransportContextStatus.RUNNING }
//    }

//    but execution of that will cause ConcurrentModificationException
//    because run another builder
//    execute {
//        operation {
//
//        }
//    }

    execute(MessageCreateStub)
    execute(FinishPipeline)
})
