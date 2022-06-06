package Core;

import java.io.IOException;

public class AppMain {
    public static void main(String[] args) throws IOException {
    	DataStructureOperator dso=new DataStructureOperator();
    String[]a=	dso.getRecordFromFile("C:\\Users\\barsu\\Desktop\\zad 2 test directory\\1");
    System.out.println(a[0]);
    System.out.println(a[1]);
    System.out.println(a[2]);


    }
}
