package hu.csepel.etlap.controllers;

import hu.csepel.etlap.Controller;
import hu.csepel.etlap.EtlapDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class KategoriaHozzaadasController extends Controller {

    @FXML
    private TextField inputKategoriaTextField;


    @FXML
    public void onHozzaadasKategoriaClick(ActionEvent actionEvent) {
        String nev = inputKategoriaTextField.getText().toString().trim();
        if (nev.isEmpty()) {
            alert("Név megadása kötelező");
            return;
        }

        try {
            EtlapDb db = new EtlapDb();
            int siker = db.kategoriaHozzaadasa(nev);
            if (siker == 1) {
                alert("Kategória hozzáadása sikeres");
                inputKategoriaTextField.setText("");
            } else {
                alert("Kategória hozzáadása Sikertelen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
