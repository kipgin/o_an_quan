module oanquan { // Tên module của bạn (đặt tùy ý)
    // 1. Xin phép dùng thư viện JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Thường cần thêm cái này để vẽ

    // 2. Mở cửa cho JavaFX vào đọc file FXML và code của bạn
    // (Vì code của bạn nằm trong package 'application' và 'controller')
    opens application to javafx.fxml, javafx.graphics;
    opens controller to javafx.fxml;

    // 3. Xuất khẩu code (để module khác dùng được - nếu cần)
    exports application;
}