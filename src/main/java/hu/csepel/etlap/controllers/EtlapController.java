package hu.csepel.etlap.controllers;

import hu.csepel.etlap.Controller;
import hu.csepel.etlap.Etlap;
import hu.csepel.etlap.EtlapDb;
import hu.csepel.etlap.Kategoria;
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
    @FXML
    private TableView<Kategoria> tableViewKategoria;
    @FXML
    private TableColumn<Kategoria, String> masikKategoriaCol;
    @FXML
    private ChoiceBox<String> choiceBoxSzures;

    private EtlapDb db;
    private List<Kategoria> kategoriaLista;

    public void initialize() {
        choiceBoxSzures.getItems().add("összes");
        try {
            db = new EtlapDb();
            kategoriaLista = db.getKategoria();
            for (Kategoria kategoria : kategoriaLista) {
                choiceBoxSzures.getItems().add(kategoria.getNev());
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }

        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoriaCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        arCol.setCellValueFactory(new PropertyValueFactory<>("ar"));

        masikKategoriaCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        try {
            db = new EtlapDb();
            etlapUjratoltese();
            kategoriaUjratoltese();
        } catch (SQLException e) {
            hibaKiir(e);
        }
        megkotesKivalasztas();
    }

    @FXML
    public void onUjFelveteleClick(ActionEvent actionEvent) {
        try {
            Controller hozzadas = ujAblak("hozzaadas-view.fxml", "Étel hozzáadása", 310, 277);
            hozzadas.getStage().setOnCloseRequest(event -> etlapUjratoltese());
            hozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onEtlapTorlesClick(ActionEvent actionEvent) {
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
            hibaKiir(e);
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
                if (db.etelEmelesForintOsszes(emeles)) {
                    alertWait("Sikeres emelés");
                    etlapUjratoltese();
                }
            } catch (SQLException e) {
                hibaKiir(e);
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                if (db.etelEmelesForint(emelesEtel.getId(), emeles)) {
                    alertWait("Sikeres emelés");
                    etlapUjratoltese();
                    elemLeirasaTextArea.setText("");
                } else {
                    alert("Sikertelen emelés");
                }

            } catch (SQLException e) {
                hibaKiir(e);
            }
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
                if (db.etelEmelesSzazalekOsszes(emeles)){
                    alertWait("Sikeres emelés");
                    etlapUjratoltese();
                } else {
                    alert("Sikertelen emelés");
                }
            } catch (SQLException e) {
                hibaKiir(e);
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                if (db.etelEmelesSzazalek(emelesEtel.getId(), emeles)) {
                    alertWait("Sikeres emelés");
                    etlapUjratoltese();
                    elemLeirasaTextArea.setText("");
                } else {
                    alert("Sikertelen emelés");
                }
            } catch (SQLException e) {
                hibaKiir(e);
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

    public void megkotesKivalasztas() {
        choiceBoxSzures.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            try {
                if (newValue.equals("összes")) {
                    etlapUjratoltese();
                } else {
                    List<Etlap> szurtEtlapLista = db.getSzurtEtlap(newValue);
                    etlapTableView.getItems().clear();
                    for (Etlap etlap : szurtEtlapLista) {
                        etlapTableView.getItems().add(etlap);
                    }
                }
            } catch (SQLException e) {
                hibaKiir(e);
            }
        });
    }

    private void etlapUjratoltese() {
        try {
            List<Etlap> etlapLista = db.getEtlap();
            etlapTableView.getItems().clear();
            for (Etlap etlap : etlapLista) {
                etlapTableView.getItems().add(etlap);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onHozzaadasClick(ActionEvent actionEvent) {
        try {
            Controller kategoriaHozzadas = ujAblak("kategoria-hozzaadas-view.fxml", "Kategória hozzáadása", 310, 277);
            kategoriaHozzadas.getStage().setOnCloseRequest(event -> kategoriaUjratoltese());
            kategoriaHozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onKategoriaTorlesClick(ActionEvent actionEvent) {
        int selectedIndex = tableViewKategoria.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Kategoria torlendoKategoria = tableViewKategoria.getSelectionModel().getSelectedItem();
        if (!confirm("Biztosan törölni szeretnéd a kategóriák közül: " + torlendoKategoria.getNev())) {
            return;
        }
        try {
            db.kategoriaTorlese(torlendoKategoria.getId());
            alert("Sikeres törlés");
            kategoriaUjratoltese();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    private void kategoriaUjratoltese() {
        try {
            List<Kategoria> kategoriaLista = db.getKategoria();
            tableViewKategoria.getItems().clear();
            for (Kategoria kategoria : kategoriaLista) {
                tableViewKategoria.getItems().add(kategoria);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
}