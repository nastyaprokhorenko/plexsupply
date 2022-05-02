package basic.thread;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WriterThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(ReaderThread.class);
    private final String outputFile;
    private final BlockingQueue<String> writerQueue;

    public WriterThread(String outputFile, BlockingQueue<String> writerQueue) {
        this.outputFile = outputFile;
        this.writerQueue = writerQueue;
    }

    @Override
    public void run() {
        logger.info("run method from writer thread was called");
        write();
        logger.info("run method from writer thread has finished work");
    }

    public boolean write() {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            String buffer;
            while (true) {
                buffer = writerQueue.take();
                if (buffer.equals("End!")) {
                    break;
                }
                writer.println(buffer);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Can't write data to file " + outputFile, e);
        }
        return true;
    }
}
