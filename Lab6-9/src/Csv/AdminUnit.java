package Csv;

import java.io.PrintStream;
import java.util.List;

public class AdminUnit {
    String name;
    int adminLevel;
    double population;
    double area;
    double density;
    AdminUnit parent;
    BoundingBox bbox = new BoundingBox();
    List<AdminUnit> children;


    @Override
    public String toString(){
        return name+" "+adminLevel+" "+population+" "+area+" "+density+" "+parent.toString()+" "+bbox.toString();
    }

    void fixMissingValues(){
        if(this.density==-1){
             AdminUnit tmp = this.parent;
             while(tmp.density!=-1){
                 tmp = tmp.parent;
             }
             this.density = tmp.density;
        }
        if(this.population==-1){
            this.population=this.area*this.density;
        }
    }

    public String getName() {
        return name;
    }

    public double getPopulation() {
        return population;
    }

    public double getArea() {
        return area;
    }

    public AdminUnit getParent() {
        return parent;
    }

    void writeHTML(PrintStream out){
        out.printf("<html><body>\n");
        //zapisujemy podstawowe informacje
        out.printf("<p> Name: + " + this.name + "<br/> Area: " + this.area + "<br/> Density: " + this.density +
                        "<br/>" + this.population);
        //wypisywanie linku do parenta
        out.printf("Link parent : <a href=\"" + parent.name.replaceAll("\\s+","") +
                ".html\">Klik</a><br/>");

        for(AdminUnit child : children){

        }

        out.printf("</p></body></html>");

        // zapisz niezbędne znaczniki HTML
        // dodaj tytuł i obrazek
        // dla każdej sekcji wywołaj section.writeHTML(out)
    }
}
