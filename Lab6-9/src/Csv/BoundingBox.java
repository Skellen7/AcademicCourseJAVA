package Csv;

public class BoundingBox {
    double xmin = -1;
    double ymin = -1;
    double xmax = -1;
    double ymax = -1;

    /**
     * Powiększa BB tak, aby zawierał punkt (x,y)
     * @param x - współrzędna x
     * @param y - współrzędna y
     */
    void addPoint(double x, double y){
        if(this.isEmpty()){
            this.xmin = x;
            this.xmax = x;
            this.ymin = y;
            this.ymax = y;
        }
        else {
            if (x < this.xmin) this.xmin = x;
            else if(x > this.xmax) this.xmax = x;
            if (y < this.ymin) this.ymin = y;
            else if (y > this.ymax) this.ymax = y;
        }
    }

    /**
     * Sprawdza, czy BB zawiera punkt (x,y)
     * @param x
     * @param y
     * @return
     */
    boolean contains(double x, double y){
        return x>=xmin && x<=xmin && y>=ymin && y<=ymax;
    }

    /**
     * Sprawdza czy dany BB zawiera bb
     * @param bb
     * @return
     */
    boolean contains(BoundingBox bb){
        return this.contains(bb.xmin, bb.ymin) &&
                this.contains(bb.xmin, bb.ymax) &&
                this.contains(bb.xmax, bb.ymin) &&
                this.contains(bb.xmax, bb.ymax);
    }

    /**
     * Sprawdza, czy dany BB przecina się z bb
     * @param bb
     * @return
     */
    boolean intersects(BoundingBox bb){
        return this.contains(bb.xmin, bb.ymin) ||
                this.contains(bb.xmin, bb.ymax) ||
                this.contains(bb.xmax, bb.ymin) ||
                this.contains(bb.xmax, bb.ymax);
    }
    /**
     * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
     * @param bb
     * @return
     */
    BoundingBox add(BoundingBox bb){
        if(this.contains(bb)) return this;
        else {
            if (bb.xmin < this.xmin) this.xmin = bb.xmin;
            if (bb.xmax > this.xmax) this.xmax = bb.xmax;
            if (bb.ymin < this.xmin) this.ymin = bb.ymin;
            if (bb.ymax > this.ymax) this.ymax = bb.ymax;
        }
        return this;
    }
    /**
     * Sprawdza czy BB jest pusty
     * @return
     */
    boolean isEmpty(){
        return this.xmin == -1 && this.xmax == -1 && this.ymin== -1 && this.ymax== -1;
    }

    /**
     * Oblicza i zwraca współrzędną x środka
     * @return if !isEmpty() współrzędna x środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterX(){
        if(this.isEmpty())throw new IllegalThreadStateException("You can't solve the x center of an empty Bounding Box");
        else return (this.xmax+this.xmin)/2;
    }
    /**
     * Oblicza i zwraca współrzędną y środka
     * @return if !isEmpty() współrzędna y środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterY(){
        if(this.isEmpty())throw new IllegalThreadStateException("You can't solve the y center of an empty Bounding Box");
        else return (this.ymax-this.ymin)/2;
    }

    /**
     * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
     * @param bbx prostokąt, do którego liczona jest odległość
     * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
     * Ze względu na to, że są to współrzędne geograficzne, zamiast odległosci euklidesowej możesz użyć wzoru haversine
     * (ang. haversine formula)
     */
    double distanceTo(BoundingBox bbx){
        if(this.isEmpty() || bbx.isEmpty())throw new IllegalThreadStateException("You can't solve the distance between points in an empty Bounding Box");
        else return Haversine.distance(this.getCenterX(),this.getCenterY(),bbx.getCenterX(),bbx.getCenterY());
    }

    public String toString(){
        return xmin+" "+ymin+" "+xmax+" "+ymax+'\n';
    }
}
