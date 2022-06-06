package project;

import processing.Processor;
import processing.Status;
import processing.StatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownToSmallLetters implements Processor {

    //Procesor do zamiany wielkosci liter

    private String result = "";
    int taskId = 1;

    private synchronized int giveID() {
        int id = taskId;
        taskId++;
        return id;
    }

    private synchronized void setResult(int id, String str) {
        result = result + "Zadanie: " + id + " Wynik: " + str + "\n";
    }

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {


            System.out.println("work1");
            int id = giveID();
            StringBuilder str = new StringBuilder();
            char[] letters = task.toCharArray();
            int progres = 0;
            for (char letter : letters) {

                if (letter >= 'A' && letter <= 'Z') {
                    str.append((char) ((int) letter + 32));
                } else {
                    str.append(letter);
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }

                progres++;
                sl.statusChanged(new Status(id, progres * 100 / letters.length));
            }
            setResult(id, str.toString());
            executor.shutdown();

        });
        return true;
    }

    @Override
    public String getInfo() {
        return "Zamiana rozmiarów liter";
    }

    @Override
    public String getResult() {
        return result;
    }
}
