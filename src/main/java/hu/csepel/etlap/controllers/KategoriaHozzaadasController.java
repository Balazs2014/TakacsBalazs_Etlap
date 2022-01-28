package hu.csepel.etlap.controllers;

import hu.csepel.etlap.Controller;
import hu.csepel.etlap.EtlapDb;
import hu.csepel.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class KategoriaHozzaadasController extends Controller {

    @FXML
    private TextField inputKategoriaTextField;

    private EtlapDb db;
    private List<Kategoria> kategoriaLista;

    public void initialize() {
        try {
            db = new EtlapDb();
            kategoriaLista = db.getKategoria();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onHozzaadasKategoriaClick(ActionEvent actionEvent) {
        String nev = inputKategoriaTextField.getText().toString().trim();
        if (nev.isEmpty()) {
            alert("Név megadása kötelező");
            return;
        }

        try {
            int i = 0;
            int listaHossza = kategoriaLista.size();
            while (i < listaHossza && !kategoriaLista.get(i).getNev().equals(nev.toLowerCase())) {
                i++;
            }
            if (i < listaHossza) {
                alert(nev + " már benne van a listában");
                inputKategoriaTextField.setText("");
            } else {
                EtlapDb db = new EtlapDb();
                int siker = db.kategoriaHozzaadasa(nev.toLowerCase());
                if (siker == 1) {
                    alert("Kategória hozzáadása sikeres");
                    inputKategoriaTextField.setText("");
                } else {
                    alert("Kategória hozzáadása sikertelen");
                }
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
}
