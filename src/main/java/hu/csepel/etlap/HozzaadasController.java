package hu.csepel.etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class HozzaadasController extends Controller {

    @FXML
    private ChoiceBox inputKategoria;
    @FXML
    private TextArea inputLeiras;
    @FXML
    private TextField inputNev;
    @FXML
    private Spinner<Integer> inputAr;

    private EtlapDb db;

    public void initialize() {
        try {
            db = new EtlapDb();
            List<Kategoria> kategoriaLista = db.getKategoria();
            for (Kategoria kategoria : kategoriaLista) {
                inputKategoria.getItems().add(kategoria.getNev());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onHozzaadClick(ActionEvent actionEvent) {
        String nev = inputNev.getText().trim();
        String leiras = inputLeiras.getText().trim();
        int kategoriaIndex = inputKategoria.getSelectionModel().getSelectedIndex();
        int ar = 0;

        if (nev.isEmpty()) {
            alert("Név megadása kötelező");
            return;
        }
        if (leiras.isEmpty()) {
            alert("Leírás megadása kötelező");
            return;
        }
        if (kategoriaIndex == -1) {
            alert("Kategória kiválasztása kötelező");
            return;
        }
        try {
            ar = inputAr.getValue();
        } catch (NullPointerException e) {
            alert("Ár megadása kötelező");
            return;
        } catch (Exception e) {
            alert("Az ár 1 és 99999 közötti szám lehet");
            return;
        }
        if (ar < 1 || ar > 99999) {
            alert("Az ár 1 és 99999 közötti szám lehet");
            return;
        }
        try {
            EtlapDb db = new EtlapDb();
            int siker = db.etelHozzaadasa(nev, leiras, kategoria_id, ar);
            if (siker == 1) {
                alert("Étel hozzáadása sikeres");
                inputNev.setText("");
                inputLeiras.setText("");
                inputKategoria.setValue("előétel");
                inputAr.getValueFactory().setValue(1000);
            } else {
                alert("Étel hozzáadása Sikertelen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
