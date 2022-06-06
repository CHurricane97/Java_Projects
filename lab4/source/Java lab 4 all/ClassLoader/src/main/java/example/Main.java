package example;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import processing.StatusListener;

public class Main {

	public static void main(String[] args) {
		// Niech zmienna p reprezentuje referencjê do jakiejœ nieznanej klasy, 
	    // implementuj¹cej interfejs Processor, za³adowanej w³asnym ³adowaczem.
		// Jednak w tym przyk³adowym kodzie nie dostarczono ¿adnego takiego ³adowacza klas.
		// Zamiast tego do p przypisano wartoœæ wy³uskana bezpoœrednio z klasy MyProcessor
		// (implementuj¹cej interfejs Processor, ale znanej na etapie kompilacji projektu).
		// W ten sposób "zasymulowano" ³adowanie klasy.
		Class<?> p = MyProcessor.class;
		// Maj¹c za³adowan¹ klasê mo¿na siêgn¹æ do jej metod i je wywo³aæ. 
		try {
			// Pozyskujemy konstruktor
			Constructor<?> cp = p.getConstructor();
			// a nastêpnie tworzymy obiekt za³adowanej klasy 
			// (trzeba to zrobiæ, aby mo¿na by³o wywo³aæ metody instancyjne).
			Object o = cp.newInstance();
			
			
			
			// Poniewa¿ wiemy, ¿e za³adowana klasa implementuje interfejs processing.Processor, 
			// dlatego szukamy w tej klasie znanych metod.
			// Najpierw dowiadujemy siê, na czym polega algorytm przetwarzania wywo³uj¹c getInfo()
			Method getInfoMethod = p.getDeclaredMethod("getInfo");
			System.out.println((String)getInfoMethod.invoke(o));

			// Nastêpnie przesy³amy zadanie do wykonania metod¹ submitTask()
			// Method method = p.getDeclaredMethod("submitTask", new Class[] {String.class,
			// StatusListener.class});					
			Method submitTaskMethod = p.getDeclaredMethod("submitTask", String.class, StatusListener.class);
			
			// Aby wywo³aæ tê metodê nale¿y pos³u¿yæ siê odpowiednimi parametrami: task, sl
			// gdzie sl referencja do w³asnego s³uchacza - instancji klasy implementuj¹cej interfejs StatusListener.			
              
			// boolean b = (boolean) method.invoke(o,new Object[] {"Text to process", new
			// MyStatusListener()});
			boolean b = (boolean) submitTaskMethod.invoke(o, "Tekst na wejœcie", new MyStatusListener());

			// Implementacja metody submitTask w klasie MyProcessor jest asynchroniczna.
			// Do rozpoznania koñca przetwarzania i pobrania wyliczonego wyniku 
			// nale¿y wykorzystaæ metody s³uchacza.
			// Na razie piszemy tylko komunikat, ¿e zainicjowano przetwarzanie
			if (b)
				System.out.println("Processing started correctly");
			else
				System.out.println("Processing ended with an error");

			// Do rozpoznania koñca przetwarzania s³u¿y metoda statusChanged
			// s³uchacza MyStatusListener. Do tej metody dostarczana bêdzie instancja Status,
			// z której wyci¹gn¹æ mo¿na progress i taskId.
			// Jak progress dojdzie do koñca, bêdzie mo¿na pobraæ wynik przetwarzania
			// getod¹ getResult() z instancji klasy MyProcessor.
			// Jeœli z metody getResult() otrzyma siê coœ innego ni¿ null, to w³aœnie bêdzie to
            // wynik

			// Poniewa¿ mamy program konsolowy, asynchronicznoœæ wymaga uruchamieniam w¹tku
			// oczekuj¹cego na rezultat (sprawdzaj¹cy, czy przetwarzanie nie dobieg³o koñca).
			// W aplikacji okienkowej podobnie zadzia³a³by s³uchacz, który 
			// odpali³by pobranie rezultatu.

			
			Method getResultMethod = p.getDeclaredMethod("getResult");

			ExecutorService executor = Executors.newSingleThreadExecutor();
			
			// uruchom zadanie, które skoñczy siê, gdy result!=null
			executor.submit(() -> {
				String result = null;				
				while (true) {
					// System.out.println(scheduleFuture.isDone());
					
						try {
							Thread.sleep(800);

						// String result = (String) getResultMethod.invoke(o,new Object[] {});
							result = (String) getResultMethod.invoke(o);
						} catch (InterruptedException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						if (result != null) {
							System.out.println("Result: " + result);
							executor.shutdown();
							break;
						}
				}
			});

			System.out.println("main FINISHED");

		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
