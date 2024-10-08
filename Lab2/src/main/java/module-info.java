module com.app.lab2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.app.lab2 to javafx.fxml;
    exports com.app.lab2;
}