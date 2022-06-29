package database;
import java.util.HashMap;

public class Database {
    private static Database instance = null;
    private HashMap<String, Table> tables;
    private  Database() {
        tables = new HashMap<String, Table>();
        tables.put("messages", new Table("src/data/messages.txt"));
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }
}
