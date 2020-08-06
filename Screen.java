import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.logging.*;
import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class Screen extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

    private Square[][] grid;

    private Square[][] colors;

    private Boolean mouseDown = false;

    private Color drawColor;

    private Boolean fill = false;

    private Color colorSelected;

    private int mode = 0;

    private int mX;

    private int mY;

    private Color c;

    private JButton eraseButton;

    private JButton saveButton;

    private JButton loadButton;

    private JTextField fileName;

    private JButton fillButton;

    private JButton drawButton;

    private JButton selection1;

    private JButton selection2;

    private JButton modeButton;

    private JButton clearButton;

    private Boolean eraser = false;


    public Screen(){
        setLayout(null);

        grid = new Square[32][32];
        colors = new Square[15][15];
        c = new Color(255,255,255);
        drawColor = new Color(0,0,0);

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new Square(c);
            }
        }

        int num1 = 0;
        int num2 = 0;


        for(int i = 0; i < colors.length; i++){
            for(int j = 0; j < colors[i].length; j++){
                colors[i][j] = new Square(num1,num2,255 - num1);
                num2+=17;
            }
            num2 = 0;
            num1+=17;
        }

        modeButton = new JButton("Mode");
        modeButton.setBounds(542,300,240,20);
        modeButton.addActionListener(this);
        this.add(modeButton);

        eraseButton = new JButton("Eraser");
        eraseButton.setBounds(542,330,240,20);
        eraseButton.addActionListener(this);
        this.add(eraseButton);

        fillButton = new JButton("Fill");
        fillButton.setBounds(542,360,240,20);
        fillButton.addActionListener(this);
        this.add(fillButton);

        drawButton = new JButton("Draw");
        drawButton.setBounds(542,360,240,20);
        drawButton.addActionListener(this);

        saveButton = new JButton("Save");
        saveButton.setBounds(542,390,112,20);
        saveButton.addActionListener(this);
        this.add(saveButton);

        loadButton = new JButton("Load");
        loadButton.setBounds(669,390,112,20);
        loadButton.addActionListener(this);
        this.add(loadButton);
        //Textfield for the name of the img
        fileName = new JTextField();
        fileName.setBounds(542,420,240,20);
        this.add(fileName);
        //Button to clear grid
        clearButton = new JButton("Clear");
        clearButton.setBounds(542,507,240,20);
        clearButton.addActionListener(this);
        this.add(clearButton);
        //Button to show colors
        selection1 = new JButton("1");
        selection1.setBounds(542,270,112,20);
        selection1.addActionListener(this);
        this.add(selection1);
        //Button to show black shades
        selection2 = new JButton("2");
        selection2.setBounds(669,270,112,20);
        selection2.addActionListener(this);
        this.add(selection2);

        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public Dimension getPreferredSize() {
        //Sets the size of the panel
        return new Dimension(796, 542);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(mode == 0){
            g.setColor(Color.BLACK);
            g.fillRect(0,0,796,542);

            g.setColor(Color.WHITE);
            g.fillRect(640,450,44,44);
            g.setColor(drawColor);
            g.fillRect(642,452,40,40);
        } else if(mode == 1){
            g.setColor(new Color(186, 187, 188));
            g.fillRect(0,0,796,542);
            g.setColor(Color.GRAY);
            g.fillRect(640,450,44,44);
            g.setColor(drawColor);
            g.fillRect(642,452,40,40);
        }


        int x = 15;
        int y = 15;


        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j].drawMe(g,x,y);
                x+=16;
            }
            x = 15;
            y+=16;
        }

        x = 542;
        y = 15;


        for(int i = 0; i < colors.length; i++){
            for(int j = 0; j < colors[i].length; j++){
                colors[i][j].drawMe(g,x,y);
                x+=16;
            }
            x = 542;
            y+=16;
        }


    }

    public void mousePressed(MouseEvent e) {

        int row = 0;
        int col = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (e.getX() - 15 >= (i * 16) && e.getX() - 15 <= ((i * 16) + 16) && e.getY() - 15 >= (j * 16) && e.getY() - 15 <= ((j * 16) + 16)) {
                    row = j;
                    col = i;
                    mouseDown = true;
                    if(fill){
                        colorSelected = grid[j][i].getColor();
                        System.out.println(colorSelected);
                    }
                }
            }
        }

        if (mouseDown) {
            if (eraser) {
                grid[row][col] = new Square(c);
            } else {
                grid[row][col] = new Square(drawColor);

            }


        }

        if(fill) {
            if(row >= 0 && row <= 32 && col >= 0 && col <= 32){
               fill(row, col); 
            }
            
        }



        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                if (e.getX() - 542 >= (i * 16) && e.getX() - 542 <= ((i * 16) + 16) && e.getY() - 15 >= (j * 16) && e.getY() - 15 <= ((j * 16) + 16)) {
                    drawColor = colors[j][i].getColor();
                }
            }
        }


        repaint();

    }

    public void mouseReleased(MouseEvent e) { mouseDown = false; }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) { mouseDown = false; }

    public void mouseClicked(MouseEvent e) {}

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == eraseButton){

            if(eraser){
                eraser = false;
            } else {
                eraser = true;
            }
        } else if(e.getSource() == clearButton){

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {

                    grid[j][i] = new Square(c);
                }
            }
            repaint();
        } else if(e.getSource() == selection1){
            int num1 = 0;
            int num2 = 0;


            for(int i = 0; i < colors.length; i++){
                for(int j = 0; j < colors[i].length; j++){
                    colors[i][j] = new Square(num1,num2,255 - num1);
                    num2+=17;
                }
                num2 = 0;
                num1+=17;
            }
        } else if(e.getSource() == selection2){
            int num1 = 0;
            int num2 = 0;


            for(int i = 0; i < colors.length; i++){
                for(int j = 0; j < colors[i].length; j++){
                    colors[i][j] = new Square(num1,num1,num1);
                    num2+=17;
                }
                num2 = 0;
                num1+=17;
            }
        } else if(e.getSource() == fillButton){
            fill = true;
            eraser = false;
            this.add(drawButton);
            this.remove(fillButton);
        } else if(e.getSource() == saveButton){
            saveImage();
        } else if(e.getSource() == loadButton){
            loadImage();
        } else if(e.getSource() == modeButton){
            if(mode == 1){
                mode = 0;
            } else if(mode == 0){
                mode = 1;
            }
        } else if(e.getSource() == drawButton){
            fill = false;
            eraser = false;
            this.add(fillButton);
            this.remove(drawButton);
        }

        repaint();


    }

    public void mouseDragged(MouseEvent e){
        int row = 0;
        int col = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (e.getX() - 15 >= (i * 16) && e.getX() - 15 <= ((i * 16) + 16) && e.getY() - 15 >= (j * 16) && e.getY() - 15 <= ((j * 16) + 16)) {
                    mouseDown = true;
                    row = j;
                    col = i;
                }
            }
        }

        if (mouseDown) {
            if (eraser){
                grid[row][col] = new Square(c);
             } else {
                grid[row][col] = new Square(drawColor);

            }


        }
        repaint();
    }

    public void fill(int row, int col){
        if(row > 0 && row < 32 && col > 0 && col < 32){
            fillRow(row,col);
            fillCol(row,col);
            repaint();
        }
        
    }

    public void fillRow(int row, int col){
        for(int i = col + 1; i < 32; i++){
            if(grid[row][i].getColor().equals(colorSelected)){
                grid[row][i] = new Square(drawColor);
                fillCol(row,i);
            } else {
                break;
            }
        }

        for(int i = col - 1; i >= 0; i--){
            if(grid[row][i].getColor().equals(colorSelected)){
                grid[row][i] = new Square(drawColor);
                fillCol(row,i);
            } else {
                break;
            }
        }
    }

    public void fillCol(int row,int col){
        for(int i = row + 1; i < 32; i++){
            if(grid[i][col].getColor().equals(colorSelected)){
                grid[i][col] = new Square(drawColor);
                fillRow(i,col);
            } else {
                break;
            }
        }

        for(int i = row - 1; i >= 0; i--){
            if(grid[i][col].getColor().equals(colorSelected)){
                grid[i][col] = new Square(drawColor);
                fillRow(i,col);
            } else {
                break;
            }
        }
    }



    public void mouseMoved(MouseEvent e){
    }


    public void saveImage(){
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if(grid[y][x].getColor().equals(c)){
                    img.setRGB(x,y,Color.WHITE.getRGB());
                } else {
                    img.setRGB(x, y, grid[y][x].getColor().getRGB());
                }
            }
        }
        File outputfile = new File(fileName.getText());
        try {
            ImageIO.write(img, "png", outputfile);
        } catch(IOException e){

        }
    }

    public void loadImage(){
        BufferedImage img2 = new BufferedImage(32, 32, BufferedImage.TYPE_3BYTE_BGR);
        try {
            img2 = ImageIO.read(new File(fileName.getText()));
        } catch(IOException e){

        }

        for (int x = 0; x < img2.getWidth(); x++) {
            for (int y = 0; y < img2.getHeight(); y++) {
                int rgb = img2.getRGB(x,y);
                grid[y][x] = new Square(new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF));
            }
        }
    }
}
