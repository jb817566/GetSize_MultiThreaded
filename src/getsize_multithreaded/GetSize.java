package getsize_multithreaded;

import IO.FileIO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GetSize {

    private static ThreadPoolExecutor tpe = null;
    public static final Deque<Result> dr = new ArrayDeque<Result>();
    public static Map results = null;
    private static BlockingQueue<Runnable> bq;
    public static CountDownLatch cdl = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        final FileIO io = new FileIO();

//        System.out.print("Please enter path to list of files: ");
//        String[] lin = read(new Scanner(System.in).nextLine()); //input path
        String[] lin = read("E:\\testcase.txt"); //input path

        System.out.println("How many threads do you want?");
//        int numThreads = Integer.parseInt(new Scanner(System.in).nextLine());
        int numThreads = 10;
        bq = new ArrayBlockingQueue<Runnable>(10000000);

        cdl = new CountDownLatch(lin.length - 1);
        results = new ConcurrentHashMap<Result, Object>();
        tpe = new ThreadPoolExecutor(10, 18, new Long(5), TimeUnit.SECONDS, bq);
//        writeToFile(goodFiles, args[0]);
//            writeToFile(fSizes);
        String str = "";
        for (String s : lin) {
            try {
                tpe.submit(new SizeCheckWorker(s));
            } catch (Exception e) {
                io.writeToFile(e.getMessage() + "," + s, "E:\\logsjava.txt", true);
            }
        }
        System.out.println("d");
        tpe.allowCoreThreadTimeOut(true);
        tpe.prestartAllCoreThreads();
//                while (!tpe.isShutdown()) {
//            System.out.println("Pool is still alive");
//            System.out.println(cdl.getCount());
//            System.out.println(tpe.getPoolSize());
//            Thread.sleep(2000);
//        }

//        while (!tpe.isShutdown()) {
//            Thread.sleep(1000);
//            System.out.println("Not yet");
//            System.out.println("Active: " + tpe.getActiveCount());
//            System.out.println("Queued" + tpe.getPoolSize());
////            if (tpe.getPoolSize() == 0) {
////                tpe.shutdownNow();
////            }
//        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                tpe.shutdown();
                System.out.println(dr.size());
            }
        }).start();

//        str += "Total MB: " + totalmb;
//        str += "\n" + "Total GB" + totalmb / 1000;
//        str += "\n" + "Total Skipped: " + skipped;
//        JOptionPane.showMessageDialog(null, str);
//
////         create a JTextArea
//        JTextArea textArea = new JTextArea(6, 25);
//        String skippedBox = "";
//      for(String n: skippedFiles){
//          skippedBox += n + "\n";
//      }
//        textArea.setText(skippedBox);
//        textArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(textArea);
//        JOptionPane.showMessageDialog(null, scrollPane);
    }

    public static String[] read(String filename) throws Exception {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    public static void writeToFile(ArrayList<String> input, String path) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(path));
            for (int i = 0; i < input.size(); i++) {
                printWriter.println(input.get(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
