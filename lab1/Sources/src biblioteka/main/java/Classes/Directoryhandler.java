package Classes;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.isDirectory;

public class Directoryhandler {


  public   boolean checkdir(String pth) {

        return (isDirectory(Path.of(pth)));
    }

  public   boolean checkiffirst(List<String> listplikow) {

        boolean jest = false;

        int siz = listplikow.size();
        for (int i = 0; i < siz; i++) {
            String temp = listplikow.get(i);
            if (temp.equals("md5.txt")) {
                jest = true;
                break;
            }
        }

        return jest;


    }

  public   void createmd5(String pth, List<Plik> offer) {


        String B = pth + "\\" + "md5.txt";
        try {
            File myObj = new File(B);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(B);

            int temp = offer.size();
            for (int i = 0; i < temp; i++) {
                String linia = offer.get(i).nazwa + "," + offer.get(i).md5 + "\n";
                myWriter.write(linia);


            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    ;

  public   void updatemd5(String pth, List<Plik> offer) {


        String A = pth + "\\" + "md5.txt";
        String B = pth + "\\" + "tymczas.txt";
        try {
            File myObj = new File(B);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(B);

            int temp = offer.size();
            for (int i = 0; i < temp; i++) {
                String linia = offer.get(i).nazwa + "," + offer.get(i).md5 + "\n";
                myWriter.write(linia);


            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        File myObj = new File(A);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
        File f1 = new File(B);
        File f2 = new File(A);
        boolean b = f1.renameTo(f2);
        if (b) {
            System.out.println("Skuteczna zmiana nazwy");
        } else {
            System.out.println("bład");
        }


    }

  public   List<Plik> readmd5(String pth) {

        List<Plik> templist = new ArrayList<>();
        String A = pth + "\\" + "md5.txt";


        try {
            BufferedReader br = new BufferedReader(new FileReader(A));
            String fileRead = br.readLine();
            while (fileRead != null) {
                String[] tokenize = fileRead.split(",");


                String temnazww = (tokenize[0]);
                String temmd5 = (tokenize[1]);
                templist.add(new Plik(temnazww, temmd5));


                fileRead = br.readLine();
            }

            br.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("file not found");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        return templist;


    }


    public List<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        List<String> listaplikow = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                    listaplikow.add(path.getFileName()
                            .toString());
                }
            }
        }
        int siz = listaplikow.size();
        for (int i = 0; i < siz; i++) {
            System.out.println(listaplikow.get(i));
        }


        return listaplikow;


    }


   public Boolean checkifonlist(List<StatusPliku> lista, String sprawdzane) {

        boolean jest = false;

        int siz = lista.size();
        for (int i = 0; i < siz; i++) {
            String temp = lista.get(i).nazwa;
            if (temp.equals(sprawdzane)) {
                jest = true;
                break;
            }
        }

        return jest;
    }

   public int checkifonlist2(List<Plik> lista, String sprawdzane) {

        boolean jest = false;
        int indeks = -1;
        int siz = lista.size();
        for (int i = 0; i < siz; i++) {
            String temp = lista.get(i).nazwa;
            if (temp.equals(sprawdzane)) {
                jest = true;
                indeks = i;
                break;
            }
        }

        return indeks;
    }
}
