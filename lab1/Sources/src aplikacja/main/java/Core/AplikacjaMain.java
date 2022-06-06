package Core;

import Classes.Directoryhandler;
import Classes.MD5handler;
import Classes.Plik;
import Classes.StatusPliku;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplikacjaMain {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        MD5handler md5 = new MD5handler();
        Directoryhandler dir = new Directoryhandler();


        List<String> listaplikow = new ArrayList<>();
        List<Plik> listaplikowzmd5 = new ArrayList<>();
        List<Plik> listaplikowzmd5poprzednia = new ArrayList<>();
        List<StatusPliku> listazmian=new ArrayList<>();


        boolean pentla = true;
        Scanner console = new Scanner(System.in);
        String folder = "";
        while (true) {

            System.out.println("Podaj sciezke do folderu:");
            folder = console.nextLine();
            if (dir.checkdir(folder)) {
                break;

            }
            System.out.println("Blendny folder");

        }

        listaplikow = dir.listFilesUsingDirectoryStream(folder);
        int ile = listaplikow.size();
        for (int i = 0; i < ile; i++) {
            String pth = folder + "\\" + listaplikow.get(i);
            String s = md5.givenFile_generatingChecksum(pth);
            System.out.println(s);

            Plik temp = new Plik(listaplikow.get(i), s);
            if (!(temp.nazwa.equals("md5.txt"))) {
                listaplikowzmd5.add(temp);
            }

        }

//za pierwszym razem
        if (!(dir.checkiffirst(listaplikow))) {

            dir.createmd5(folder, listaplikowzmd5);

        }


        int znak = 1;

        pentla = true;

        while (true) {

            System.out.println("Co chcesz zrobić:");
            System.out.println("1-Sprawdzenie zmian");
            System.out.println("2-Aktualizacja");

            System.out.println("0-Wyjście");
            znak = console.nextInt();
            if (znak == 0) {
                break;
            }
            switch (znak) {
                case 1: {
                  listazmian.clear();
                  listaplikow.clear();
                  listaplikowzmd5.clear();
                  listaplikowzmd5poprzednia.clear();

                    listaplikow = dir.listFilesUsingDirectoryStream(folder);
                     ile = listaplikow.size();
                    for (int i = 0; i < ile; i++) {
                        String pth = folder + "\\" + listaplikow.get(i);
                        String s = md5.givenFile_generatingChecksum(pth);
                        System.out.println(s);

                        Plik temp = new Plik(listaplikow.get(i), s);
                        if (!(temp.nazwa.equals("md5.txt"))) {
                            listaplikowzmd5.add(temp);
                        }

                    }

                    listaplikowzmd5poprzednia=dir.readmd5(folder);
                    for (int i = 0; i < listaplikowzmd5poprzednia.size(); i++) {
                        Plik temp=listaplikowzmd5poprzednia.get(i);
                        System.out.println(temp.nazwa+"  "+temp.md5);

                    }

                    ile=listaplikowzmd5poprzednia.size();

                    for (int i = 0; i < ile; i++) {
                        Plik temp=listaplikowzmd5poprzednia.get(i);
                        int indeks=dir.checkifonlist2(listaplikowzmd5,temp.nazwa);
                        if (indeks==-1){
                            StatusPliku tymst=new StatusPliku(temp.nazwa,0);
                            listazmian.add(tymst);

                        }else {
                            if(md5.checkIfSame(temp.md5,listaplikowzmd5.get(indeks).md5)){
                                StatusPliku tymst=new StatusPliku(temp.nazwa,1);
                                listazmian.add(tymst);


                            }else {

                                StatusPliku tymst=new StatusPliku(temp.nazwa,2);
                                listazmian.add(tymst);

                            }

                        }

                    }

                    ile=listaplikowzmd5.size();
                    for (int i = 0; i < ile; i++) {
                        Plik temp=listaplikowzmd5.get(i);
                        boolean jest=dir.checkifonlist(listazmian,temp.nazwa);
                        if (!jest){

                            StatusPliku tymst=new StatusPliku(temp.nazwa,3);
                            listazmian.add(tymst);



                        }
                    }

                    ile=listazmian.size();
                    System.out.println("          ");
                    System.out.println("          ");
                    System.out.println("*************************");
                    for (int i = 0; i < ile; i++) {
                        StatusPliku temp=listazmian.get(i);
                        int wybor=temp.status;
                        switch (wybor){

                            case 0:
                                System.out.println(temp.nazwa+" DELETED");
                                break;
                            case 1:
                                System.out.println(temp.nazwa+" NO CHANGES");
                                break;

                            case 2:
                                System.out.println(temp.nazwa+" CHANGED");
                                break;
                            case 3:
                                System.out.println(temp.nazwa+" ADDED");
                                break;
                            default:

                                break;


                        }


                    }
                    System.out.println("*************************");






                }
                break;
                case 2: {
                    listazmian.clear();
                    listaplikow.clear();
                    listaplikowzmd5.clear();
                    listaplikowzmd5poprzednia.clear();

                    listaplikow = dir.listFilesUsingDirectoryStream(folder);
                     ile = listaplikow.size();
                    for (int i = 0; i < ile; i++) {
                        String pth = folder + "\\" + listaplikow.get(i);
                        String s = md5.givenFile_generatingChecksum(pth);
                        System.out.println(s);

                        Plik temp = new Plik(listaplikow.get(i), s);
                        if (!(temp.nazwa.equals("md5.txt"))) {
                            listaplikowzmd5.add(temp);
                        }

                    }

                    dir.updatemd5(folder, listaplikowzmd5);

                }
                break;

                default: {

                }
            }


        }









    }
}
