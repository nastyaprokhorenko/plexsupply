package basic.thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReaderThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(ReaderThread.class);
    private final String inputFile;
    private final BlockingQueue<String> readerQueue;

    public ReaderThread(String inputFile, BlockingQueue<String> readerQueue) {
        this.inputFile = inputFile;
        this.readerQueue = readerQueue;
    }

    @Override
    public void run() {
        logger.info("run method from reader thread was called");
        read();
        logger.info("run method from reader thread has finished work");
    }

    public boolean read() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String buffer;
            while ((buffer = br.readLine()) != null) {
                if (buffer.matches("^[0-9]+$")) {
                    readerQueue.put(buffer);
                }
            }
            readerQueue.put("End!");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Can't read data from file " + inputFile, e);
        }
        return true;
    }
}
