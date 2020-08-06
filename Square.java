import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.*;
import java.util.stream.*;


public class Square{
    int r = 0;
    int g = 0;
    int b = 0;
    Color c;

    public Square(Color c){
        this.c = new Color(c.getRed(),c.getGreen(),c.getBlue());
    }

    public Square(int r, int g, int b){
        c = new Color(r,g,b);
    }

    public void drawMe(Graphics gr, int x, int y){

        gr.setColor(c);
        gr.fillRect(x,y,15,15);
    }

    public Color getColor(){
        return c;
    }
}