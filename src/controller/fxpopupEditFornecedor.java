/*  Criado em 22 de maio de 2025
 *  Última edição em 11 de maio de 2025
 * 
 *  Código: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Responsável pelo popup com os campos para o usuário cadastrar um novo cliente no banco
 */

 package controller;

 import java.io.IOException;
 
 import javafx.event.ActionEvent;
 import javafx.fxml.FXML;
 import javafx.fxml.FXMLLoader;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.control.TextField;
 import javafx.scene.control.TextFormatter;
 import javafx.stage.Modality;
 import javafx.stage.Stage;

public class fxpopupEditFornecedor
{
       private fxadm fxadm;
       private int id;

    public void setFxadm(fxadm fxadm)
    {
        this.fxadm = fxadm;
        initFornecedor();
    }

    private void initFornecedor()
    {
        fornecedor fornecedorSelecionado = fxadm.getFornecedorSelecionado();

        txtfNomeFornecedor.setText(fornecedorSelecionado.getFornecedorNome());
        txtfEmailFornecedor.setText(fornecedorSelecionado.getFornecedorContato());
        id = fornecedorSelecionado.getFornecedorID();
    }

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtfEmailFornecedor;

    @FXML
    private TextField txtfNomeFornecedor;

    @FXML
    public void initialize()
    {
        // Configuração do campo Nome
        txtfNomeFornecedor.setTextFormatter(new TextFormatter<>(change ->
        {
            String text = change.getControlNewText();
            if (text.length() <= 80)
            {
                return change;
            }

            return null;
        }));

        // Configuração do campo Email
        txtfEmailFornecedor.setTextFormatter(new TextFormatter<>(change ->
        {
            if (change.getText().isEmpty() && change.getRangeStart() < change.getControlText().length())
            {
                return change; // Permitir exclusão de caracteres
            }
            String text = change.getControlNewText();
            if (text.length() > 50)
            {
                return null; // Limite de 50 caracteres
            }
            if (text.contains("@") && text.indexOf("@") != text.lastIndexOf("@"))
            {
                return null; // Mais de um "@"
            }
            if (!text.matches("^[a-zA-Z0-9._@-]+$"))
            {
                return null; // Caracteres inválidos
            }
            return change;
        }));
    }

    @FXML
    void actionCancelar(ActionEvent event)
    {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void actionConfirmar(ActionEvent event) throws IOException
    {
        
        String erro = "Ocorreu um erro!";

        // Verifica se os campos estão vazios e realiza o INSERT
        if(!txtfEmailFornecedor.getText().isEmpty() && !txtfNomeFornecedor.getText().trim().isEmpty())
        {
            sqlite.editarFornecedor(id, txtfNomeFornecedor.getText(), txtfEmailFornecedor.getText());
        }
        else
        {
            erro = "Um ou mais campos estão vazios!";
            sqlite.setErro(1);
        }

        // Verifica se o INSERT foi realizado com sucesso
        if(sqlite.getErro() != 0)
        {
            // Popup de erro da exceção
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popup.fxml"));
            Parent root = loader.load();
            fxpopup popup = loader.getController();
            popup.setErro(erro);
            Stage popstage = new Stage();
            popstage.setScene(new Scene(root));
            popstage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
            popstage.setResizable(false);
            popstage.setTitle("Aviso");
            popstage.show();
            sqlite.setErro(0);
        }
        else
        {
            fxadm.atualizarTabelas(3);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
