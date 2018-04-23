package Christmas;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_MITER;



public class DrawPanel extends JPanel{

    List<XmasShape> shapes = new ArrayList<>();

    DrawPanel(){
        setBackground(new Color(21, 33, 128));
        //setOpaque(true);
        this.backgrounds();
        this.branches();
        this.bubbles();
        this.stars();
        this.sentence();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(XmasShape s:shapes){
            s.draw((Graphics2D)g);
        }
    }

    void bubbles(){

        Bubble moon = new Bubble(100,50,1, Color.YELLOW, Color.YELLOW);
        Bubble moon_offset = new Bubble(120,60,1,new Color(21, 33, 128), new Color(21, 33, 128));
        shapes.add(moon);
        shapes.add(moon_offset);

        Bubble[] bubbles = {
                new Bubble(680,400,0.2,new Color(180, 8, 20),new Color(0,20,128)),
                new Bubble(600,350,0.2,new Color(180, 8, 20),new Color(0,20,128)),
                new Bubble(660,300,0.2,new Color(180, 8, 20),new Color(0,20,128)),
                new Bubble(630,220,0.2,new Color(180, 8, 20),new Color(0,20,128)),
                new Bubble(700,500,0.2,new Color(180, 8, 20),new Color(0,20,128)),
                new Bubble(620,450,0.2,new Color(180, 8, 20),new Color(0,20,128)),
                new Bubble(550,510,0.2,new Color(180, 8, 20),new Color(0,20,128)),

        };

        for(int i=0; i<bubbles.length; i++){
            shapes.add(bubbles[i]);
        }

    }

    void branches(){

        int[] x_stomp = {610,610,660,660};
        int[] y_stomp = {620,550,550,620};

        Branch stomp = new Branch(x_stomp,y_stomp,1,new GradientPaint(0,500,new Color(55,52,15),0,600, new Color(70, 68, 19)));
        shapes.add(stomp);

        int x_offset = 15;
        int y_offset = 60;
        for(int i=0; i<8; i++){
            int x [] = {600-i*x_offset, 640, 680+i*x_offset};
            int y [] = {150+i*y_offset, 100+i*y_offset/2 ,150+i*y_offset};
            Branch b = new Branch(x,y,1,new GradientPaint(0,0,new Color(0,255,0),0,400, new Color(0, 64, 14)));
            shapes.add(b);
        }
    }

    void stars(){
        Star tree_star = new Star(640, 100, 10, 30, 12, 0, Color.YELLOW);
        shapes.add(tree_star);
        Star bigStar = new Star(1000,150,50,100,5,0,Color.WHITE);
        shapes.add(bigStar);

        Star stars[] = {
                new Star(50,50,7,15,5,0,Color.YELLOW),
                new Star(500,120,7,15,5,50,Color.YELLOW),
                new Star(300,200,7,15,5,30,Color.YELLOW),
                new Star(450,30,7,15,5,70,Color.YELLOW),
                new Star(800,240,7,15,5,90,Color.YELLOW),
                new Star(920,300,7,15,5,120,Color.YELLOW),
                new Star(1100,330,7,15,5,15,Color.YELLOW),
                new Star(100,350,7,15,5,80,Color.YELLOW),
                new Star(250,300,7,15,5,40,Color.YELLOW),
                new Star(500,250,7,15,5,15,Color.YELLOW),
                new Star(1240,70,7,15,5,25,Color.YELLOW)

        };

        for(int i=0; i<stars.length; i++){
            shapes.add(stars[i]);
        }


    }

    void backgrounds(){
        int[] x1 = {0,0,100,200,300,400,500,600,700,800,900,1000,1100,1280,1280};
        int[] y1 = {720, 400,457,420,390,412,380,350,420,445,410,435,400,372,720};
        Background b1 = new Background(x1,y1, Color.LIGHT_GRAY);
        shapes.add(b1);
        int[] x2 = {0,0,150,300,450,600,750,900,1050,1280,1280};
        int[] y2 = {720,500,490,520,545,510,475,490,510,530,720};
        Background b2 = new Background(x2,y2,new Color(145, 144, 145));
        shapes.add(b2);
    }

    void sentence(){
        Font font = new Font("Serif", Font.PLAIN, 28);
        Sentence sentence = new Sentence(900,-370,font,"Xmas",2*Math.PI/12, new Color(21, 33, 128));
        shapes.add(sentence);
    }



}
