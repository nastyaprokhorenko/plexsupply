package basic.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterThreadTest {
    private static final String CORRECT_OUTPUT_FILE = "src/test/resources/output.txt";
    private static final String INCORRECT_OUTPUT_FILE = "";
    private BlockingQueue<String> blockingWriterQueue;
    private WriterThread writerThread;

    @BeforeEach
    void setUp() throws InterruptedException {
        blockingWriterQueue = new ArrayBlockingQueue<>(1000);
        blockingWriterQueue.put("1 = 1");
        blockingWriterQueue.put("3 = 6");
        blockingWriterQueue.put("End!");
        writerThread = new WriterThread(CORRECT_OUTPUT_FILE, blockingWriterQueue);
    }

    @Test
    void write_ok() {
        boolean actual = writerThread.write();
        Assertions.assertEquals(0, blockingWriterQueue.size());
        Assertions.assertTrue(actual);
    }

    @Test
    void write_RuntimeException_notOk() {
        WriterThread thread = new WriterThread(INCORRECT_OUTPUT_FILE, blockingWriterQueue);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, thread::write,
                "RuntimeException was expected");
        Assertions.assertEquals("Can't write data to file ", thrown.getMessage());
    }

    @Test
    void run_ok() {
        Thread thread = new Thread(writerThread);
        thread.start();
    }
}
