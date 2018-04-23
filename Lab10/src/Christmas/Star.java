package Christmas;

import java.awt.*;
import java.awt.geom.Path2D;

public class Star implements XmasShape{
    Shape path;
    Color color;


    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(color);
        // ustaw kolor obramowania
        //g2d.fillPolygon(x,y,x.length);
        g2d.fill(this.path);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(0,0);
        g2d.scale(1,1);
    }

    public Star(double centerX, double centerY,
                double innerRadius, double outerRadius, int numRays,
                double startAngleRad, Color color){
        this.path = createStar(centerX, centerY, innerRadius, outerRadius, numRays, startAngleRad);
        this.color = color;
    }


private Shape createStar(double centerX, double centerY,
                                double innerRadius, double outerRadius, int numRays,
                                double startAngleRad)
{
    Path2D path = new Path2D.Double();
    double deltaAngleRad = Math.PI / numRays;
    for (int i = 0; i < numRays * 2; i++)
    {
        double angleRad = startAngleRad + i * deltaAngleRad;
        double ca = Math.cos(angleRad);
        double sa = Math.sin(angleRad);
        double relX = ca;
        double relY = sa;
        if ((i & 1) == 0)
        {
            relX *= outerRadius;
            relY *= outerRadius;
        }
        else
        {
            relX *= innerRadius;
            relY *= innerRadius;
        }
        if (i == 0)
        {
            path.moveTo(centerX + relX, centerY + relY);
        }
        else
        {
            path.lineTo(centerX + relX, centerY + relY);
        }
    }
    path.closePath();
    return path;
}
}
