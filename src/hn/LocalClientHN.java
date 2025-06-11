/* Chay chung voi file CentralServer & Main_Client */
package hn;

import Model.*;
import com.sleepycat.je.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class LocalClientHN {
    public static void main(String[] args) throws Exception {
        // Kết nối tới CentralServer (ví dụ CentralServer chạy trên máy HCM, port 9999)
        Socket socket = new Socket("192.168.1.3", 9999); 
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Mở database HN để lấy dữ liệu cần đồng bộ
        BerkeleyDBManager dbManager = new BerkeleyDBManager();
        dbManager.open("data_hn");
        DataService dataService = new DataService(dbManager, AccessLevel.ADMIN_HN);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Đồng bộ danh sách nhân viên HN lên CentralServer
        out.println("SYNC NHANVIEN_HN");
        List<NhanVien> nhanVienList = dataService.getNhanVienList("HN");
        System.out.println("Số lượng nhân viên HN gửi đi: " + nhanVienList.size());
        for (NhanVien nv : nhanVienList) {
            String data = nv.getMaNV() + "|" + nv.getHoTen() + "|" + sdf.format(nv.getNgaySinh()) + "|" +
                        nv.getSdt() + "|" + nv.getChucVu() + "|" + nv.getLuongCoBan() + "|" +
                        nv.getMaPB() + "|" + nv.getMaCN();
            System.out.println("Gửi: " + data); // debug từng dòng gửi đi
            out.println(data);
        }
        out.println("END");
        System.out.println(in.readLine()); // Đọc phản hồi từ CentralServer

        // Đồng bộ danh sách chấm công HN lên CentralServer
        out.println("SYNC CHAMCONG_HN");
        List<ChamCong> chamCongList = dataService.getChamCongList("HN");
        System.out.println("Số lượng chấm công HN gửi đi: " + chamCongList.size());
        for (ChamCong cc : chamCongList) {
            String data = cc.getMaCC() + "|" + cc.getMaNV() + "|" + sdf.format(cc.getNgayChamCong()) + "|" +
                        cc.getGioVao() + "|" + cc.getGioRa() + "|" + cc.getGhiChu();
            System.out.println("Gửi: " + data); // debug từng dòng gửi đi
            out.println(data);
        }
        out.println("END");
        System.out.println(in.readLine()); // Đọc phản hồi từ CentralServer

        // Đồng bộ chi nhánh
        out.println("SYNC CHINHANH");
        List<ChiNhanh> cnList = dataService.getChiNhanhList();
        for (ChiNhanh cn : cnList) {
            out.println(cn.getMaCN() + "|" + cn.getTenCN() + "|" + cn.getDiaChi());
        }
        out.println("END");
        System.out.println(in.readLine());

        // Đồng bộ phòng ban
        out.println("SYNC PHONGBAN");
        List<PhongBan> pbList = dataService.getPhongBanList();
        for (PhongBan pb : pbList) {
            out.println(pb.getMaPB() + "|" + pb.getTenPB());
        }
        out.println("END");
        System.out.println(in.readLine());

        // Đóng kết nối
        socket.close();
        dbManager.close();
    }
}