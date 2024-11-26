module com.app.lab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.app.lab3 to javafx.fxml;
    exports com.app.lab3;
}