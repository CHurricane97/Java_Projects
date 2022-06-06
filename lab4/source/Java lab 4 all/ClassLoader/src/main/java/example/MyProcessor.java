package example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


import processing.Processor;
import processing.Status;
import processing.StatusListener;

/**
 * Klasa procesora symuluj¹ca d³ugotrwa³e przetwarzanie
 * @author tkubik
 *
 */
		
public class MyProcessor implements Processor {
	
	private static int taskId=0;
	private String result = null;

	@Override
	public boolean submitTask(String task, StatusListener sl) {
		taskId++;
		AtomicInteger ai = new AtomicInteger(0);
		 	
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		// Aby zasymulowaæ d³ugotrwa³e przetwarzanie uruchamiane s¹ w nieskoñczonoœæ
		// krótkie zadania inkrementuj¹ce licznik i nie zwracaj¹ce wartoœci.
		// W³aœciwie powinno siê przetwarzaæ przekazywany task, ale tutaj nie jest to robione.
		//
		// ScheduledFuture<?> scheduleFuture = executorService.scheduleAtFixedRate(

		executorService.scheduleAtFixedRate(
				()->{
					System.out.println("running"); // do debbugowania
		            ai.incrementAndGet();
		            sl.statusChanged(new Status(taskId,ai.get()));		    		
				}, 
				1, 10, TimeUnit.MILLISECONDS);	
		
		// Powy¿sze mo¿na by³oby zrobiæ w pêtli 
		//  for(int i=1; i<=100; i++){
		//     try {
		//         ai.incrementAndGet();
		//        Thread.sleep(1000);
		//     } catch(InterruptedException e){System.out.println(e);}
        //     sl.statusChanged(new Status(taskId,ai.get()));
		// ale zrobiono to inaczej. 
		
		// Poniewa¿ zapuszczono egzekutor, trzeba poczekaæ, a¿ przekrêci 
		// siê w nim 100 zadañ, po czym nale¿y zamkn¹æ serwis egzekutora 
		// i zakoñczy dzia³anie samego egzekutora.
		// Mo¿na to zrobiæ "na zewn¹trz", w kolejnym egzekutorze.
		// Przyk³ad ten nie jest mo¿e najlepszy, ale chodzi³o w nim 
		// o pokazanie synchronizacji przez zmienn¹ wspó³dzielon¹.

		ExecutorService executor = Executors.newSingleThreadExecutor();
		// uruchom zadanie, które skoñczy siê, gdy licznik przekroczy wartoœæ 100
		executor.submit(() -> {
		      while (true) {
		          //System.out.println(scheduleFuture.isDone()); will always print false
		          try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		          if (ai.get() >= 100) { 
		        	  // przekrêciliœmy 100 razy, 
		        	  // mo¿emy zwróciæ wynik przetwarzania
		        	 // tutaj "przetwarzanie" polega na zamianie literek na du¿e
		        	  result = task.toUpperCase(); 
		              System.out.println("finished");
		              //scheduleFuture.cancel(true);
		              executorService.shutdown();
		              executor.shutdown();
		              break;
		          }
		      }			
			});
		
		// Jeœli chcielibyœmy zaznaczyæ, ¿e coœ posz³o nie tak 
		// nale¿a³oby zwróciæ false
		// (np. gdy ostatnie przetwarzanie jeszcze nie dobieg³o koñca)
		// 
		// Jeœli wszystko posz³o ok, wtedy zwracane jest true
	    //
		// Uwaga: przypominaj¹c - implementacja "przetwarzania" jest asynchroniczna,
		//        zwrócenie wartoœci true nale¿y interpretowaæ jako informacjê o tym,
		//        ¿e zlecenie przetwarzania siê uda³o.
		return true;
	}

	@Override
	public String getInfo() {
		return "Zamiana literek na du¿e";
	}

	@Override
	public String getResult() {
		return result;
	}

}
