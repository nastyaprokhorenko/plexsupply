package basic.thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ReaderThread implements Runnable {
    private final String inputFile;
    private final BlockingQueue<String> blockingQueue;

    public ReaderThread(String inputFile, BlockingQueue<String> blockingQueue) {
        this.inputFile = inputFile;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        read();
    }

    public boolean read() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String buffer;
            while ((buffer = br.readLine()) != null) {
                if (!buffer.equals("")) {
                    blockingQueue.put(buffer);
                }
            }
            blockingQueue.put("End!");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Can't read data from file " + inputFile, e);
        }
        return true;
    }
}
