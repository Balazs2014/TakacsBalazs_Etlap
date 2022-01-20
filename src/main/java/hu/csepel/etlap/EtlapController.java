package hu.csepel.etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class EtlapController extends Controller {

    @FXML
    private TableView<Etlap> etlapTableView;
    @FXML
    private TableColumn<Etlap, String> kategoriaCol;
    @FXML
    private TableColumn<Etlap, Integer> arCol;
    @FXML
    private TableColumn<Etlap, String> nevCol;
    @FXML
    private TextArea elemLeirasaTextArea;

    private EtlapDb db;

    public void initialize() {
        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoriaCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        arCol.setCellValueFactory(new PropertyValueFactory<>("ar"));
        try {
            db = new EtlapDb();
            List<Etlap> etlapLista = db.getEtlap();
            for (Etlap etlap : etlapLista) {
                etlapTableView.getItems().add(etlap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void onTorlesClick(ActionEvent actionEvent) {
        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Etlap torlendoEtel = etlapTableView.getSelectionModel().getSelectedItem();
        try {
            db.etelTorlese(torlendoEtel.getId());
            alert("Sikeres törlés");
            etlapUjratoltese();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onEmelesForintClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onUjFelveteleClick(ActionEvent actionEvent) {
        try {
            Controller hozzadas = ujAblak("hozzaadas-view.fxml", "Étel hozzáadása", 310, 277);
            hozzadas.getStage().setOnCloseRequest(event -> etlapUjratoltese());
            hozzadas.getStage().show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void etlapUjratoltese() {
        try {
            List<Etlap> etlapLista = db.getEtlap();
            etlapTableView.getItems().clear();
            for (Etlap etlap : etlapLista) {
                etlapTableView.getItems().add(etlap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onEmelesSzazalekClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onEtelClick(MouseEvent event) {
        Etlap kiirandoLeiras = etlapTableView.getSelectionModel().getSelectedItem();
        elemLeirasaTextArea.setText(kiirandoLeiras.getLeairas());
    }
}