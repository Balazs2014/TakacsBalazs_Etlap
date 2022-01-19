package hu.csepel.etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class EtlapController {

    @FXML
    private TableView<Etel> etlapTableView;
    @FXML
    private TableColumn<Etel, String> ketegoriaCol;
    @FXML
    private TableColumn<Etel, Integer> arCol;
    @FXML
    private TableColumn<Etel, String> nevCol;
    @FXML
    private TextArea elemLeirasaTextArea;

    public void initialize() {

    }

    @FXML
    public void onTorlesClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onEmelesSzazalekClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onEmelesForintClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onUjFelveteleClick(ActionEvent actionEvent) {
    }
}