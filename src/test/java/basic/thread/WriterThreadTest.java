package basic.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterThreadTest {
    private static final String CORRECT_OUTPUT_FILE = "src/test/resources/output.txt";
    private static final String INCORRECT_OUTPUT_FILE = "";
    private BlockingQueue<String> writerQueue;
    private WriterThread writerThread;

    @BeforeEach
    void setUp() throws InterruptedException {
        writerQueue = new ArrayBlockingQueue<>(1000);
        writerQueue.put("1 = 1");
        writerQueue.put("3 = 6");
        writerQueue.put("End!");
        writerThread = new WriterThread(CORRECT_OUTPUT_FILE, writerQueue);
    }

    @Test
    void write_ok() {
        boolean actual = writerThread.write();
        Assertions.assertEquals(0, writerQueue.size());
        Assertions.assertTrue(actual);
    }

    @Test
    void write_RuntimeException_notOk() {
        WriterThread thread = new WriterThread(INCORRECT_OUTPUT_FILE, writerQueue);
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
