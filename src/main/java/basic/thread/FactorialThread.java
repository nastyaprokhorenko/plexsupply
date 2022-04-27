package basic.thread;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class FactorialThread implements Runnable {
    private final BlockingQueue<String> blockingQueue;
    private final BlockingQueue<String> blockingWriterQueue;

    public FactorialThread(BlockingQueue<String> blockingQueue,
                           BlockingQueue<String> blockingWriterQueue) {
        this.blockingQueue = blockingQueue;
        this.blockingWriterQueue = blockingWriterQueue;
    }

    @Override
    public void run() {
        calculateFactorial();
    }

    public boolean calculateFactorial() {
        BigInteger result;
        String buffer;
        int number;
        try {
            while (true) {
                buffer = blockingQueue.take();
                if (buffer.equals("End!")) {
                    blockingWriterQueue.put(buffer);
                    break;
                }
                result = BigInteger.ONE;
                number = Integer.parseInt(buffer);
                for (int i = 2; i <= number; i++) {
                    result = result.multiply(BigInteger.valueOf(i));
                }
                blockingWriterQueue.put(number + " = " + result);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Can't calculate factorial of number", e);
        }
        return true;
    }
}
