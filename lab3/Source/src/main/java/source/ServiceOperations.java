package source;

import com.jayway.jsonpath.JsonPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class ServiceOperations {

    public static int getCountryCount(String url){
        try {
            return JsonPath.parse(urlReader(url)).read("$['count']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getContinentCount(String url){
        try {
            return JsonPath.parse(urlReader(url)).read("$['count']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getContinentFromCountry(String url){
        try {
            return JsonPath.parse(urlReader(url)).read("$['_links']['country:continent']['name']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCountryNameFromList(String url, int index){
        try {
            return JsonPath.parse(urlReader(url)).read("$['_links']['country:items'][" + index + "]['name']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCountryHrefFromList(String url, int index){
        try {
            return JsonPath.parse(urlReader(url)).read("$['_links']['country:items'][" + index + "]['href']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getCountryPopulation(String url){
        try {
            return JsonPath.parse(urlReader(url)).read("$['population']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getCountryCurrency(String url){
        try {
            return JsonPath.parse(urlReader(url)).read("$['currency_code']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getAdministrative(String url){
        try {
            String adminUrl = JsonPath.parse(urlReader(url)).read("$['_links']['country:admin1_divisions']['href']");
            return JsonPath.parse(urlReader(adminUrl)).read("$['count']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getCountryCountOnContinent(String url){
        try {
            return JsonPath.parse(urlReader(url)).read("$['count']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getContinentNameFromList(String url, int index){
        try {
            return JsonPath.parse(urlReader(url)).read("$['_links']['continent:items'][" + index + "]['name']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getContinentHrefFromList(String url, int index){
        try {
            return JsonPath.parse(urlReader(url)).read("$['_links']['continent:items'][" + index + "]['href']");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String spaceDeleter(String message) {
        message=message.toLowerCase(Locale.ROOT);
        return message.replaceAll("\\s","");
    }

    public static boolean checkAnswer(String e, String myanswer) {
        myanswer = spaceDeleter(myanswer);
        e = spaceDeleter(e);
        if (e.equals(myanswer)) {
            return true;
        } else {
            return false;
        }
    }

    private static String urlReader(String url) throws IOException {
        URL urlQ1 = new URL(url);
        HttpURLConnection conQ1  = (HttpURLConnection) urlQ1.openConnection();
        conQ1.setRequestMethod("GET");
        BufferedReader bufferedReaderQ1 = new BufferedReader(new InputStreamReader(conQ1.getInputStream()));
        return bufferedReaderQ1.readLine();
    }

}
