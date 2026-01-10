package com.code.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpScreenController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private TextArea txtContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtContent.setText(
                "Trong trò chơi Ô ăn quan truyền thống, sỏi được chia thành 2 loại chính với kích thước và giá trị quy đổi khác nhau để phân biệt vai trò trên bàn cờ:\n\n"
                        +
                        "1. Quân Quan (Cái)\n" +
                        "Số lượng: 2 viên, mỗi ô quan một viên\n" +
                        "Đặc điểm: Thường là những viên sỏi hoặc đá có kích thước lớn nhất, hình dáng nổi bật (thường to gấp 5-10 lần quân dân).\n"
                        +
                        "Vị trí: Đặt vào 2 ô hình bán nguyệt ở hai đầu bàn cờ.\n" +
                        "Giá trị: Được quy đổi bằng 5 quân dân,\n\n" +
                        "2. Quân Dân (Con)\n" +
                        "Số lượng: 50 viên.\n" +
                        "Đặc điểm: Những viên sỏi, đá hoặc hạt nhỏ, kích thước đồng đều nhau, vừa lòng bàn tay để dễ dàng cầm nắm và rải quân.\n"
                        +
                        "Vị trí: Chia đều vào 10 ô vuông (ô dân), mỗi ô có 5 quân.\n" +
                        "Giá trị: Mỗi quân dân tính là 1 điểm.\n\n" +
                        "Luật cơ bản\n" +
                        "1. Bàn chơi gồm 10 ô dân (mỗi ô 5 quân) và 2 ô quan (mỗi ô 10 điểm).\n" +
                        "2. Người chơi chọn 1 ô dân của mình để rải quân.\n" +
                        "3. Rải lần lượt từng viên vào các ô tiếp theo theo chiều đã chọn.\n" +
                        "4. Nếu viên cuối cùng rơi vào ô có quân, bốc tiếp để rải.\n" +
                        "5. Nếu viên cuối cùng rơi vào ô trống, ăn các quân ở ô tiếp theo.\n" +
                        "6. Trò chơi kết thúc khi 2 ô Quan bị ăn hết.\n\n" +
                        "Luật nâng cao\n" +
                        "1. Quy tắc \"Ô trống sát ô trống\" và \"Ô Quan\"\n" +
                        "Mất lượt: Nếu viên cuối cùng rơi vào một ô trống, mà ô tiếp theo sau đó cũng là ô trống hoặc là ô Quan, thì người chơi bị mất lượt (không được ăn).\n"
                        +
                        "Ăn chuỗi (Ăn lồng): Nếu sau khi ăn một ô, ô tiếp theo lại là ô trống và ô sau đó nữa có quân, bạn được quyền ăn tiếp ô đó. Việc này có thể lặp lại nhiều lần trong một lượt.\n\n"
                        +
                        "2. Quy tắc rải quân tại ô Quan\n" +
                        "Khi rải quân, nếu viên cuối cùng rơi vào ngay trước ô Quan, bạn không được phép bốc quân trong ô Quan để rải tiếp (ô Quan chỉ để ăn, không để bốc). Bạn phải dừng lại và mất lượt.\n\n"
                        +
                        "3. Quy tắc \"Hết quân trên hàng\" (Rất quan trọng)\n" +
                        "Đến lượt mình nhưng tất cả 5 ô dân thuộc quyền quản lý của mình đều trống, bạn phải lấy 5 quân từ quỹ điểm (quân đã ăn được) để rải vào mỗi ô 1 viên rồi mới bắt đầu chơi.\n"
                        +
                        "Nếu không còn đủ 5 quân, bạn phải \"vay\" của đối phương và trả lại khi tính điểm cuối cùng.");

        if (btnBack != null) {
            btnBack.setOnAction(e -> handleBack());
        }
    }

    private void handleBack() {
        NavigationController.getInstance().showMainMenu();
    }
}