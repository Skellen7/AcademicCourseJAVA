package Csv;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;

import static org.junit.Assert.*;

public class CSVReaderTest {

    @Test
    public void TestCSVReader() throws Exception {
        CSVReader reader = new CSVReader("with-header.csv", ";", true);
        assertEquals(true, reader.hasHeader);
        assertEquals(new String(";"), reader.delimiter);
        assertEquals("nazwisko", reader.columnLabels.get(2));
    }

    @Test
    public void TestCSVReaderNoHeader() throws Exception {
        CSVReader reader = new CSVReader("with-header.csv", ";");
        assertEquals(false, reader.hasHeader);
    }

    @Test
    public void TestCSVReaderNoDelimiter() throws Exception {
        CSVReader reader = new CSVReader("with-header.csv");
        assertEquals(new String(";"), reader.delimiter);
    }

    @Test
    public void next() throws Exception {
        CSVReader reader = new CSVReader("with-header.csv", ";", true);
        while (reader.next()) {
            int id = reader.getInt(0);
            String name = reader.Get(3);
            double fare = reader.getDouble(5);
            System.out.printf(Locale.US, "%d %s %f", id, name, fare);
            System.out.println();
        }
    }


    @Test
    public void getColumnLabels() throws Exception {
        CSVReader reader = new CSVReader("with-header.csv", ";", true);
        assertEquals("nrdomu", reader.columnLabels.get(4));
    }

    @Test
    public void titanic() throws Exception {
        CSVReader reader = new CSVReader("titanic.csv", ",", true);
        while (reader.next()) {
            int id = reader.getInt(0);
            String name = reader.Get(3);
            double fare = reader.getDouble(9);
            System.out.printf(Locale.US, "%d %s %f", id, name, fare);
            System.out.println();
        }
    }

    @Test
    public void getDouble() throws IOException {
        CSVReader reader = new CSVReader("admin-units.csv", ",", true);
        int i = 0;
        while (reader.next() && i < 100) {
            i++;
            System.out.println("\n" + i + "\n");
            double population = reader.getDouble("population");
            double area = reader.getDouble("area");
            double density = reader.getDouble("density");
            System.out.printf(Locale.US, "%f %f %f", population, area, density);
            System.out.println();
        }
    }

}