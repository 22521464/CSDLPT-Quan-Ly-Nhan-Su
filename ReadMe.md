# HƯỚNG DẪN CHẠY HỆ THỐNG PHÂN TÁN QUẢN LÝ NHÂN SỰ

## **Bước 0: Khởi tạo dữ liệu ban đầu (bắt buộc)**
Trước khi chạy hệ thống, hãy chạy các file khởi tạo dữ liệu mẫu cho từng chi nhánh:

**Trên máy HCM:**
```bash
java hcm.InitDataHCM
```

**Trên máy HN:**
```bash
java hn.InitDataHN
```

---

## **Thứ tự chạy các file**

### **1. Chạy CentralServer (máy HCM)**
```bash
java hcm.CentralServer
```
- Khởi động máy chủ tổng hợp dữ liệu toàn hệ thống.

---

### **2. Đồng bộ dữ liệu từ các chi nhánh lên CentralServer**

**Trên máy HCM:**
```bash
java hcm.LocalClientHCM
```

**Trên máy HN:**
```bash
java hn.LocalClientHN
```
- Hai lệnh này sẽ gửi toàn bộ dữ liệu nhân viên, chấm công, chi nhánh, phòng ban từ từng chi nhánh lên CentralServer.

---

### **3. Chạy LocalServer và giao diện người dùng**

#### **Trên máy HCM:**
- **Chạy LocalServerHCM:**
    ```bash
    java hcm.LocalServerHCM
    ```
- **Chạy giao diện người dùng:**
    - Nếu muốn truy cập dữ liệu tổng hợp (CentralServer):
        ```bash
        java hcm.Main_final
        ```
    - Nếu chỉ muốn truy cập dữ liệu cục bộ HCM:
        ```bash
        java hcm.Main
        ```

#### **Trên máy HN:**
- **Chạy LocalServerHN:**
    ```bash
    java hn.LocalServerHN
    ```
- **Chạy giao diện người dùng:**
    - Nếu muốn truy cập dữ liệu tổng hợp (CentralServer):
        ```bash
        java hn.Main_final
        ```
    - Nếu chỉ muốn truy cập dữ liệu cục bộ HN:
        ```bash
        java hn.Main
        ```

---

## **Ghi chú**
- **Luôn chạy các file InitData trước khi chạy hệ thống lần đầu hoặc khi cần làm mới dữ liệu.**
- Các bước trên cần thực hiện đúng thứ tự để đảm bảo dữ liệu được đồng bộ và truy cập chính xác.
- Nếu cập nhật dữ liệu ở chi nhánh, hãy chạy lại LocalClientHCM hoặc LocalClientHN để đồng bộ lên CentralServer.
- Đảm bảo các file cấu hình, thư viện và biến môi trường Java đã được thiết lập đúng.
- Địa chỉ IP và port trong code cần đúng với máy chủ thực tế bạn đang sử dụng.

---
