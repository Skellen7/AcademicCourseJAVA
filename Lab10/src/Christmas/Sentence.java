package Christmas;

import java.awt.*;

public class Sentence implements XmasShape{
    int x;
    int y;
    double scale;
    double angle;
    Font font;
    String sentence;
    Color color;

    @Override
    public void render(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(this.font);
        g2d.setColor(this.color);
        g2d.drawString(this.sentence,this.x,this.y);

    }

    @Override
    public void transform(Graphics2D g2d) {
        //g2d.translate(0,0);
        //g2d.scale(scale,scale);
        g2d.rotate(this.angle);
    }

    public Sentence(int x, int y, Font font, String sentence, double angle, Color color){
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.angle = angle;
        this.color = color;
        this.font = font;
        this.sentence = sentence;
    }
}
