package example;

import processing.Status;
import processing.StatusListener;

public class MyStatusListener implements StatusListener {
    // listener jest znany podczas kompilacji projektu
	// mo¿na wiêc wi¹zaæ siê w nim z interfejsem u¿ytkownika
	@Override
	public void statusChanged(Status s) {
			System.out.println("Progress:"+s.getProgress()+" TaskId:" +s.getTaskId());
	}

}
