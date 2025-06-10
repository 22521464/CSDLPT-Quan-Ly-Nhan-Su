package hn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main_final {
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

            boolean isAdminHN = "adminhn".equalsIgnoreCase(username);
            boolean isStaffHN = "staffhn".equalsIgnoreCase(username);

            while (true) {
                System.out.println("\n==== MENU ====");
                if (isStaffHN) {
                    System.out.println("1. Xem chi nhánh (CentralServer)");
                    System.out.println("2. Xem phòng ban (CentralServer)");
                    System.out.println("3. Xem nhân viên (LocalServerHCM)");
                    System.out.println("4. Xem chấm công (LocalServerHCM)");
                    System.out.println("5. Thêm chấm công (LocalServerHCM)");
                    System.out.println("0. Thoát");
                    System.out.print("Chọn chức năng: ");
                    int choice = Integer.parseInt(sc.nextLine());
                    String line;
                    switch (choice) {
                        case 1:
                            // Xem chi nhánh (CentralServer)
                            try (
                                Socket centralSocket = new Socket("192.168.1.3", 9999);
                                PrintWriter centralOut = new PrintWriter(centralSocket.getOutputStream(), true);
                                BufferedReader centralIn = new BufferedReader(new InputStreamReader(centralSocket.getInputStream()))
                            ) {
                                System.out.println("=== Danh sách chi nhánh ===");
                                centralOut.println("GET CHINHANH");
                                while ((line = centralIn.readLine()) != null && !line.equals("END")) {
                                    System.out.println(line);
                                }
                            } catch (Exception ex) {
                                System.out.println("Không kết nối được CentralServer: " + ex.getMessage());
                            }
                            break;
                        case 2:
                            // Xem phòng ban (CentralServer)
                            try (
                                Socket centralSocket = new Socket("192.168.1.3", 9999);
                                PrintWriter centralOut = new PrintWriter(centralSocket.getOutputStream(), true);
                                BufferedReader centralIn = new BufferedReader(new InputStreamReader(centralSocket.getInputStream()))
                            ) {
                                System.out.println("=== Danh sách phòng ban ===");
                                centralOut.println("GET PHONGBAN");
                                while ((line = centralIn.readLine()) != null && !line.equals("END")) {
                                    System.out.println(line);
                                }
                            } catch (Exception ex) {
                                System.out.println("Không kết nối được CentralServer: " + ex.getMessage());
                            }
                            break;
                        case 3:
                            // Xem nhân viên (LocalServerHN)
                            out.println("GET NHANVIEN HN");
                            System.out.println("Danh sách nhân viên HN:");
                            while ((line = in.readLine()) != null && !line.equals("END")) {
                                System.out.println(line);
                            }
                            break;
                        case 4:
                            // Xem chấm công (LocalServerHN)
                            out.println("GET CHAMCONG HN");
                            System.out.println("Danh sách chấm công HN:");
                            while ((line = in.readLine()) != null && !line.equals("END")) {
                                System.out.println(line);
                            }
                            break;
                        case 5:
                            // Thêm chấm công (LocalServerHN)
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
                } else if (isAdminHN) {
                    System.out.println("1. Xem chi nhánh (CentralServer)");
                    System.out.println("2. Xem phòng ban (CentralServer)");
                    System.out.println("3. Xem nhân viên (CentralServer)");
                    System.out.println("4. Xem chấm công (CentralServer)");
                    System.out.println("5. Thêm chi nhánh");
                    System.out.println("6. Thêm phòng ban");
                    System.out.println("7. Thêm nhân viên");
                    System.out.println("8. Thêm chấm công");
                    System.out.println("0. Thoát");
                    System.out.print("Chọn chức năng: ");
                    int choice = Integer.parseInt(sc.nextLine());
                    String line;
                    switch (choice) {
                        case 1:
                            // Xem chi nhánh (CentralServer)
                            try (
                                Socket centralSocket = new Socket("192.168.1.3", 9999);
                                PrintWriter centralOut = new PrintWriter(centralSocket.getOutputStream(), true);
                                BufferedReader centralIn = new BufferedReader(new InputStreamReader(centralSocket.getInputStream()))
                            ) {
                                System.out.println("=== Danh sách chi nhánh ===");
                                centralOut.println("GET CHINHANH");
                                while ((line = centralIn.readLine()) != null && !line.equals("END")) {
                                    System.out.println(line);
                                }
                            } catch (Exception ex) {
                                System.out.println("Không kết nối được CentralServer: " + ex.getMessage());
                            }
                            break;
                        case 2:
                            // Xem phòng ban (CentralServer)
                            try (
                                Socket centralSocket = new Socket("192.168.1.3", 9999);
                                PrintWriter centralOut = new PrintWriter(centralSocket.getOutputStream(), true);
                                BufferedReader centralIn = new BufferedReader(new InputStreamReader(centralSocket.getInputStream()))
                            ) {
                                System.out.println("=== Danh sách phòng ban ===");
                                centralOut.println("GET PHONGBAN");
                                while ((line = centralIn.readLine()) != null && !line.equals("END")) {
                                    System.out.println(line);
                                }
                            } catch (Exception ex) {
                                System.out.println("Không kết nối được CentralServer: " + ex.getMessage());
                            }
                            break;
                        case 3:
                            // Xem nhân viên (CentralServer)
                            try (
                                Socket centralSocket = new Socket("192.168.1.3", 9999);
                                PrintWriter centralOut = new PrintWriter(centralSocket.getOutputStream(), true);
                                BufferedReader centralIn = new BufferedReader(new InputStreamReader(centralSocket.getInputStream()))
                            ) {
                                System.out.println("=== Danh sách nhân viên HCM ===");
                                centralOut.println("GET NHANVIEN_HCM");
                                while ((line = centralIn.readLine()) != null && !line.equals("END")) {
                                    System.out.println(line);
                                }
                                System.out.println("=== Danh sách nhân viên HN ===");
                                centralOut.println("GET NHANVIEN_HN");
                                while ((line = centralIn.readLine()) != null && !line.equals("END")) {
                                    System.out.println(line);
                                }
                            } catch (Exception ex) {
                                System.out.println("Không kết nối được CentralServer: " + ex.getMessage());
                            }
                            break;
                        case 4:
                            // Xem chấm công (CentralServer)
                            try (
                                Socket centralSocket = new Socket("192.168.1.3", 9999);
                                PrintWriter centralOut = new PrintWriter(centralSocket.getOutputStream(), true);
                                BufferedReader centralIn = new BufferedReader(new InputStreamReader(centralSocket.getInputStream()))
                            ) {
                                System.out.println("=== Danh sách chấm công HCM ===");
                                centralOut.println("GET CHAMCONG_HCM");
                                while ((line = centralIn.readLine()) != null && !line.equals("END")) {
                                    System.out.println(line);
                                }
                                System.out.println("=== Danh sách chấm công HN ===");
                                centralOut.println("GET CHAMCONG_HN");
                                while ((line = centralIn.readLine()) != null && !line.equals("END")) {
                                    System.out.println(line);
                                }
                            } catch (Exception ex) {
                                System.out.println("Không kết nối được CentralServer: " + ex.getMessage());
                            }
                            break;
                        case 5:
                            // Thêm chi nhánh
                            System.out.print("Mã CN: ");
                            String maCN = sc.nextLine();
                            System.out.print("Tên CN: ");
                            String tenCN = sc.nextLine();
                            System.out.print("Địa chỉ: ");
                            String diaChi = sc.nextLine();
                            out.println("ADD CHINHANH " + maCN + "|" + tenCN + "|" + diaChi);
                            System.out.println(in.readLine());
                            break;
                        case 6:
                            // Thêm phòng ban
                            System.out.print("Mã PB: ");
                            String maPB = sc.nextLine();
                            System.out.print("Tên PB: ");
                            String tenPB = sc.nextLine();
                            out.println("ADD PHONGBAN " + maPB + "|" + tenPB);
                            System.out.println(in.readLine());
                            break;
                        case 7:
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
                            String maPB_NV = sc.nextLine();
                            out.println("ADD NHANVIEN " + maNV + "|" + hoTen + "|" + ngaySinh + "|" + sdt + "|" + chucVu + "|" + luong + "|" + maPB_NV + "|HN");
                            System.out.println(in.readLine());
                            break;
                        case 8:
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
                } else {
                    System.out.println("Bạn không có quyền sử dụng chương trình này.");
                    return;
                }
            }
        }
    }
}