package com.specialprojectslab.simpleux.concurrent;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.fail;

public class OrderedThreadPoolExecutorTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<String,AtomicLong> results = new ConcurrentHashMap<String,AtomicLong>();
    private static final int TEST_SIZE = 1000000;

    @Test
    public void shouldAlwaysExecuteColorsInOrder() throws Exception {

        int corePoolSize = 10;

        OrderedThreadPoolExecutor executor = new OrderedThreadPoolExecutor(corePoolSize, 10, TimeUnit.SECONDS,
                new ThreadFactoryBuilder().setNameFormat("test-oe-%d").build());

        String[] colors = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

        // initialise results

        Map<String,Long> countersByColor = new HashMap<String, Long>();
        for( String c : colors ) {
            countersByColor.put(c, 0L);
            results.put(c, new AtomicLong(0));
        }

        final FailureListener failureListener = new FailureListener() {

            @Override
            public void onFailure(String message) {
               log.info(message);
            }
        };

        // run test
        for ( int i=0;i<TEST_SIZE;i++) {


            int colorIndex = i % colors.length;
            String color = colors[colorIndex];
            long lastSeqNo = countersByColor.get(color);
            long seqNo = lastSeqNo + 1;
            countersByColor.put(color, seqNo);
            executor.execute(new MyOrderedRunnable(seqNo, colors[colorIndex], failureListener));
        }

        executor.shutdown();
        if ( !executor.awaitTermination(30, TimeUnit.SECONDS)) {
            fail("Should have shut down cleanly");
        };


    }

    private class MyOrderedRunnable implements OrderedRunnable {
        private final String color;
        private final long seqNo;
        private final FailureListener failureListener;

        private MyOrderedRunnable(long seqNo, String color, FailureListener failureListener) {
            this.seqNo = seqNo;
            this.color = color;
            this.failureListener = failureListener;
        }

        @Override
        public String getColor() {
            return color;
        }

        @Override
        public void run() {
            AtomicLong counter = results.get(color);
            long before = counter.getAndIncrement();
            long expected = seqNo-1;
            if ( before != seqNo-1)
                failureListener.onFailure("Expected " + expected + " but was " + before);
        }
    }


    private interface FailureListener {
        void onFailure(String message);
    }

}
