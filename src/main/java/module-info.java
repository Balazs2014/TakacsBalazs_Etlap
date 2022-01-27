module hu.csepel.etlap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens hu.csepel.etlap to javafx.fxml;
    exports hu.csepel.etlap;
    exports hu.csepel.etlap.controllers;
    opens hu.csepel.etlap.controllers to javafx.fxml;
}