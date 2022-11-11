module com.example.af2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.af2 to javafx.fxml;
    exports com.example.af2;
}