package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class fxpopupRemFornecedor
{
    private fxadm fxadm;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSim;

    public void setFxadm(fxadm fxadm)
    {
        this.fxadm = fxadm;
    }

    @FXML
    void actionCancelar(ActionEvent event)
    {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void actionSim(ActionEvent event) throws IOException
    {
        // Seleciona e obtém o campo ID da tabela "Cliente" e passa para os métodos públicos da classe fxadm
        fornecedor fornecedorSelecionado = fxadm.getFornecedorSelecionado();
        if (fornecedorSelecionado != null) 
        {
            int id = fornecedorSelecionado.getFornecedorID();
            fxadm.excluirFornecedor(id);
        }

        if (sqlite.getErro() != 0)
        {
            // Popup de erro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popup.fxml"));
            Parent root = loader.load();
            fxpopup popup = loader.getController();
            popup.setErro("Fornecedor vinculado a um pedido!");
            Stage popstage = new Stage();
            popstage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
            popstage.setScene(new Scene(root));
            popstage.setResizable(false);
            popstage.setTitle("Aviso");
            popstage.show();
            sqlite.setErro(0);
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
