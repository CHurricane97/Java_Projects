package project;

import processing.Processor;
import processing.Status;
import processing.StatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Subtraction implements Processor {

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


            System.out.println("work4");
            int id = giveID();
            String[] additions = task.split("\\-");
            int wynik = Integer.parseInt(additions[0]);
            int progres = 1;
            for (int i = 1; i < additions.length; i++) {


                wynik -= Integer.parseInt(additions[i]);
                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }

                progres++;
                sl.statusChanged(new Status(id, progres * 100 / additions.length));
            }

            setResult(id, String.valueOf(wynik));
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
