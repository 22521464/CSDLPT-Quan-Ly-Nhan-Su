package hcm;

import Model.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class InitDataHCM {
    public static void main(String[] args) throws Exception {
        BerkeleyDBManager dbManager = new BerkeleyDBManager();
        dbManager.open("data_hcm");
        DataService dataService = new DataService(dbManager, AccessLevel.ADMIN_HCM);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Tài khoản HCM
        dataService.addAccount(new Account("adminhcm", "123", AccessLevel.ADMIN_HCM, "HCM"));
        dataService.addAccount(new Account("staffhcm", "123", AccessLevel.STAFF_HCM, "HCM"));

        // Chi nhánh HCM
        dataService.addChiNhanh(new ChiNhanh("HCM", "Tru so chinh TP.HCM", "123 Nguyen Hue, Quan 1, TP.HCM"));

        // Phòng ban (dùng chung)
        dataService.addPhongBan(new PhongBan("KD", "Kinh Doanh"));
        dataService.addPhongBan(new PhongBan("KT", "Ky Thuat"));
        dataService.addPhongBan(new PhongBan("NS", "Nhan Su"));
        dataService.addPhongBan(new PhongBan("MKT", "Marketing"));

        // Nhân viên HCM
        dataService.addNhanVien(new NhanVien("NV001", "Nguyen Van An", sdf.parse("1990-05-15"), "901112222", "Truong phong", 35000000, "KD", "HCM"));
        dataService.addNhanVien(new NhanVien("NV002", "Tran Thi Binh", sdf.parse("1995-02-20"), "912223333", "Chuyen vien", 18000000, "KD", "HCM"));
        dataService.addNhanVien(new NhanVien("NV005", "Hoang Van Em", sdf.parse("1993-09-01"), "935556666", "Chuyen vien MKT", 17000000, "MKT", "HCM"));
        dataService.addNhanVien(new NhanVien("NV007", "Do Tuan Anh", sdf.parse("1992-04-12"), "907778888", "Ky su he thong", 25000000, "KT", "HCM"));
        dataService.addNhanVien(new NhanVien("NV009", "Mai Tien Dung", sdf.parse("1991-08-19"), "989123456", "Truong nhom MKT", 28000000, "MKT", "HCM"));
        dataService.addNhanVien(new NhanVien("NV010", "Nguyen Thao Nhi", sdf.parse("2000-12-01"), "905654321", "Content Creator", 14000000, "MKT", "HCM"));
        dataService.addNhanVien(new NhanVien("NV011", "Tran Quang Khai", sdf.parse("1997-06-22"), "913111222", "Lap trinh vien", 21000000, "KT", "HCM"));
        dataService.addNhanVien(new NhanVien("NV014", "Huynh Kim Ngan", sdf.parse("1995-07-07"), "977444555", "Nhan vien kinh doanh", 16000000, "KD", "HCM"));
        dataService.addNhanVien(new NhanVien("NV016", "Phan Thanh Tam", sdf.parse("2001-01-15"), "909666777", "Thuc tap sinh", 5000000, "KD", "HCM"));
        dataService.addNhanVien(new NhanVien("NV018", "Ngo Bao Chau", sdf.parse("1998-09-09"), "922888999", "Chuyen vien nhan su", 17000000, "NS", "HCM"));
        dataService.addNhanVien(new NhanVien("NV020", "Ta Minh Hieu", sdf.parse("1996-06-18"), "944000111", "Nhan vien ho tro", 15000000, "KD", "HCM"));

        // Chấm công HCM
        dataService.addChamCong(new ChamCong("CC01", "NV001", sdf.parse("2025-06-05"), "08:30", "17:35", "OK"));
        dataService.addChamCong(new ChamCong("CC02", "NV002", sdf.parse("2025-06-05"), "08:25", "17:30", "OK"));
        dataService.addChamCong(new ChamCong("CC05", "NV005", sdf.parse("2025-06-05"), "08:32", "17:28", "OK"));
        dataService.addChamCong(new ChamCong("CC07", "NV007", sdf.parse("2025-06-05"), "08:28", "18:05", "OT 30p"));
        dataService.addChamCong(new ChamCong("CC09", "NV001", sdf.parse("2025-06-06"), "08:29", "17:30", "OK"));
        dataService.addChamCong(new ChamCong("CC10", "NV002", sdf.parse("2025-06-06"), "08:35", "17:40", "OK"));
        dataService.addChamCong(new ChamCong("CC13", "NV005", sdf.parse("2025-06-06"), "08:30", "17:35", "OK"));
        dataService.addChamCong(new ChamCong("CC15", "NV007", sdf.parse("2025-06-06"), "08:33", "18:35", "OT 1h"));
        dataService.addChamCong(new ChamCong("CC17", "NV009", sdf.parse("2025-06-06"), "08:25", "17:45", "OK"));
        dataService.addChamCong(new ChamCong("CC18", "NV010", sdf.parse("2025-06-06"), "08:45", "17:20", "OK"));
        dataService.addChamCong(new ChamCong("CC19", "NV011", sdf.parse("2025-06-06"), "08:30", "18:00", "OK"));
        dataService.addChamCong(new ChamCong("CC22", "NV014", sdf.parse("2025-06-06"), "08:29", "17:00", "Ve som"));
        dataService.addChamCong(new ChamCong("CC24", "NV016", sdf.parse("2025-06-06"), "09:05", "17:30", "OK"));
        dataService.addChamCong(new ChamCong("CC26", "NV018", sdf.parse("2025-06-06"), "08:25", "17:30", "OK"));
        dataService.addChamCong(new ChamCong("CC28", "NV020", sdf.parse("2025-06-06"), "08:27", "17:30", "OK"));

        dbManager.close();
        System.out.println("Khởi tạo dữ liệu HCM thành công!");
    }
}