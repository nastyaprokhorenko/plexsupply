package basic.thread;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class WriterThread implements Runnable {
    private final String outputFile;
    private final BlockingQueue<String> blockingWriterQueue;

    public WriterThread(String outputFile, BlockingQueue<String> blockingWriterQueue) {
        this.outputFile = outputFile;
        this.blockingWriterQueue = blockingWriterQueue;
    }

    @Override
    public void run() {
        write();
    }

    public boolean write() {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            String buffer;
            while (true) {
                buffer = blockingWriterQueue.take();
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
