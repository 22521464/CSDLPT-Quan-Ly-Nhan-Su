package Model;

public class AccessControl {
    // Kiểm tra quyền xem dữ liệu
    public static boolean canView(String table, AccessLevel userRole, String recordLocation) {
        switch (table) {
            case "CHINHANH":
            case "PHONGBAN":
                // Mọi user đều xem được
                return true;
            case "NHANVIEN":
                if (userRole == AccessLevel.ADMIN_HCM) return true; // Xem cả HCM & HN
                if (userRole == AccessLevel.ADMIN_HN || userRole == AccessLevel.STAFF_HN)
                    return "HN".equalsIgnoreCase(recordLocation);
                if (userRole == AccessLevel.STAFF_HCM)
                    return "HCM".equalsIgnoreCase(recordLocation);
                break;
            case "CHAMCONG":
                if (userRole == AccessLevel.ADMIN_HCM) return true; // Xem cả HCM & HN
                if (userRole == AccessLevel.ADMIN_HN || userRole == AccessLevel.STAFF_HN)
                    return "HN".equalsIgnoreCase(recordLocation);
                if (userRole == AccessLevel.STAFF_HCM)
                    return "HCM".equalsIgnoreCase(recordLocation);
                break;
        }
        return false;
    }

    // Kiểm tra quyền thêm dữ liệu
    public static boolean canAdd(String table, AccessLevel userRole, String recordLocation) {
        switch (table) {
            case "CHINHANH":
            case "PHONGBAN":
                // Chỉ adminHCM được thêm
                return userRole == AccessLevel.ADMIN_HCM;
            case "NHANVIEN":
                if (userRole == AccessLevel.ADMIN_HCM) return true; // Thêm cả HCM & HN
                if (userRole == AccessLevel.ADMIN_HN)
                    return "HN".equalsIgnoreCase(recordLocation); // Chỉ thêm nhân viên HN
                break;
            case "CHAMCONG":
                if (userRole == AccessLevel.ADMIN_HCM) return true; // Thêm cả HCM & HN
                if (userRole == AccessLevel.ADMIN_HN || userRole == AccessLevel.STAFF_HN)
                    return "HN".equalsIgnoreCase(recordLocation); // Thêm chấm công HN
                if (userRole == AccessLevel.STAFF_HCM)
                    return "HCM".equalsIgnoreCase(recordLocation); // Thêm chấm công HCM
                break;
        }
        return false;
    }
}