package com.specialprojectslab.simpleux.concurrent;


import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.*;


public class OrderedThreadPoolExecutor extends ThreadPoolExecutor {


    private final ConcurrentMap<String, Executor> childExecutors = newChildExecutorMap();


    public OrderedThreadPoolExecutor(
            int corePoolSize,
            long keepAliveTime, TimeUnit unit,
            ThreadFactory threadFactory) {

        super(corePoolSize, corePoolSize, keepAliveTime, unit,
                new LinkedTransferQueue<Runnable>(), threadFactory, new NewThreadRunsPolicy());
    }


    protected ConcurrentMap<String, Executor> newChildExecutorMap() {
        return new ConcurrentIdentityWeakKeyHashMap<String, Executor>();
    }


    protected Set<String> getChildExecutorKeySet() {
        return childExecutors.keySet();
    }

    protected boolean removeChildExecutor(Object key) {
        // FIXME: Succeed only when there is no task in the ChildExecutor's queue.
        //        Note that it will need locking which might slow down task submission.
        return childExecutors.remove(key) != null;
    }

    /**
     * Executes the specified task concurrently while maintaining the event
     * order.
     */
    @Override
    public void execute(Runnable task) {
        if (!(task instanceof OrderedRunnable)) {
            doUnorderedExecute(task);
        } else {
            OrderedRunnable r = (OrderedRunnable) task;
            getChildExecutor(r.getColor()).execute(task);
        }
    }

    private Executor getChildExecutor(String e) {
        Executor executor = childExecutors.get(e);
        if (executor == null) {
            executor = new ChildExecutor();
            Executor oldExecutor = childExecutors.putIfAbsent(e, executor);
            if (oldExecutor != null) {
                executor = oldExecutor;
            }
        }

        return executor;
    }


    void onAfterExecute(Runnable r, Throwable t) {
        afterExecute(r, t);
    }

    private final class ChildExecutor implements Executor, Runnable {
        private final LinkedList<Runnable> tasks = new LinkedList<Runnable>();

        ChildExecutor() {
            super();
        }

        public void execute(Runnable command) {
            boolean needsExecution;
            synchronized (tasks) {
                needsExecution = tasks.isEmpty();
                tasks.add(command);
            }

            if (needsExecution) {
                doUnorderedExecute(this);
            }
        }

        public void run() {
            Thread thread = Thread.currentThread();
            for (; ; ) {
                final Runnable task;
                synchronized (tasks) {
                    task = tasks.getFirst();
                }

                boolean ran = false;
                beforeExecute(thread, task);
                try {
                    task.run();
                    ran = true;
                    onAfterExecute(task, null);
                } catch (RuntimeException e) {
                    if (!ran) {
                        onAfterExecute(task, e);
                    }
                    throw e;
                } finally {
                    synchronized (tasks) {
                        tasks.removeFirst();
                        if (tasks.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Executes the specified task without maintaining the event order.
     */
    protected final void doUnorderedExecute(Runnable task) {
        super.execute(task);
    }


    private static final class NewThreadRunsPolicy implements RejectedExecutionHandler {
        NewThreadRunsPolicy() {
            super();
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                final Thread t = new Thread(r, "Temporary task executor");
                t.start();
            } catch (Throwable e) {
                throw new RejectedExecutionException(
                        "Failed to start a new thread", e);
            }
        }
    }

}
