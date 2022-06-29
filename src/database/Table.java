package database;

import utils.Convertor;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Table {
    private String path;

    public Table(String path) {
        this.path = path;
    }

    public void insert(HashMap <String, String> row) throws Exception {
        File f = new File(path);
        FileWriter fileWriter = new FileWriter(f, true);
        fileWriter.write(Convertor.mapToString(row) + "\n");
        fileWriter.close();
    }

    public ArrayList <HashMap <String, String> > get() {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            ArrayList <HashMap <String, String> > data = new ArrayList <HashMap <String, String> > ();
            while(scanner.hasNext()) {
                String str = scanner.nextLine();
                HashMap <String, String> row = Convertor.StringToMap(str);
                data.add(row);
            }
            return data;
        }catch (Exception e) {}
        return new ArrayList<>();
    }
}
