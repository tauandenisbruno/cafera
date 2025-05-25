/*  Criado em 23 de maio de 2025
 *  Última edição em 25 de maio de 2025
 * 
 *  Código: Tauan, Arthur
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Responsável pelo popup com os campos para o usuário cadastrar um novo pedido no banco
 */

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class fxpopupAddPedido
{
    private int ped_quantidade;
    private fxadm fxadm;

    public void setFxadm(fxadm fxadm)
    {
        this.fxadm = fxadm;
    }

    // Choicebox Pagamento ---------------------------------
    @FXML
    private ChoiceBox<String> choicePagamento;

    private void setChoiceBoxPagamento()
    {
        List <String> pagamentos = sqlite.getPagamentos();
        choicePagamento.getItems().addAll(pagamentos);
        if (!pagamentos.isEmpty())
        {
            choicePagamento.setValue(pagamentos.get(0));
        }
    }
    // -----------------------------------------------------

    // Choicebox Produto ---------------------------------
    @FXML
    private ChoiceBox<String> choiceProduto;

    private void setChoiceBoxProduto()
    {
        List <String> produtos = sqlite.getProdutos();
        choiceProduto.getItems().addAll(produtos);
        if (!produtos.isEmpty())
        {
            choiceProduto.setValue(produtos.get(0));
        }
    }
    // -----------------------------------------------------

    // Choicebox Cliente ---------------------------------
    @FXML
    private ChoiceBox<String> choiceCliente;

    private void setChoiceBoxCliente()
    {
        List <String> clientes = sqlite.getClientes();
        choiceCliente.getItems().addAll(clientes);
        if (!clientes.isEmpty())
        {
            choiceCliente.setValue(clientes.get(0));
        }
    }
    // -----------------------------------------------------

    @FXML
    private DatePicker cxData;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Label labelTitulo;

    @FXML
    private TextField txtfQuantidadeProdutos;

    @FXML
    public void initialize()
    {
        // Inicializa o campo de data com a data atual
        cxData.setValue(LocalDate.now());

        // Adiciona a lista de Pagamentos disponíveis no banco na choicebox
        setChoiceBoxPagamento();

        // Adiciona a lista de Produto disponíveis no banco na choicebox
        setChoiceBoxProduto();

        // Adiciona a lista de Clientes disponíveis no banco na choicebox
        setChoiceBoxCliente();
         
        // Impede o campo "Quantidade" de inserir dados não números ou com ponto flutuante
        txtfQuantidadeProdutos.setTextFormatter(new TextFormatter<>(change ->
        {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,5}"))
            {
                return change;
            }
            else
            {
                return null;
            }
        }));

        // Configura o DatePicker para aceitar somente números e a barra
        TextField textField = cxData.getEditor();
        textField.setTextFormatter(new TextFormatter<>(change ->
        {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9/]{0,10}"))
            {
                return change;
            }
            else
            {
                return null;
            }
        }));
    }

    // Fecha a janela
    @FXML
    void actionCancelar(ActionEvent event)
    {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // Adiciona o pedido na tabela
    @FXML
    void actionConfirmar(ActionEvent event) throws IOException
    {
        try
        {
            // Faz as conversões
            ped_quantidade = Integer.parseInt(txtfQuantidadeProdutos.getText());
            
            LocalDate dataSelecionada = cxData.getValue();

            String erro = "Ocorreu um erro!";

            // Verifica se os campos estão vazios e realiza o INSERT
            if(ped_quantidade >= 0 && dataSelecionada != null)
            {
                sqlite.adicionarPedido(choiceCliente.getValue(), choiceProduto.getValue(), choicePagamento.getValue(), dataSelecionada, ped_quantidade);
            }
            else
            {
                erro = "Um ou mais campos estão vazios!";
                sqlite.setErro(1);
            }

            // Verifica se o INSERT foi realizado com sucesso
            if(sqlite.getErro() != 0)
            {
                if(sqlite.getErro() == 19)
                {
                    erro = "A data é inválida!";
                }
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
                fxadm.actionPedidosAtualizar(null);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
        catch(NumberFormatException e)
        {
            // Popup de erro da exceção
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popup.fxml"));
            Parent root = loader.load();
            Stage popstage = new Stage();
            popstage.setScene(new Scene(root));
            popstage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
            popstage.setResizable(false);
            popstage.setTitle("Aviso");
            popstage.show();

            System.out.println("Erro > " + e.getMessage());
            sqlite.setErro(0);
        }
    }
}