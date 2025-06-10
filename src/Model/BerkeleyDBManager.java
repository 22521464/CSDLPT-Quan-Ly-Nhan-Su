package Model;

import com.sleepycat.je.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BerkeleyDBManager {
    private Environment env;
    private Map<String, Database> dbMap = new HashMap<>();

    // Mở environment và các database cần thiết
    public void open(String dbDir) {
        try {
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            env = new Environment(new File(dbDir), envConfig);

            // Mở các database cho HCM
            openDatabase("ACCOUNT");
            openDatabase("CHINHANH");
            openDatabase("PHONGBAN");
            openDatabase("NHANVIEN_HCM");
            openDatabase("CHAMCONG_HCM");

            // Nếu muốn HCM tổng hợp luôn dữ liệu HN thì mở thêm:
            openDatabase("NHANVIEN_HN");
            openDatabase("CHAMCONG_HN");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDatabase(String dbName) {
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        Database db = env.openDatabase(null, dbName, dbConfig);
        dbMap.put(dbName, db);
    }

    public Database getDatabase(String dbName) {
        return dbMap.get(dbName);
    }

    public void close() {
        try {
            for (Database db : dbMap.values()) {
                db.close();
            }
            if (env != null) env.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}