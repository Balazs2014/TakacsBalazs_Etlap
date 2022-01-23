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
    @FXML
    private Spinner<Integer> inputForintNoveles;
    @FXML
    private Spinner<Integer> inputSzazalekNoveles;

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
        if (!confirm("Biztosan törölni szeretnéd az étlapról:" + torlendoEtel.getNev())) {
            return;
        }
        try {
            db.etelTorlese(torlendoEtel.getId());
            alert("Sikeres törlés");
            etlapUjratoltese();
            elemLeirasaTextArea.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onEmelesForintClick(ActionEvent actionEvent) {
        int emeles = 0;
        try {
            emeles = inputForintNoveles.getValue();
        } catch (NullPointerException e) {
            alert("Az emeléshez az ár megadása kötelező");
            return;
        } catch (Exception e) {
            alert("Az ár 50 és 3000 közötti szám lehet");
            return;
        }
        if (emeles < 50 || emeles > 3000) {
            alert("Az ár 50 és 3000 közötti szám lehet");
            return;
        }

        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();
        Etlap emelesEtel = etlapTableView.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos szeretné emelni az összes étel árát?")) {
                return;
            }
            try {
                db.etelEmelesForintOsszes(emeles);
                alert("Sikeres emelés");
                etlapUjratoltese();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                db.etelEmelesForint(emelesEtel.getId(), emeles);
                alert("Sikeres emelés");
                etlapUjratoltese();
                elemLeirasaTextArea.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
        int emeles = 0;
        try {
            emeles = inputSzazalekNoveles.getValue();
        } catch (NullPointerException e) {
            alert("Az emeléshez a százalék megadása kötelező");
            return;
        } catch (Exception e) {
            alert("Az ár 5 és 50 közötti szám lehet");
            return;
        }
        if (emeles < 5 || emeles > 50) {
            alert("Az ár 5 és 50 közötti szám lehet");
            return;
        }

        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();
        Etlap emelesEtel = etlapTableView.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos szeretné emelni az összes étel árát?")) {
                return;
            }
            try {
                db.etelEmelesSzazalekOsszes(emeles);
                alert("Sikeres emelés");
                etlapUjratoltese();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                db.etelEmelesSzazalek(emelesEtel.getId(), emeles);
                alert("Sikeres emelés");
                etlapUjratoltese();
                elemLeirasaTextArea.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onEtelClick(MouseEvent event) {
        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();
        if (!(selectedIndex == -1)) {
            Etlap kiirandoLeiras = etlapTableView.getSelectionModel().getSelectedItem();
            elemLeirasaTextArea.setText(kiirandoLeiras.getLeairas());
        }
    }
}