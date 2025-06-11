package hn;

import Model.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class InitDataHN {
    public static void main(String[] args) throws Exception {
        BerkeleyDBManager dbManager = new BerkeleyDBManager();
        dbManager.open("data_hn");
        DataService dataService = new DataService(dbManager, AccessLevel.ADMIN_HCM);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Tài khoản HN
        dataService.addAccount(new Account("adminhn", "123", AccessLevel.ADMIN_HN, "HN"));
        dataService.addAccount(new Account("staffhn", "123", AccessLevel.STAFF_HN, "HN"));

        // Chi nhánh HN
        dataService.addChiNhanh(new ChiNhanh("HN", "Chi nhanh Ha Noi", "456 Tran Duy Hung, Cau Giay, Ha Noi"));

        // Phòng ban (dùng chung)
        dataService.addPhongBan(new PhongBan("KD", "Kinh Doanh"));
        dataService.addPhongBan(new PhongBan("KT", "Ky Thuat"));
        dataService.addPhongBan(new PhongBan("NS", "Nhan Su"));
        dataService.addPhongBan(new PhongBan("MKT", "Marketing"));

        // Nhân viên HN
        dataService.addNhanVien(new NhanVien("NV003", "Le Minh Cuong", sdf.parse("1988-11-30"), "983334444", "Giam doc chi nhanh", 40000000, "NS", "HN"));
        dataService.addNhanVien(new NhanVien("NV004", "Pham Thi Dung", sdf.parse("1998-07-10"), "974445555", "Lap trinh vien", 22000000, "KT", "HN"));
        dataService.addNhanVien(new NhanVien("NV006", "Vu Thi Ha", sdf.parse("1996-01-25"), "946667777", "Nhan vien tuyen dung", 16000000, "NS", "HN"));
        dataService.addNhanVien(new NhanVien("NV008", "Bui Thu Thuy", sdf.parse("1999-03-08"), "918889999", "Nhan vien kinh doanh", 15000000, "KD", "HN"));
        dataService.addNhanVien(new NhanVien("NV012", "Le Thi Lan", sdf.parse("1994-10-18"), "933222333", "Ke toan", 19000000, "NS", "HN"));
        dataService.addNhanVien(new NhanVien("NV013", "Vo Minh Luan", sdf.parse("1999-02-14"), "944333444", "Tester", 18000000, "KT", "HN"));
        dataService.addNhanVien(new NhanVien("NV015", "Dang Huu Toan", sdf.parse("1989-03-29"), "988555666", "Truong nhom KT", 32000000, "KT", "HN"));
        dataService.addNhanVien(new NhanVien("NV017", "Trinh Hoai Nam", sdf.parse("1993-11-11"), "911777888", "Ky su DevOps", 28000000, "KT", "HN"));
        dataService.addNhanVien(new NhanVien("NV019", "Lam Anh Thu", sdf.parse("1997-04-30"), "933999000", "Digital Marketing", 20000000, "MKT", "HN"));

        // Chấm công HN
        dataService.addChamCong(new ChamCong("CC03", "NV003", sdf.parse("2025-06-05"), "08:15", "17:45", "OK"));
        dataService.addChamCong(new ChamCong("CC04", "NV004", sdf.parse("2025-06-05"), "09:00", "18:00", "OK"));
        dataService.addChamCong(new ChamCong("CC06", "NV006", sdf.parse("2025-06-05"), "08:40", "17:30", "Xin ve som 30p"));
        dataService.addChamCong(new ChamCong("CC08", "NV008", sdf.parse("2025-06-05"), "08:55", "17:33", "OK"));
        dataService.addChamCong(new ChamCong("CC11", "NV003", sdf.parse("2025-06-06"), "08:20", "17:30", "OK"));
        dataService.addChamCong(new ChamCong("CC12", "NV004", sdf.parse("2025-06-06"), "09:10", "18:00", "Di tre 10p"));
        dataService.addChamCong(new ChamCong("CC14", "NV006", sdf.parse("2025-06-06"), "08:28", "17:30", "OK"));
        dataService.addChamCong(new ChamCong("CC16", "NV008", sdf.parse("2025-06-06"), "08:50", "17:30", "OK"));
        dataService.addChamCong(new ChamCong("CC20", "NV012", sdf.parse("2025-06-06"), "08:22", "17:30", "OK"));
        dataService.addChamCong(new ChamCong("CC21", "NV013", sdf.parse("2025-06-06"), "08:30", "17:31", "OK"));
        dataService.addChamCong(new ChamCong("CC23", "NV015", sdf.parse("2025-06-06"), "08:19", "17:30", "OK"));
        dataService.addChamCong(new ChamCong("CC25", "NV017", sdf.parse("2025-06-06"), "08:30", "18:15", "OT 45p"));
        dataService.addChamCong(new ChamCong("CC27", "NV019", sdf.parse("2025-06-06"), "08:30", "17:40", "OK"));

        dbManager.close();
        System.out.println("Khởi tạo dữ liệu HN thành công!");
    }
}