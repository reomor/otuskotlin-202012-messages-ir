package ru.otus.kotlin.messaging.business.backend.pipeline

import ru.otus.kotlin.messaging.business.backend.operation.FinishPipeline
import ru.otus.kotlin.messaging.business.backend.operation.InitializePipeline
import ru.otus.kotlin.messaging.business.backend.operation.stub.MessageCreateStub
import ru.otus.kotlin.messaging.business.backend.operation.stub.MessageDeleteStub
import ru.otus.kotlin.messaging.business.backend.operation.stub.MessageEditStub
import ru.otus.kotlin.messaging.business.backend.operation.stub.MessageGetStub
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.pipeline.IOperation
import ru.otus.kotlin.messaging.pipeline.pipeline

object MessageCreatePipeline : IOperation<TransportContext> by pipeline({

    execute(InitializePipeline)
    //<editor-fold default-stated="collapsed" desc="useful comment">
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
    //</editor-fold>
    execute(MessageStubPipeline)
    execute(FinishPipeline)
})

object MessageDeletePipeline : IOperation<TransportContext> by pipeline({
    execute(InitializePipeline)
    execute(MessageStubPipeline)
    execute(FinishPipeline)
})

object MessageEditPipeline : IOperation<TransportContext> by pipeline({
    execute(InitializePipeline)
    execute(MessageStubPipeline)
    execute(FinishPipeline)
})

object MessageGetPipeline : IOperation<TransportContext> by pipeline({
    execute(InitializePipeline)
    execute(MessageStubPipeline)
    execute(FinishPipeline)
})

/**
 * All message stub cases
 */
object MessageStubPipeline : IOperation<TransportContext> by pipeline({
    execute(MessageCreateStub)
    execute(MessageDeleteStub)
    execute(MessageEditStub)
    execute(MessageGetStub)
})
