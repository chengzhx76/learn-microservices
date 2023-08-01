## HashedWheelTimer时间轮原理分析

https://albenw.github.io/posts/ec8df8c/

## Dubbo HashedWheelTimer

https://github.com/apache/dubbo/blob/3.0/dubbo-common/src/main/java/org/apache/dubbo/common/timer/HashedWheelTimer.java

## Go WheelTimer

https://github.com/ouqiang/timewheel/blob/master/timewheel.go
https://github.com/fagongzi/goetty/blob/master/timewheel/timewheel_test.go


### ==================

````
tickDuration
每一tick的时间

timeUnit
tickDuration的时间单位

ticksPerWheel
就是轮子一共有多个格子，即要多少个tick才能走完这个wheel一圈。

maxPendingTimeouts
最大任务数，默认是-1，即不限制。如果并发量真的很高，可以设置一下，防止OOM
````

````
https://www.codetd.com/article/169432

代码中normalizeTicksPerWheel得出环的大小，取了一个大于等于ticksPerWheel且是2的N次幂的整数。为啥要取成2的N次幂呢？主要是因为在大小而2的N次幂的环上求索引非常的方便，a & (b-1) = a % b，当b时2的N次幂时成立。
为什么要用按位取与，不用%取余？
由于位运算直接对内存数据进行操作,不需要转成十进制，因此处理速度非常快

常用技巧
a & (b-1) = a % b，当b时2的N次幂时成立。
````