import java.util.*;  
import java.util.stream.*;  

public class SortJava {
    static {
        System.loadLibrary("native");
    }

    Double[] a;
    Double[] b;
    Boolean order;
    
    public static void main(String[] args) {
        SortJava s = new SortJava();
        Double[] tab=new Double[]{7.5, -22.5, 3.5, 5.05, 3.33, 7.77};
        System.out.println("sort01");
        Double[] d = s.sort01(tab, true);

        for (Double dd : d){
            System.out.print(dd+" ");
        }
        System.out.println();
        System.out.println();

        Double[] d1 = s.sort01(tab, false);
        for (Double dd : d1){
            System.out.print(dd+" ");
        }
        System.out.println();
        System.out.println();
        System.out.println("sort02");
        s.order = true;
        Double[] d3 = s.sort02(tab);
        for (Double dd : d3){
            System.out.print(dd+" ");
        }
        System.out.println();
        System.out.println();
        s.order = false;
        Double[] d4 = s.sort02(tab);
        for (Double dd : d4){
            System.out.print(dd+" ");
        }
        System.out.println();
        System.out.println();

        System.out.println();
        System.out.println("sort03");
        s.sort03();

        System.out.println();
        System.out.println("Wynik sort04");
        for (Double double1 : s.b) {
            System.out.print(double1+" ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Wcisnij przycisk zeby wyjsc");
        Scanner sc = new Scanner(System.in);
        sc.next();
        sc.close();
    }

    public native Double[] sort01(Double[] a, Boolean order);

    public native Double[] sort02(Double[] a);

    public native void sort03();

    public void sort04(){

        List<Double> slist = Arrays.asList(a);
        List<Double> sortedList;

        if(order){
            sortedList = slist.stream().sorted().collect(Collectors.toList());
        }else{
            sortedList=slist.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }

        b = new Double[a.length];

        for (int i = 0; i < b.length; i++) {
            b[i] = (Double) sortedList.toArray()[i];
        }
    }
}