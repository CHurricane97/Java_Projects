package service.loader;


//import com.google.auto.service.AutoService;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@AutoService(AnalysisService.class)
public class Mediana implements AnalysisService {

    DataSet result;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Mediana";
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
                    resultTab[i][0] = calculateMedian(ds.getData()[i]);
                    if(i==0) resultTab[i][0] = "Mediana";
                }
                result = new DataSet();
                result.setData(resultTab);
                status = true;

            }).start();
        }else throw new AnalysisException("Nie skonczono zadania!");
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        DataSet r = result;
        if(clear) result = null;
        return r;
    }

    public String calculateMedian(String[] data) {
        String wynik = "";

        int howlong = data.length;
        double[] dane = new double[howlong];
        for (int i = 0; i < howlong; i++) {
            dane[i] = Double.parseDouble(data[i]);
        }
        Arrays.sort(dane);

        if (howlong % 2 == 0) {
            wynik = String.valueOf((dane[howlong / 2] + dane[(howlong / 2) - 1]) / 2);


        } else {
            wynik = String.valueOf(dane[howlong / 2]);
        }

        return wynik;
    }

    @Override
    public String toString() {
        return "Mediana";
    }
}
