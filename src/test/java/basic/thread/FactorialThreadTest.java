package basic.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactorialThreadTest {
    private BlockingQueue<String> readerQueue;
    private BlockingQueue<String> writerQueue;
    private FactorialThread factorialThread;

    @BeforeEach
    void setUp() throws InterruptedException {
        readerQueue = new ArrayBlockingQueue<>(1000);
        writerQueue = new ArrayBlockingQueue<>(1000);
        readerQueue.put("7");
        readerQueue.put("3");
        readerQueue.put("End!");
        factorialThread = new FactorialThread(readerQueue, writerQueue);
    }

    @Test
    void calculateFactorial_ok() {
        boolean actual = factorialThread.calculateFactorial();
        Assertions.assertTrue(actual);
        Assertions.assertEquals(0, readerQueue.size());
        Assertions.assertEquals(3, writerQueue.size());
        Assertions.assertEquals("7 = 5040", writerQueue.element());
    }

    @Test
    void run_ok() {
        Thread thread = new Thread(factorialThread);
        thread.start();
    }
}
