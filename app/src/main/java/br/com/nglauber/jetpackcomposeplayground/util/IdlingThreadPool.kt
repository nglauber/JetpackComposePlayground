package br.com.nglauber.jetpackcomposeplayground.util

import androidx.test.espresso.idling.concurrent.IdlingThreadPoolExecutor
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

object IdlingThreadPool : IdlingThreadPoolExecutor(
    "coroutinesDispatchersThreadPool",
    Runtime.getRuntime().availableProcessors(),
    Runtime.getRuntime().availableProcessors(),
    0L,
    TimeUnit.MILLISECONDS,
    LinkedBlockingQueue(),
    Executors.defaultThreadFactory()
)