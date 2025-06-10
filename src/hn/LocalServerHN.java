package hn;

import Model.*;
//import com.sleepycat.je.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class LocalServerHN {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("LocalServerHN started, waiting for local clients...");
            BerkeleyDBManager dbManager = new BerkeleyDBManager();
            dbManager.open("data_hn"); // Thư mục riêng cho dữ liệu HN

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket, dbManager)).start();
            }
        }
    }

    private static void handleClient(Socket socket, BerkeleyDBManager dbManager) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            DataService dataService = new DataService(dbManager, null);
            Account currentAccount = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Đăng nhập
            while (true) {
                String request = in.readLine();
                if (request == null) return;
                String[] parts = request.split(" ");
                if ("LOGIN".equalsIgnoreCase(parts[0])) {
                    String username = parts[1];
                    String password = parts[2];
                    currentAccount = dataService.login(username, password);
                    if (currentAccount != null) {
                        dataService.setUserRole(currentAccount.getRole());
                        out.println("OK");
                        break;
                    } else {
                        out.println("FAIL");
                        return;
                    }
                }
            }

            // Xử lý các lệnh tiếp theo
            String location = currentAccount.getLocation();
            String line;
            while ((line = in.readLine()) != null && !line.equalsIgnoreCase("EXIT")) {
                String[] parts = line.split(" ", 3);
                String action = parts[0];
                String table = parts.length > 1 ? parts[1] : "";
                String param = parts.length > 2 ? parts[2] : "";

                if ("GET".equalsIgnoreCase(action) && "NHANVIEN".equalsIgnoreCase(table)) {
                    List<NhanVien> list = dataService.getNhanVienList("HN");
                    if (list.isEmpty()) {
                        out.println("Không có nhân viên nào.");
                    } else {
                        for (NhanVien nv : list) {
                            out.println(nv.getMaNV() + " - " + nv.getHoTen() + " - " + nv.getChucVu() + " - " + nv.getMaPB() + " - " + nv.getMaCN());
                        }
                        out.println("END");
                    }
                } else if ("GET".equalsIgnoreCase(action) && "CHAMCONG".equalsIgnoreCase(table)) {
                    List<ChamCong> list = dataService.getChamCongList("HN");
                    if (list.isEmpty()) {
                        out.println("Không có dữ liệu chấm công.");
                    } else {
                        for (ChamCong cc : list) {
                            out.println(cc.getMaCC() + " - " + cc.getMaNV() + " - " +
                                    sdf.format(cc.getNgayChamCong()) + " - " +
                                    cc.getGioVao() + " - " + cc.getGioRa() + " - " + cc.getGhiChu());
                        }
                        out.println("END");
                    }
                } else if ("GET".equalsIgnoreCase(action) && "CHINHANH".equalsIgnoreCase(table)) {
                    List<ChiNhanh> list = dataService.getChiNhanhList();
                    if (list.isEmpty()) {
                        out.println("Không có chi nhánh nào.");
                    } else {
                        for (ChiNhanh cn : list) {
                            out.println(cn.getMaCN() + " - " + cn.getTenCN() + " - " + cn.getDiaChi());
                        }
                        out.println("END");
                    }
                } else if ("GET".equalsIgnoreCase(action) && "PHONGBAN".equalsIgnoreCase(table)) {
                    List<PhongBan> list = dataService.getPhongBanList();
                    if (list.isEmpty()) {
                        out.println("Không có phòng ban nào.");
                    } else {
                        for (PhongBan pb : list) {
                            out.println(pb.getMaPB() + " - " + pb.getTenPB());
                        }
                        out.println("END");
                    }
                } else if ("ADD".equalsIgnoreCase(action) && "NHANVIEN".equalsIgnoreCase(table)) {
                    // Chỉ cho phép adminHN
                    if (currentAccount.getRole() == AccessLevel.ADMIN_HN) {
                        // param: maNV|hoTen|ngaySinh|sdt|chucVu|luong|maPB|maCN
                        String[] fields = param.split("\\|");
                        if (fields.length == 8) {
                            NhanVien nv = new NhanVien(
                                fields[0], fields[1], sdf.parse(fields[2]), fields[3], fields[4],
                                Double.parseDouble(fields[5]), fields[6], fields[7]
                            );
                            dataService.addNhanVien(nv);
                            out.println("Thêm nhân viên thành công.");
                        } else {
                            out.println("Dữ liệu nhân viên không hợp lệ.");
                        }
                    } else {
                        out.println("Bạn không có quyền thêm nhân viên.");
                    }
                } else if ("ADD".equalsIgnoreCase(action) && "CHAMCONG".equalsIgnoreCase(table)) {
                    // Chỉ cho phép staffHN, adminHN
                    if (currentAccount.getRole() == AccessLevel.STAFF_HN ||
                        currentAccount.getRole() == AccessLevel.ADMIN_HN) {
                        // param: maCC|maNV|ngayCC|gioVao|gioRa|ghiChu
                        String[] fields = param.split("\\|");
                        if (fields.length == 6) {
                            ChamCong cc = new ChamCong(
                                fields[0], fields[1], sdf.parse(fields[2]), fields[3], fields[4], fields[5]
                            );
                            dataService.addChamCong(cc);
                            out.println("Thêm chấm công thành công.");
                        } else {
                            out.println("Dữ liệu chấm công không hợp lệ.");
                        }
                    } else {
                        out.println("Bạn không có quyền thêm chấm công.");
                    }
                } else {
                    out.println("Lệnh không hợp lệ hoặc chưa hỗ trợ.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}