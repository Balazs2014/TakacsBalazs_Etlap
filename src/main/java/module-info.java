module hu.csepel.etlap {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.csepel.etlap to javafx.fxml;
    exports hu.csepel.etlap;
}