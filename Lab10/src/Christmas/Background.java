package Christmas;

import java.awt.*;

public class Background implements XmasShape{
    int [] x;
    int [] y;
    double scale;
    Color color;

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(color);
        // ustaw kolor obramowania
        g2d.fillPolygon(x,y,x.length);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(0,0);
    }

    public Background(int x[], int y[], Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
