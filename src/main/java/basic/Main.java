package basic;

import basic.thread.FactorialThread;
import basic.thread.ReaderThread;
import basic.thread.WriterThread;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int QUEUE_SIZE = 1000;
    private static final String INPUT_FILE = "src/main/resources/input.txt";
    private static final String OUTPUT_FILE = "src/main/resources/output.txt";

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
        BlockingQueue<String> writerQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);

        Scanner in = new Scanner(System.in);
        System.out.print("Please, enter size of thread pool: ");
        int quantityOfThreads = in.nextInt();
        ExecutorService executorService = Executors.newFixedThreadPool(quantityOfThreads);

        ReaderThread reader = new ReaderThread(INPUT_FILE, queue);
        new Thread(reader).start();
        FactorialThread factorialThread = new FactorialThread(queue, writerQueue);
        for (int i = 0; i < quantityOfThreads; i++) {
            executorService.submit(new Thread(factorialThread));
        }
        WriterThread writer = new WriterThread(OUTPUT_FILE, writerQueue);
        new Thread(writer).start();
        try {
            if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
