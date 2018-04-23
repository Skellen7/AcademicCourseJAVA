package Csv;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.LongToIntFunction;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;

    // nazwy kolumn w takiej kolejności, jak w pliku
    List<String> columnLabels = new ArrayList<>();
    // odwzorowanie: nazwa kolumny -> numer kolumny
    Map<String,Integer> columnLabelsToInt = new HashMap<>();

    String[] current;

    /**
     *
     * @param filename - nazwa pliku
     * @param delimiter - separator pól
     * @param hasHeader - czy plik ma wiersz nagłówkowy
     */

    public CSVReader(String filename,String delimiter,boolean hasHeader) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if(hasHeader)parseHeader();
    }

    public CSVReader(String filename,String delimiter) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        this.delimiter = delimiter;
        this.hasHeader = false;
    }

    public CSVReader(String filename) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filename));
        this.delimiter = ";";
        this.hasHeader = false;
    }

    public CSVReader(Reader reader, String delimiter, boolean hasHeader){
        this.reader = new BufferedReader(reader);
        this.delimiter=delimiter;
        this.hasHeader=hasHeader;
    }

    void parseHeader() throws IOException {
        // wczytaj wiersz
        String line = reader.readLine();
        if (line == null) {
            return;
        }
        // podziel na pola
        String[] header = line.split(delimiter);
        // przetwarzaj dane w wierszu
        for (int i = 0; i < header.length; i++) {
            // dodaj nazwy kolumn do columnLabels i numery do columnLabelsToInt
            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i],i);
        }
    }

    boolean next() throws IOException {
        // wczytaj wiersz
        String line  = reader.readLine();
        if(line==null){
            return false;
        }
        // podziel na pola (uwzlegniajacy znak '"')
        current = line.split(delimiter+"(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        //current = line.split(delimiter);
        return true;
    }

    String Get(int columnIndex){
        if(isMissing(columnIndex)) return "n/a";
        else return current[columnIndex];
    }

    String Get(String columnLabel){
        if(isMissing(columnLabel)) return "n/a";
        return current[this.columnLabelsToInt.get(columnLabel)];
    }

    List<String> getColumnLabels(){
        return columnLabels;
    }

    int getRecordLength(){
        return current.length;
    }


    boolean isMissing(int columnindex){
        if(current.length<=columnindex) return true;
        if(this.current[columnindex].isEmpty()) return true;
        else return false;
    }

    boolean isMissing(String columnLabel){
        return isMissing(this.columnLabelsToInt.get(columnLabel));
    }

    int getInt(int columnIndex){
        if(isMissing(columnIndex)) return -1;
        return Integer.parseInt(current[columnIndex]);
    }

    int getInt(String columnLabel){
        if(isMissing(columnLabel)) return -1;
        else return Integer.parseInt(this.current[this.columnLabelsToInt.get(columnLabel)]);
    }

    long getLong(int columnIndex){
        if(isMissing(columnIndex)) return -1;
        else return Long.parseLong(current[columnIndex]);
    }

    long getLong(String columnLabel){
        if(isMissing(columnLabel)) return -1;
        else return Long.parseLong(this.current[this.columnLabelsToInt.get(columnLabel)]);
    }

    double getDouble(int columnIndex){
        if(isMissing(columnIndex)) return -1.0;
        else return Double.parseDouble(current[columnIndex]);
    }

    double getDouble(String columnLabel){
        if(isMissing(columnLabel)) return -1.0;
        else return Double.parseDouble(this.current[this.columnLabelsToInt.get(columnLabel)]);
    }

    void getDate(int columnIndex, String format){
        LocalDate date = LocalDate.parse(current[columnIndex], DateTimeFormatter.ofPattern(format));
        System.out.println(date);
    }

    void getTime(int columnIndex, String format){
        LocalTime time = LocalTime.parse(current[columnIndex],DateTimeFormatter.ofPattern(format));
        System.out.println(time);
    }
}
