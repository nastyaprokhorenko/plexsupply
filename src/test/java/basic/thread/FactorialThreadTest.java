package basic.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactorialThreadTest {
    private BlockingQueue<String> blockingQueue;
    private BlockingQueue<String> blockingWriterQueue;
    private FactorialThread factorialThread;

    @BeforeEach
    void setUp() throws InterruptedException {
        blockingQueue = new ArrayBlockingQueue<>(1000);
        blockingWriterQueue = new ArrayBlockingQueue<>(1000);
        blockingQueue.put("7");
        blockingQueue.put("3");
        blockingQueue.put("End!");
        factorialThread = new FactorialThread(blockingQueue, blockingWriterQueue);
    }

    @Test
    void calculateFactorial_ok() {
        boolean actual = factorialThread.calculateFactorial();
        Assertions.assertTrue(actual);
        Assertions.assertEquals(0, blockingQueue.size());
        Assertions.assertEquals(3, blockingWriterQueue.size());
        Assertions.assertEquals("7 = 5040", blockingWriterQueue.element());
    }

    @Test
    void run_ok() {
        Thread thread = new Thread(factorialThread);
        thread.start();
    }
}
