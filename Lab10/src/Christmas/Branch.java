package Christmas;

import java.awt.*;
import java.util.ArrayList;

public class Branch implements XmasShape{
    int [] x;
    int [] y;
    double scale;
    GradientPaint paint;

    @Override
    public void render(Graphics2D g2d) {
        g2d.setPaint(paint);
        // ustaw kolor obramowania
        g2d.fillPolygon(x,y,x.length);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(0,0);
        g2d.scale(scale,scale);
    }

    public Branch(int x[], int y[], double scale, GradientPaint paint){
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.paint = paint;
    }
}
