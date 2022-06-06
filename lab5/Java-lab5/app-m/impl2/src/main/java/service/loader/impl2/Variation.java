package service.loader.impl2;

import service.loader.api.AnalysisException;
import service.loader.api.AnalysisService;
import service.loader.api.DataSet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Variation implements AnalysisService {

    DataSet result;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Odchylenie Standardowe";
    }

    boolean status = true;
    @Override
    public void submit(DataSet ds) throws AnalysisException {
        if(status) {
            status = false;
            new Thread(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String[][] resultTab = new String[ds.getData().length][];
                for (int i = 0; i < ds.getData().length; i++) {
                    resultTab[i] = new String[1];
                    resultTab[i][0] = calculateVariation(ds.getData()[i]);
                    if(i==0) resultTab[i][0] = "Odchylenie Standardowe";
                }
                result = new DataSet();
                result.setData(resultTab);
                status = true;
                System.out.println(result.getData()[1][0]);
            }).start();
        }else throw new AnalysisException("Nie skonczono zadania!");
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        DataSet r = result;
        if(clear) result = null;
        return r;
    }

    public String calculateVariation(String[] data) {
        String wynik = "";

        int howlong = data.length;
        double[] dane = new double[howlong];
        double srednia = 0;
        for (int i = 0; i < howlong; i++) {
            dane[i] = Double.parseDouble(data[i]);
            srednia += dane[i];
        }
        srednia = srednia / howlong;
        double up = 0;

        for (int i = 0; i < howlong; i++) {

            up += Math.pow((dane[i] - srednia), 2);

        }
        wynik= String.valueOf(Math.sqrt(up/howlong));

        return wynik;
    }

    @Override
    public String toString() {
        return "Odchylenie Standardowe";
    }
}
