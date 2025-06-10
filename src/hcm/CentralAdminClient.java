package hcm;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CentralAdminClient {
    public static void main(String[] args) throws Exception {
        try (
            Scanner sc = new Scanner(System.in);
            Socket socket = new Socket("192.168.1.3", 9999); // Đổi IP nếu CentralServer chạy trên máy khác
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Kết nối tới CentralServer thành công!");

            while (true) {
                System.out.println("\n==== MENU CENTRAL ====");
                System.out.println("1. Xem danh sách nhân viên HCM");
                System.out.println("2. Xem danh sách nhân viên HN");
                System.out.println("3. Xem danh sách chấm công HCM");
                System.out.println("4. Xem danh sách chấm công HN");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng: ");
                int choice = Integer.parseInt(sc.nextLine());

                String line;
                switch (choice) {
                    case 1:
                        out.println("GET NHANVIEN_HCM");
                        System.out.println("Danh sách nhân viên HCM:");
                        while ((line = in.readLine()) != null && !line.equals("END")) {
                            System.out.println(line);
                        }
                        break;
                    case 2:
                        out.println("GET NHANVIEN_HN");
                        System.out.println("Danh sách nhân viên HN:");
                        while ((line = in.readLine()) != null && !line.equals("END")) {
                            System.out.println(line);
                        }
                        break;
                    case 3:
                        out.println("GET CHAMCONG_HCM");
                        System.out.println("Danh sách chấm công HCM:");
                        while ((line = in.readLine()) != null && !line.equals("END")) {
                            System.out.println(line);
                        }
                        break;
                    case 4:
                        out.println("GET CHAMCONG_HN");
                        System.out.println("Danh sách chấm công HN:");
                        while ((line = in.readLine()) != null && !line.equals("END")) {
                            System.out.println(line);
                        }
                        break;
                    case 0:
                        System.out.println("Đã thoát chương trình.");
                        return;
                    default:
                        System.out.println("Chức năng không hợp lệ.");
                }
            }
        }
    }
}