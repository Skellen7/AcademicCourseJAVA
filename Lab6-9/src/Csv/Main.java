package Csv;

import java.io.IOException;
import java.util.Locale;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) throws IOException {
        /*CSVReader reader = new CSVReader("admin-units.csv",",",true);
        int i=0;
        while(reader.next() && i<100000){
            try {
                int id = reader.getInt("id");
                int parent = reader.getInt("parent");
                String name = reader.Get("name");
                double x1 = reader.getDouble("population");
                int admin_level = reader.getInt("admin_level");
                double area = reader.getDouble("area");
                double density = reader.getDouble("density");
                System.out.println(id + " " + parent + " " + name+" "+admin_level+" "+area+" "+density+" "+x1);
            }catch(NoSuchElementException err){}
        }
        */

        AdminUnitList list = new AdminUnitList();
        list.read("admin-units.csv");
        list.writeHTML();
    }
}
