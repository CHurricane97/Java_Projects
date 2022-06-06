import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Malowanie extends JPanel implements MouseListener, MouseMotionListener {

    ScriptObjectMirror a;
    private boolean mouseLeftButton = false;
    int sqSize;

    public Malowanie(int sqSize) {
        super();
        this.sqSize = sqSize;
    }

    public void setA(ScriptObjectMirror a) {
        this.a = a;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        setBackground(Color.WHITE);
        Graphics2D drawImage = (Graphics2D) g;
        drawImage.setColor(Color.BLACK);
        int x = 10;
        int y = 10;
        for (Object i : (Object[]) a.values().toArray()[0]) {
            for (Object u : ((Object[]) i)) {

                if((Integer) u == 1) {
                    drawImage.setColor(Color.BLACK);
                    drawImage.fillRect(x, y, sqSize, sqSize);
                }
                drawImage.setColor(Color.GRAY);
                drawImage.drawRect(x, y, sqSize, sqSize);
                x += sqSize;
            }
            x = 10;
            y += sqSize;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftButton = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftButton = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftButton = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
