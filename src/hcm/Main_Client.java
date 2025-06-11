/* Chay chung voi file CentralServer & LocalClientHCM & LocalServerHCM*/

package hcm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main_Client {
    public static void main(String[] args) throws Exception {
        try (
            Scanner sc = new Scanner(System.in);
            Socket socket = new Socket("192.168.1.3", 8888); // Kết nối tới LocalServerHCM
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Kết nối tới LocalServerHCM thành công!");
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

            boolean isAdminHCM = "adminhcm".equalsIgnoreCase(username);
            boolean isStaffHCM = "staffhcm".equalsIgnoreCase(username);

            while (true) {
                System.out.println("\n==== MENU ====");
                if (isStaffHCM) {
                    System.out.println("1. Xem chi nhánh");
                    System.out.println("2. Xem phòng ban");
                    //System.out.println("3. Xem nhân viên");
                    //System.out.println("4. Xem chấm công");
                    //System.out.println("5. Thêm chấm công (LocalServerHCM)");
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
                        case 0:
                            out.println("EXIT");
                            System.out.println("Đã thoát chương trình.");
                            return;
                        default:
                            System.out.println("Chức năng không hợp lệ.");
                    }
                } else if (isAdminHCM) {
                    System.out.println("1. Xem chi nhánh");
                    System.out.println("2. Xem phòng ban");
                    System.out.println("3. Xem nhân viên");
                    System.out.println("4. Xem chấm công");
                    System.out.println("5. Báo cáo tổng số giờ làm việc toàn công ty theo tháng");
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
                            // Báo cáo tổng giờ làm hàng tháng (CentralServer)
                            try (
                                Socket centralSocket = new Socket("192.168.1.3", 9999);
                                PrintWriter centralOut = new PrintWriter(centralSocket.getOutputStream(), true);
                                BufferedReader centralIn = new BufferedReader(new InputStreamReader(centralSocket.getInputStream()))
                            ) {
                                System.out.print("Nhập tháng: ");
                                int thang = Integer.parseInt(sc.nextLine());
                                System.out.print("Nhập năm: ");
                                int nam = Integer.parseInt(sc.nextLine());


                                // Gửi lệnh BAOCAO cho CentralServer
                                centralOut.println("BAOCAO " + thang + " " + nam);


                                // Đọc kết quả từ CentralServer
                                String response;
                                while ((response = centralIn.readLine()) != null) {
                                    System.out.println(response);
                                    // Thông thường báo cáo chỉ trả về 1 dòng, nhưng phòng trường hợp sau này có nhiều dòng thì dùng vòng lặp
                                    if (response.contains("giờ")) break;
                                }
                            } catch (Exception ex) {
                                System.out.println("Không kết nối được CentralServer: " + ex.getMessage());
                            }
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