package basic.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderThreadTest {
    private static final String CORRECT_INPUT_FILE = "src/test/resources/input.txt";
    private static final String INCORRECT_INPUT_FILE = "";
    private BlockingQueue<String> blockingQueue;
    private ReaderThread readerThread;

    @BeforeEach
    void setUp() {
        blockingQueue = new ArrayBlockingQueue<>(1000);
        readerThread = new ReaderThread(CORRECT_INPUT_FILE, blockingQueue);
    }

    @Test
    void read_ok() {
        boolean actual = readerThread.read();
        Assertions.assertEquals(20, blockingQueue.size());
        Assertions.assertTrue(actual);
    }

    @Test
    void read_RuntimeException_notOk() {
        ReaderThread thread = new ReaderThread(INCORRECT_INPUT_FILE, blockingQueue);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, thread::read,
                "RuntimeException was expected");
        Assertions.assertEquals("Can't read data from file ", thrown.getMessage());
    }

    @Test
    void run_ok() {
        Thread thread = new Thread(readerThread);
        thread.start();
    }
}
