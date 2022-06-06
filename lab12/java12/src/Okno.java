import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class Okno extends JFrame {

    private Malowanie paintWindow;
    private ScriptEngine scriptEngine;
    private ScriptObjectMirror automat;
    private Thread thr;
    private boolean koniec = false;

    public Okno(int sizeX, int sizeY, int speed, int sqSize, boolean mrowka) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        setTitle("Automat Komórkowy");
        int y1 =  sqSize*sizeY+75;
        int x1 = sqSize*sizeX+35;

        setSize(x1, y1);
        setLayout(new BorderLayout());

        paintWindow = new Malowanie(sqSize);
        paintWindow.setBorder(new EmptyBorder(0, 0, 0, 0));

        scriptEngine = new ScriptEngineManager().getEngineByName("Nashorn");
        if(mrowka) scriptEngine.eval(new FileReader("Mrowka.js"));
        else scriptEngine.eval(new FileReader("GraWZycie.js"));

        Invocable invocable = (Invocable) scriptEngine;

        Object[][] o = new Object[sizeY][sizeX];
        Random r = new Random();
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                if(mrowka) o[i][j] = 0;
                else o[i][j] = r.nextInt(2);
            }
        }

        if(mrowka) {

            automat = (ScriptObjectMirror) invocable.invokeFunction("nextGeneration", new Object[]{o, r.nextInt(sizeX),r.nextInt(sizeY),5});
        }else{
            automat = (ScriptObjectMirror) invocable.invokeFunction("nextGeneration", new Object[]{o});
        }

        paintWindow.setA(automat);

        add(paintWindow, BorderLayout.CENTER);

        thr = new Thread(() -> {
            while (!koniec){
                try {
                    repaint();
                    Thread.sleep(speed);
                    if(mrowka) {
                        automat = (ScriptObjectMirror) invocable.invokeFunction("nextGeneration", new Object[]{
                                automat.values().toArray()[0],
                                automat.values().toArray()[1],
                                automat.values().toArray()[2],
                                automat.values().toArray()[3]
                        });
                    }else{
                        automat = (ScriptObjectMirror) invocable.invokeFunction("nextGeneration", new Object[]{
                                automat.values().toArray()[0]
                        });
                    }
                    paintWindow.setA(automat);
                } catch (InterruptedException | NoSuchMethodException | ScriptException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thr.start();

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                koniec = true;
                try {
                    thr.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                paintWindow = null;
                scriptEngine = null;
                automat = null;
                thr = null;
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
