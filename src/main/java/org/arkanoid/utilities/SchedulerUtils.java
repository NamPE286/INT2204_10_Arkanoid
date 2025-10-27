package org.arkanoid.utilities;

import java.util.concurrent.*;

public class SchedulerUtils {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Runs a task once after a given delay (like JavaScript's setTimeout)
     */
    public static ScheduledFuture<?> setTimeout(Runnable task, long delayMillis) {
        return scheduler.schedule(task, delayMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Runs a task repeatedly at a fixed interval (like JavaScript's setInterval)
     */
    public static ScheduledFuture<?> setInterval(Runnable task, long intervalMillis) {
        return scheduler.scheduleAtFixedRate(task, intervalMillis, intervalMillis,
            TimeUnit.MILLISECONDS);
    }

    /**
     * Cancels a timeout or interval using the returned ScheduledFuture
     */
    public static void clear(ScheduledFuture<?> future) {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);
        }
    }

    /**
     * Shuts down the scheduler gracefully (optional)
     */
    public static void shutdown() {
        scheduler.shutdown();
    }

    public SchedulerUtils() {
    }
}
