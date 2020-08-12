HashedWheelTimer
threadFactory：定时任务都是后台任务，需要开启线程，我们通常会通过自定义 threadFactory 来命名线程，嫌麻烦就使用 Executors.defaultThreadFactory()。
tickDuration 和 timeUnit 定义了一格的时间长度，默认的就是 100ms。
ticksPerWheel 定义了一圈有多少格，默认的就是 512；
leakDetection：用于追踪内存泄漏，本文不会介绍它，感兴趣的读者请自行去了解它。
maxPendingTimeouts：最大允许等待的 Timeout 实例数，也就是我们可以设置不允许太多的任务等待。如果未执行任务数达到阈值，那么再次提交任务会抛出 RejectedExecutionException 异常。默认不限制。