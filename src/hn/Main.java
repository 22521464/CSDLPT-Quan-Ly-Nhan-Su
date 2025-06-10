package hn;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        try (
            Scanner sc = new Scanner(System.in);
            Socket socket = new Socket("192.168.1.12", 8888); // Kết nối tới LocalServerHN
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Kết nối tới LocalServerHN thành công!");
            // Đăng nhập
            System.out.print("Tên đăng nhập: ");
            String username = sc.nextLine();
            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();

            // Gửi thông tin đăng nhập
            out.println("LOGIN " + username + " " + password);
            String loginResponse = in.readLine();
            if (!"OK".equals(loginResponse)) {
                System.out.println("Đăng nhập thất bại!");
                return;
            }
            System.out.println("Đăng nhập thành công!");

            while (true) {
                System.out.println("\n==== MENU ====");
                System.out.println("1. Xem danh sách chi nhánh");
                System.out.println("2. Xem danh sách phòng ban");
                System.out.println("3. Xem danh sách nhân viên");
                System.out.println("4. Xem danh sách chấm công");
                System.out.println("5. Thêm nhân viên (chỉ adminHN)");
                System.out.println("6. Thêm chấm công (staffHN, adminHN)");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng: ");
                int choice = Integer.parseInt(sc.nextLine());

                String line;
                switch (choice) {
                    case 1:
                        out.println("GET CHINHANH");
                        System.out.println("Danh sách chi nhánh:");
                        while ((line = in.readLine()) != null && !line.equals("END")) {
                            System.out.println(line);
                        }
                        break;
                    case 2:
                        out.println("GET PHONGBAN");
                        System.out.println("Danh sách phòng ban:");
                        while ((line = in.readLine()) != null && !line.equals("END")) {
                            System.out.println(line);
                        }
                        break;
                    case 3:
                        out.println("GET NHANVIEN HN");
                        System.out.println("Danh sách nhân viên HN:");
                        while ((line = in.readLine()) != null && !line.equals("END")) {
                            System.out.println(line);
                        }
                        break;
                    case 4:
                        out.println("GET CHAMCONG HN");
                        System.out.println("Danh sách chấm công HN:");
                        while ((line = in.readLine()) != null && !line.equals("END")) {
                            System.out.println(line);
                        }
                        break;
                    case 5:
                        // Thêm nhân viên
                        System.out.print("Mã NV: ");
                        String maNV = sc.nextLine();
                        System.out.print("Họ tên: ");
                        String hoTen = sc.nextLine();
                        System.out.print("Ngày sinh (yyyy-MM-dd): ");
                        String ngaySinh = sc.nextLine();
                        System.out.print("SĐT: ");
                        String sdt = sc.nextLine();
                        System.out.print("Chức vụ: ");
                        String chucVu = sc.nextLine();
                        System.out.print("Lương cơ bản: ");
                        String luong = sc.nextLine();
                        System.out.print("Mã PB: ");
                        String maPB = sc.nextLine();
                        out.println("ADD NHANVIEN " + maNV + "|" + hoTen + "|" + ngaySinh + "|" + sdt + "|" + chucVu + "|" + luong + "|" + maPB + "|HN");
                        System.out.println(in.readLine());
                        break;
                    case 6:
                        // Thêm chấm công
                        System.out.print("Mã CC: ");
                        String maCC = sc.nextLine();
                        System.out.print("Mã NV: ");
                        String maNVCC = sc.nextLine();
                        System.out.print("Ngày chấm công (yyyy-MM-dd): ");
                        String ngayCC = sc.nextLine();
                        System.out.print("Giờ vào: ");
                        String gioVao = sc.nextLine();
                        System.out.print("Giờ ra: ");
                        String gioRa = sc.nextLine();
                        System.out.print("Ghi chú: ");
                        String ghiChu = sc.nextLine();
                        out.println("ADD CHAMCONG " + maCC + "|" + maNVCC + "|" + ngayCC + "|" + gioVao + "|" + gioRa + "|" + ghiChu);
                        System.out.println(in.readLine());
                        break;
                    case 0:
                        out.println("EXIT");
                        System.out.println("Đã thoát chương trình.");
                        return;
                    default:
                        System.out.println("Chức năng không hợp lệ.");
                }
            }
        }
    }
}