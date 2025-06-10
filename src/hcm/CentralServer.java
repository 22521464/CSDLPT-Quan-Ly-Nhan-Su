package hcm;

import Model.*;
import com.sleepycat.je.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CentralServer {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("CentralServer started, waiting for branch clients...");
            BerkeleyDBManager dbManager = new BerkeleyDBManager();
            dbManager.open("data_central"); // Thư mục riêng cho dữ liệu tổng hợp

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleBranch(clientSocket, dbManager)).start();
            }
        }
    }

    private static void handleBranch(Socket socket, BerkeleyDBManager dbManager) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String request;
            while ((request = in.readLine()) != null) {
                if (request.startsWith("SYNC")) {
                    String[] parts = request.split(" ");
                    if (parts.length != 2) {
                        out.println("Lệnh SYNC không hợp lệ.");
                        continue;
                    }
                    String table = parts[1];
                    System.out.println("Nhận dữ liệu đồng bộ: " + table);

                    Database db = dbManager.getDatabase(table);
                    if (db == null) {
                        out.println("Không tìm thấy database: " + table);
                        continue;
                    }

                    String line;
                    while ((line = in.readLine()) != null && !line.equals("END")) {
                        String[] fields = line.split("\\|");
                        if (table.equals("CHINHANH")) {
                            // maCN|tenCN|diaChi
                            if (fields.length == 3) {
                                ChiNhanh cn = new ChiNhanh(fields[0], fields[1], fields[2]);
                                DatabaseEntry key = new DatabaseEntry(cn.getMaCN().getBytes("UTF-8"));
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(bos);
                                oos.writeObject(cn);
                                oos.close();
                                DatabaseEntry data = new DatabaseEntry(bos.toByteArray());
                                db.put(null, key, data);
                            }
                        } else if (table.equals("PHONGBAN")) {
                            // maPB|tenPB
                            if (fields.length == 2) {
                                PhongBan pb = new PhongBan(fields[0], fields[1]);
                                DatabaseEntry key = new DatabaseEntry(pb.getMaPB().getBytes("UTF-8"));
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(bos);
                                oos.writeObject(pb);
                                oos.close();
                                DatabaseEntry data = new DatabaseEntry(bos.toByteArray());
                                db.put(null, key, data);
                            }
                        } else if (table.startsWith("NHANVIEN")) {
                            // maNV|hoTen|ngaySinh|sdt|chucVu|luong|maPB|maCN
                            if (fields.length == 8) {
                                NhanVien nv = new NhanVien(
                                    fields[0], fields[1], sdf.parse(fields[2]), fields[3], fields[4],
                                    Double.parseDouble(fields[5]), fields[6], fields[7]
                                );
                                DatabaseEntry key = new DatabaseEntry(nv.getMaNV().getBytes("UTF-8"));
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(bos);
                                oos.writeObject(nv);
                                oos.close();
                                DatabaseEntry data = new DatabaseEntry(bos.toByteArray());
                                db.put(null, key, data);
                            }
                        } else if (table.startsWith("CHAMCONG")) {
                            // maCC|maNV|ngayCC|gioVao|gioRa|ghiChu
                            if (fields.length == 6) {
                                ChamCong cc = new ChamCong(
                                    fields[0], fields[1], sdf.parse(fields[2]), fields[3], fields[4], fields[5]
                                );
                                DatabaseEntry key = new DatabaseEntry(cc.getMaCC().getBytes("UTF-8"));
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(bos);
                                oos.writeObject(cc);
                                oos.close();
                                DatabaseEntry data = new DatabaseEntry(bos.toByteArray());
                                db.put(null, key, data);
                            }
                        }
                    }
                    out.println("SYNC " + table + " OK");
                } else if (request.startsWith("GET")) {
                    String[] parts = request.split(" ");
                    if (parts.length != 2) {
                        out.println("Lệnh GET không hợp lệ.");
                        continue;
                    }
                    String table = parts[1];
                    Database db = dbManager.getDatabase(table);
                    if (db == null) {
                        out.println("Không tìm thấy database: " + table);
                        continue;
                    }
                    Cursor cursor = null;
                    try {
                        cursor = db.openCursor(null, null);
                        DatabaseEntry foundKey = new DatabaseEntry();
                        DatabaseEntry foundData = new DatabaseEntry();
                        int count = 0;
                        while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                            count++;
                            if (table.equals("CHINHANH")) {
                                ByteArrayInputStream bis = new ByteArrayInputStream(foundData.getData());
                                ObjectInputStream ois = new ObjectInputStream(bis);
                                ChiNhanh cn = (ChiNhanh) ois.readObject();
                                ois.close();
                                out.println(cn.getMaCN() + "|" + cn.getTenCN() + "|" + cn.getDiaChi());
                            } else if (table.equals("PHONGBAN")) {
                                ByteArrayInputStream bis = new ByteArrayInputStream(foundData.getData());
                                ObjectInputStream ois = new ObjectInputStream(bis);
                                PhongBan pb = (PhongBan) ois.readObject();
                                ois.close();
                                out.println(pb.getMaPB() + "|" + pb.getTenPB());
                            } else if (table.startsWith("NHANVIEN")) {
                                ByteArrayInputStream bis = new ByteArrayInputStream(foundData.getData());
                                ObjectInputStream ois = new ObjectInputStream(bis);
                                NhanVien nv = (NhanVien) ois.readObject();
                                ois.close();
                                out.println(nv.getMaNV() + "|" + nv.getHoTen() + "|" + nv.getNgaySinh() + "|" +
                                            nv.getSdt() + "|" + nv.getChucVu() + "|" + nv.getLuongCoBan() + "|" +
                                            nv.getMaPB() + "|" + nv.getMaCN());
                            } else if (table.startsWith("CHAMCONG")) {
                                ByteArrayInputStream bis = new ByteArrayInputStream(foundData.getData());
                                ObjectInputStream ois = new ObjectInputStream(bis);
                                ChamCong cc = (ChamCong) ois.readObject();
                                ois.close();
                                out.println(cc.getMaCC() + "|" + cc.getMaNV() + "|" + cc.getNgayChamCong() + "|" +
                                            cc.getGioVao() + "|" + cc.getGioRa() + "|" + cc.getGhiChu());
                            }
                        }
                        System.out.println("GET " + table + " trả về " + count + " bản ghi.");
                        out.println("END");
                    } finally {
                        if (cursor != null) cursor.close();
                    }
                } else {
                    out.println("Lệnh không hợp lệ.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}