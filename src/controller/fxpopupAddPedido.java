package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class fxpopupAddPedido
{

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private ChoiceBox<String> choiceCliente;

    @FXML
    private ChoiceBox<String> choicePagamento;

    @FXML
    private ChoiceBox<String> choiceProduto;

    @FXML
    private DatePicker cxData;

    @FXML
    private Label labelTitulo;

    @FXML
    private TextField txtfIdProduto;

    @FXML
    private TextField txtfPrecoProdutoUni;

    @FXML
    private TextField txtfQuantidadeProdutos;

    @FXML
    private TextField txtfTotalPedido;

    @FXML
    void actionCancelar(ActionEvent event)
    {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void actionConfirmar(ActionEvent event) {

    }

}
