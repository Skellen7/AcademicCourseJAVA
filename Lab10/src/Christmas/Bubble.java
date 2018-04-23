package Christmas;

import java.awt.*;

public class Bubble implements XmasShape {
    int x;
    int y;
    double scale;
    Color lineColor;
    Color fillColor;

    @Override
    public void render(Graphics2D g2d) {
        // ustaw kolor wypełnienia
        g2d.setColor(this.lineColor);
        g2d.fillOval(0,0,100,100);
        g2d.setColor(this.fillColor);
        // ustaw kolor obramowania
        g2d.drawOval(0,0,100,100);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    public Bubble(int x, int y, double scale, Color lineColor, Color fillColor){
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }
}
