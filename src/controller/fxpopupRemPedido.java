package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class fxpopupRemPedido {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSim;

    @FXML
    private Label labelMensagem;

    @FXML
    void actionCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void actionSim(ActionEvent event) {

    }

}
