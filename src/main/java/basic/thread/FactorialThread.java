package basic.thread;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FactorialThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(ReaderThread.class);
    private final BlockingQueue<String> readerQueue;
    private final BlockingQueue<String> writerQueue;

    public FactorialThread(BlockingQueue<String> readerQueue, BlockingQueue<String> writerQueue) {
        this.readerQueue = readerQueue;
        this.writerQueue = writerQueue;
    }

    @Override
    public void run() {
        logger.info("run method from factorial thread was called");
        calculateFactorial();
        logger.info("run method from factorial thread has finished work");
    }

    public boolean calculateFactorial() {
        BigInteger result;
        String buffer;
        int number;
        try {
            while (true) {
                buffer = readerQueue.take();
                if (buffer.equals("End!")) {
                    writerQueue.put(buffer);
                    break;
                }
                result = BigInteger.ONE;
                number = Integer.parseInt(buffer);
                for (int i = 2; i <= number; i++) {
                    result = result.multiply(BigInteger.valueOf(i));
                }
                writerQueue.put(number + " = " + result);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Can't calculate factorial of number", e);
        }
        return true;
    }
}
