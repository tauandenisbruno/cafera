package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class fxpopupEditPedido {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private ChoiceBox<?> choiceCliente;

    @FXML
    private ChoiceBox<?> choicePagamento;

    @FXML
    private ChoiceBox<?> choiceProduto;

    @FXML
    private DatePicker cxData;

    @FXML
    private Label labelTitulo;

    @FXML
    private TextField txtfQuantidadeProdutos;

    @FXML
    void actionCancelar(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void actionConfirmar(ActionEvent event) {

    }

}
