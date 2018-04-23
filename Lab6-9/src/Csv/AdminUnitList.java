package Csv;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

import static java.lang.System.out;

public class AdminUnitList {

    List<AdminUnit> units = new ArrayList<>();

    public void add(AdminUnit unit){
        units.add(unit);
    }

    public void read(String filename){
        try {

            Map<Long, AdminUnit> numberToAdminUnit = new HashMap<>();
            Map<AdminUnit, Long> adminUnitToNumber = new HashMap<>();

            Map<Long,List<AdminUnit>> parentid2child = new HashMap<>();

            CSVReader reader = new CSVReader(filename,",",true);
            int i=0;
            while(reader.next() && i<100){
                AdminUnit tmp = new AdminUnit();
                tmp.name = reader.Get("name");
                tmp.adminLevel = reader.getInt("admin_level");
                tmp.population = reader.getDouble("population");
                tmp.area = reader.getDouble("area");
                tmp.density = reader.getDouble("density");
                tmp.parent = null;

                //wczytywanie wartosci dla Bounding Boxa
                tmp.bbox.addPoint(reader.getDouble("x1"), reader.getDouble("y1"));
                tmp.bbox.addPoint(reader.getDouble("x2"), reader.getDouble("y2"));
                tmp.bbox.addPoint(reader.getDouble("x3"), reader.getDouble("y3"));
                tmp.bbox.addPoint(reader.getDouble("x4"), reader.getDouble("y4"));
                tmp.bbox.addPoint(reader.getDouble("x5"), reader.getDouble("y5"));

                //przypisanie wartosci parent do odpowiednich list
                numberToAdminUnit.put(reader.getLong("id"),tmp);
                adminUnitToNumber.put(tmp,reader.getLong("parent"));

                units.add(tmp);
            }
            //for(AdminUnit unit : units){
            //    if(adminUnitToNumber.get(unit)!=1) unit.parent = numberToAdminUnit.get(adminUnitToNumber.get(unit));
            //}

            for(Map.Entry<Long, AdminUnit> entry : numberToAdminUnit.entrySet()){

                //ustawiamy referencje w obiektach units
                if(adminUnitToNumber.get(entry.getValue()) == -1){
                    units.get(units.indexOf(entry.getValue())).parent = null;
                }
                else units.get(units.indexOf(entry.getValue())).parent = numberToAdminUnit.get(adminUnitToNumber.get(entry.getValue()));

                //  dodaje child
                if(parentid2child.get(entry.getKey()) == null){
                    List<AdminUnit> tmp = new ArrayList<AdminUnit>();
                    tmp.add(units.get(units.indexOf(entry.getValue())));
                    parentid2child.put(entry.getKey(), tmp);
                }

                parentid2child.get(entry.getKey()).add(units.get(units.indexOf(entry.getValue())));
                units.get(units.indexOf(entry.getValue())).children = parentid2child.get(entry.getKey());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fixMissingValues(AdminUnit unit) {
        if (unit.population == -1.0) {
            if (unit.parent.population == -1.0)
                fixMissingValues(unit.parent);
            unit.density = unit.parent.density;
            unit.population = unit.area * unit.density;
        }
    }


    /**
     * Wypisuje zawartość korzystając z AdminUnit.toString()
     * @param out
     */
    void list(PrintStream out){
        for(AdminUnit unit: units){
            out.println(unit.toString());
        }
    }
    /**
     * Wypisuje co najwyżej limit elementów począwszy od elementu o indeksie offset
     * @param out - strumień wyjsciowy
     * @param offset - od którego elementu rozpocząć wypisywanie
     * @param limit - ile (maksymalnie) elementów wypisać
     */
    void list(PrintStream out, int offset, int limit ){
        for(int i=offset; i<limit+offset; i++){
            AdminUnit unit = units.get(i);
            out.println(unit.toString());
        }
    }

    /**
     * Zwraca nową listę zawierającą te obiekty AdminUnit, których nazwa pasuje do wzorca
     * @param pattern - wzorzec dla nazwy
     * @param regex - jeśli regex=true, użyj finkcji String matches(); jeśli false użyj funkcji contains()
     * @return podzbiór elementów, których nazwy spełniają kryterium wyboru
     */
    AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList ret = new AdminUnitList();
        // przeiteruj po zawartości units
        // jeżeli nazwa jednostki pasuje do wzorca dodaj do ret
        for(AdminUnit unit : units){
            if(regex){
                if(unit.name.matches(pattern)) ret.units.add(unit);
            }
            else{
                if(unit.name.contains(pattern)) ret.units.add(unit);
            }
        }
        return ret;
    }

    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * Czyli sąsiadami wojweództw są województwa, powiatów - powiaty, gmin - gminy, miejscowości - inne miejscowości
     * @param unit - jednostka, której sąsiedzi mają być wyznaczeni
     * @param maxdistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit,
     *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList getNeighbors(AdminUnit unit, double maxdistance){
        if(unit.bbox.isEmpty()) throw new IllegalThreadStateException("You can't solve the distance between points in an empty Bounding Box");
        AdminUnitList neighbours = new AdminUnitList();

        double t1 = System.nanoTime() / 1e6;

        for(AdminUnit neighbour : units){
            if(neighbour.bbox.isEmpty()) throw new IllegalThreadStateException("You can't solve the distance between points in an empty Bounding Box");
            else{
                if(unit.adminLevel==8){
                    if(neighbour.adminLevel==8 && unit.bbox.distanceTo(neighbour.bbox) < maxdistance){
                        neighbours.add(neighbour);
                    }
                }
                else{
                    if(unit.adminLevel == neighbour.adminLevel && unit.bbox.intersects(neighbour.bbox)){
                        neighbours.add(neighbour);
                    }
                }
            }
        }

        double t2 = System.nanoTime() / 1e6;

        //System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);

        return neighbours;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */
    AdminUnitList sortInplaceByName(){
       units.sort(new AdminUnitComparator());
        return this;
    }

    AdminUnitList sortInplaceByArea(){
        units.sort(new Comparator<AdminUnit>() {
            @Override
            public int compare(AdminUnit o1, AdminUnit o2) {
                return Double.compare(o1.getArea(), o2.getArea());
            }
        });
        return this;
    }

    public class AdminUnitComparator implements Comparator<AdminUnit> {
        @Override
        public int compare(AdminUnit o1, AdminUnit o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */


    AdminUnitList sortInplaceByPopulation(){
        units.sort(Comparator.comparingDouble(AdminUnit::getPopulation));
    return this;
    }

    AdminUnitList sortInplace(Comparator<AdminUnit> cmp){
        units.sort(cmp);
        return this;
    }

    AdminUnitList sort(Comparator<AdminUnit> cmp){
        // Tworzy wyjściową listę
        // Kopiuje wszystkie jednostki
        // woła sortInPlace
        AdminUnitList tmp = new AdminUnitList();
        tmp.units = new ArrayList<>(this.units);
        tmp.units.sort(cmp);
        return tmp;
    }

    /**
     *
     * @param pred referencja do interfejsu Predicate
     * @return nową listę, na której pozostawiono tylko te jednostki,
     * dla których metoda test() zwraca true
     */
    AdminUnitList filter(Predicate<AdminUnit> pred) {
        AdminUnitList tmp = new AdminUnitList();
        for(AdminUnit unit : this.units){
            if(pred.test(unit)) tmp.add(unit);
        }
        return tmp;
    }

    //jednostki zaczynajace sie na K
    AdminUnitList filterByK(){
        return filter(a -> a.getName().startsWith("K"));
    }

    //powiaty wojewodztwa malopolskiego
    AdminUnitList filterByParentMalopolskie(){
        return filter(a -> a.getParent().getName().contains("małopolskie") || a.getName().startsWith("powiat"));
    }

    //jednostki ktorych area jest wieksza od 50
    AdminUnitList filterAreaBiggerThan50(){
        return filter(a -> a.getArea()>50);
    }

    /**
     * Zwraca co najwyżej limit elementów spełniających pred
     * @param pred - predykat
     * @param limit - maksymalna liczba elementów
     * @return nową listę
     */
    AdminUnitList filter(Predicate<AdminUnit> pred, int limit){
        AdminUnitList tmp = new AdminUnitList();
        int i=0;
        for(AdminUnit unit : this.units){
            if(pred.test(unit)) tmp.add(unit);
            i++;
            if(i>limit) break;
        }
        return tmp;
    }

    /**
     * Zwraca co najwyżej limit elementów spełniających pred począwszy od offset
     * Offest jest obliczany po przefiltrowaniu
     * @param pred - predykat
     * @param offset - od którego elementu
     * @param limit - maksymalna liczba elementów
     * @return nową listę
     */
    AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit){
        AdminUnitList tmp = new AdminUnitList();
        int i=offset;
        for(AdminUnit unit : this.units){
            if(pred.test(unit)) tmp.add(unit);
            i++;
            if(i>limit+offset) break;
        }
        return tmp;
    }

    void writeHTML() throws FileNotFoundException {
        for(AdminUnit unit : this.units){
            //plki o nazwie "name.html" (bez polskich znakow)
            PrintWriter file = new PrintWriter(unit.name.replaceAll("\\s+","") + ".html");
            file.println("<html><body>");
            //zapisujemy podstawowe informacje
            file.println("<p> Name:" + unit.name + "<br/> Area: " + unit.area + "<br/> Density: " + unit.density +
                    "<br/>" + unit.population);
            //wypisywanie linku do parenta
            if(unit.parent != null){
                file.println("Link parent : <a href=\"" + unit.parent.name.replaceAll("\\s+","") +
                        ".html\">Klik</a><br/>");
            }
            else{
                file.println("Brak parenta<br/>");
            }



            //wypisywanie dzieci
            for(AdminUnit child : unit.children){
                file.println("Link child : <a href=\"" + child.name.replaceAll("\\s+","") +
                        ".html\">Klik</a><br/>");
            }

            //wypisywanie neighbours
            AdminUnitList neighbours = new AdminUnitList();
            neighbours.getNeighbors(unit, 5);
            for(AdminUnit neighbour : neighbours.units){
                file.println("Link neighbour : <a href=\"" + neighbour.name.replaceAll("\\s+","") +
                        ".html\">Klik</a><br/>");
            }

            file.println("</p></body></html>");

            file.close();
        }
    }








}

