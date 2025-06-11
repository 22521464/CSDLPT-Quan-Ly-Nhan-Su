/* Chay chung voi file CentralServer & LocalClientHN */

package hn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main_Client {
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
                if (isStaffHN || isAdminHN) {
                    System.out.println("1. Xem chi nhánh");
                    System.out.println("2. Xem phòng ban");
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
                } else {
                    System.out.println("Bạn không có quyền sử dụng chương trình này.");
                    return;
                }
            }
        } catch (IOException e) {
           System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }
}