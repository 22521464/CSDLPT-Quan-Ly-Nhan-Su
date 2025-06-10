package Model;

import com.sleepycat.je.*;
import java.io.*;
import java.util.*;

public class DataService {
    private BerkeleyDBManager dbManager;
    private AccessLevel userRole;

    public DataService(BerkeleyDBManager dbManager, AccessLevel userRole) {
        this.dbManager = dbManager;
        this.userRole = userRole;
    }

    public void setUserRole(AccessLevel userRole) {
        this.userRole = userRole;
    }

    // Thêm tài khoản mới
    public boolean addAccount(Account acc) throws Exception {
        Database db = dbManager.getDatabase("ACCOUNT");
        DatabaseEntry key = new DatabaseEntry(acc.getUsername().getBytes("UTF-8"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(acc);
        oos.close();
        DatabaseEntry data = new DatabaseEntry(bos.toByteArray());
        db.put(null, key, data);
        return true;
    }

    // Đăng nhập
    public Account login(String username, String password) throws Exception {
        Database db = dbManager.getDatabase("ACCOUNT");
        DatabaseEntry key = new DatabaseEntry(username.getBytes("UTF-8"));
        DatabaseEntry data = new DatabaseEntry();
        if (db.get(null, key, data, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            ByteArrayInputStream bis = new ByteArrayInputStream(data.getData());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Account acc = (Account) ois.readObject();
            ois.close();
            if (acc.getPassword().equals(password)) {
                return acc;
            }
        }
        return null;
    }

    // Lấy danh sách nhân viên theo chi nhánh
    public List<NhanVien> getNhanVienList(String location) throws Exception {
        List<NhanVien> result = new ArrayList<>();
        Database db = dbManager.getDatabase("NHANVIEN_" + location);
        System.out.println("Đang truy xuất database: " + db);
        Cursor cursor = null;
        try {
            cursor = db.openCursor(null, null);
            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();
            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                ByteArrayInputStream bis = new ByteArrayInputStream(foundData.getData());
                ObjectInputStream ois = new ObjectInputStream(bis);
                NhanVien nv = (NhanVien) ois.readObject();
                ois.close();

                if (AccessControl.canView("NHANVIEN", userRole, nv.getMaCN())) {
                    result.add(nv);
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return result;
    }

    // Thêm nhân viên vào đúng database chi nhánh
    public boolean addNhanVien(NhanVien nv) throws Exception {
        if (!AccessControl.canAdd("NHANVIEN", userRole, nv.getMaCN())) return false;
        Database db = dbManager.getDatabase("NHANVIEN_" + nv.getMaCN());
        DatabaseEntry key = new DatabaseEntry(nv.getMaNV().getBytes("UTF-8"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(nv);
        oos.close();
        DatabaseEntry data = new DatabaseEntry(bos.toByteArray());
        db.put(null, key, data);
        return true;
    }

    // Lấy danh sách chi nhánh
    public List<ChiNhanh> getChiNhanhList() throws Exception {
        List<ChiNhanh> result = new ArrayList<>();
        Database db = dbManager.getDatabase("CHINHANH");
        Cursor cursor = null;
        try {
            cursor = db.openCursor(null, null);
            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();
            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                ByteArrayInputStream bis = new ByteArrayInputStream(foundData.getData());
                ObjectInputStream ois = new ObjectInputStream(bis);
                ChiNhanh cn = (ChiNhanh) ois.readObject();
                ois.close();

                if (AccessControl.canView("CHINHANH", userRole, null)) {
                    result.add(cn);
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return result;
    }

    // Thêm chi nhánh
    public boolean addChiNhanh(ChiNhanh cn) throws Exception {
        if (!AccessControl.canAdd("CHINHANH", userRole, null)) return false;
        Database db = dbManager.getDatabase("CHINHANH");
        DatabaseEntry key = new DatabaseEntry(cn.getMaCN().getBytes("UTF-8"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(cn);
        oos.close();
        DatabaseEntry data = new DatabaseEntry(bos.toByteArray());
        db.put(null, key, data);
        return true;
    }

    // Lấy danh sách phòng ban
    public List<PhongBan> getPhongBanList() throws Exception {
        List<PhongBan> result = new ArrayList<>();
        Database db = dbManager.getDatabase("PHONGBAN");
        Cursor cursor = null;
        try {
            cursor = db.openCursor(null, null);
            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();
            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                ByteArrayInputStream bis = new ByteArrayInputStream(foundData.getData());
                ObjectInputStream ois = new ObjectInputStream(bis);
                PhongBan pb = (PhongBan) ois.readObject();
                ois.close();

                if (AccessControl.canView("PHONGBAN", userRole, null)) {
                    result.add(pb);
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return result;
    }

    // Thêm phòng ban
    public boolean addPhongBan(PhongBan pb) throws Exception {
        if (!AccessControl.canAdd("PHONGBAN", userRole, null)) return false;
        Database db = dbManager.getDatabase("PHONGBAN");
        DatabaseEntry key = new DatabaseEntry(pb.getMaPB().getBytes("UTF-8"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(pb);
        oos.close();
        DatabaseEntry data = new DatabaseEntry(bos.toByteArray());
        db.put(null, key, data);
        return true;
    }

    // Lấy danh sách chấm công theo chi nhánh
    public List<ChamCong> getChamCongList(String location) throws Exception {
        List<ChamCong> result = new ArrayList<>();
        Database db = dbManager.getDatabase("CHAMCONG_" + location);
        Cursor cursor = null;
        try {
            cursor = db.openCursor(null, null);
            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();
            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                ByteArrayInputStream bis = new ByteArrayInputStream(foundData.getData());
                ObjectInputStream ois = new ObjectInputStream(bis);
                ChamCong cc = (ChamCong) ois.readObject();
                ois.close();

                // Lấy MaCN từ MaNV (cần truyền vào mapNhanVien hoặc có hàm tra cứu)
                String maCN = getMaCNByMaNV(cc.getMaNV());
                if (AccessControl.canView("CHAMCONG", userRole, maCN)) {
                    result.add(cc);
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return result;
    }

    // Thêm chấm công vào đúng database chi nhánh
    public boolean addChamCong(ChamCong cc) throws Exception {
        String maCN = getMaCNByMaNV(cc.getMaNV());
        if (!AccessControl.canAdd("CHAMCONG", userRole, maCN)) return false;
        Database db = dbManager.getDatabase("CHAMCONG_" + maCN);
        DatabaseEntry key = new DatabaseEntry(cc.getMaCC().getBytes("UTF-8"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(cc);
        oos.close();
        DatabaseEntry data = new DatabaseEntry(bos.toByteArray());
        db.put(null, key, data);
        return true;
    }

    // Hàm tra cứu MaCN từ MaNV (cần có sẵn database nhân viên)
    private String getMaCNByMaNV(String maNV) throws Exception {
        // Tìm trong cả hai database NHANVIEN_HCM và NHANVIEN_HN
        for (String location : new String[]{"HCM", "HN"}) {
            Database db = dbManager.getDatabase("NHANVIEN_" + location);
            DatabaseEntry key = new DatabaseEntry(maNV.getBytes("UTF-8"));
            DatabaseEntry data = new DatabaseEntry();
            if (db.get(null, key, data, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                ByteArrayInputStream bis = new ByteArrayInputStream(data.getData());
                ObjectInputStream ois = new ObjectInputStream(bis);
                NhanVien nv = (NhanVien) ois.readObject();
                ois.close();
                return nv.getMaCN();
            }
        }
        return null;
    }
}