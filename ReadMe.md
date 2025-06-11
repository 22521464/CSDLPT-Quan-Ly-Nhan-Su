# HỆ THỐNG PHÂN TÁN QUẢN LÝ NHÂN SỰ


**Hướng dẫn cài đặt, triển khai và sử dụng hệ thống**


## THÔNG TIN ĐỒ ÁN


- **Trường**: Trường Đại học Công nghệ Thông tin - Đại học Quốc Gia TPHCM
- **Khoa**: Hệ thống Thông tin
- **Môn học**: Hệ Cơ Sở Dữ Liệu Phân Tán - IS211
- **Nhóm sinh viên thực hiện**:
 - Phạm Hồng Trà – MSSV: 22521495
 - Giang Mỹ Tiên – MSSV: 22521464
 - Huỳnh Ngọc Trang – MSSV: 22521510


## GIỚI THIỆU HỆ THỐNG


Hệ thống mô phỏng quản lý nhân sự tại công ty có 2 chi nhánh (HCM, HN), được triển khai trên 2 máy ảo sử dụng cơ sở dữ liệu phân tán với:


- **Phân mảnh ngang** dữ liệu nhân viên và chấm công
- **Nhân bản (replication)** dữ liệu chi nhánh và phòng ban
- **Cơ sở dữ liệu**: Berkeley DB Java Edition
- **Ngôn ngữ**: Java (JDK 11 trở lên)


## YÊU CẦU CÀI ĐẶT


### Cài đặt trên mỗi máy ảo:


- Java JDK 11+
- Berkeley DB Java Edition (JE)
- Git (để clone mã nguồn nếu cần)
- IDE tùy chọn (Eclipse / IntelliJ / VS Code) hoặc biên dịch bằng dòng lệnh


### Cấu hình mạng:


- Cấu hình máy ảo sử dụng **Bridge Adapter (cầu nối WiFi)** để các máy có thể giao tiếp với nhau qua mạng LAN.
- Đảm bảo các cổng mạng sử dụng không bị chặn bởi firewall (ví dụ: `8888`, `9999`, ...).


## CẤU TRÚC THƯ MỤC


```
├── src/
│   ├── hcm/
│   ├── hn/
│   └── model/
├── lib/         ← chứa các file .jar của Berkeley DB, JSON,...
├── bin/         ← thư mục chứa file đã biên dịch
```


## KHỞI TẠO DỮ LIỆU (BẮT BUỘC TRƯỚC TIÊN)


### Trên máy HCM:


```bash
java hcm.InitDataHCM
```


### Trên máy HN:


```bash
java hn.InitDataHN
```


## TRIỂN KHAI HỆ THỐNG


### 1. Chạy CentralServer (trên máy HCM)


```bash
java hcm.CentralServer
```


> **Chức năng**: Máy chủ trung tâm nhận dữ liệu đồng bộ từ các chi nhánh và cung cấp giao diện truy vấn tập trung.


### 2. Đồng bộ dữ liệu từ chi nhánh lên CentralServer


- **Trên máy HCM**:


```bash
java hcm.LocalClientHCM
```


- **Trên máy HN**:


```bash
java hn.LocalClientHN
```


> **Chức năng**: Gửi dữ liệu từ chi nhánh (NHÂN VIÊN, CHẤM CÔNG, PHÒNG BAN, CHI NHÁNH) lên CentralServer qua kết nối mạng.


### 3. Chạy LocalServer và giao diện người dùng


#### Trên máy HCM:


- **LocalServer HCM**:


```bash
java hcm.LocalServerHCM
```


- **Main_Client** – giao diện truy vấn dữ liệu tổng hợp từ CentralServer:


```bash
java hcm.Main_Client
```


**Chức năng:**


- `adminhcm`: xem toàn bộ dữ liệu các chi nhánh
- `staffhcm`: xem thông tin chi nhánh, phòng ban


- **Main_Server** – giao diện thao tác dữ liệu cục bộ với LocalServer:


```bash
java hcm.Main_Server
```


**Chức năng:**


- `adminhcm`: xem/thêm Nhân viên, Chấm công (chi nhánh HCM)
- `staffhcm`: xem và thêm chấm công của nhân viên HCM


#### Trên máy HN:


- **LocalServer HN**:


```bash
java hn.LocalServerHN
```


- **Main_Client**:


```bash
java hn.Main_Client
```


**Chức năng:**


- `adminhn` và `staffhn`: xem chi nhánh, phòng ban (dữ liệu dùng chung)


- **Main_Server**:


```bash
java hn.Main_Server
```


**Chức năng:**


- `adminhn`: xem/thêm Nhân viên, Chấm công (chi nhánh HN)
- `staffhn`: xem và thêm chấm công của nhân viên HN


## GHI CHÚ QUAN TRỌNG


- **Luôn chạy `InitData` trước lần chạy đầu tiên** hoặc khi cần reset dữ liệu.
- Sau khi cập nhật dữ liệu cục bộ, cần chạy lại `LocalClientHCM` hoặc `LocalClientHN` để đồng bộ với CentralServer.
- Cấu hình địa chỉ IP và port phải chính xác giữa các máy để hệ thống hoạt động ổn định.
- Hệ thống sử dụng **Berkeley DB Java Edition**, một cơ sở dữ liệu NoSQL dạng key-value với khả năng mở rộng cao.


## LIÊN HỆ – HỖ TRỢ


Vui lòng liên hệ nhóm thực hiện để được hướng dẫn thêm về cách chạy hệ thống, xử lý lỗi hoặc mở rộng tính năng.