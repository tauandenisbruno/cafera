/*  Criado em 22 de maio de 2025
 *  Última edição em 11 de maio de 2025
 * 
 *  Código: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Responsável pelo popup com os campos para o usuário cadastrar um novo cliente no banco
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class fxpopupAddPedido
{
    private int ped_quantidade;
    //private String ped_nomecliente, ped_nomeproduto, erro = "Dados inválidos!";
    //private Double ped_precouni, ped_total;

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

    // DataPicker ---------------------------------
    @FXML
    private DatePicker cxData;

    @FXML
    private void setDataPickerData() {
        LocalDate dataSelecionada = cxData.getValue();

        if (dataSelecionada != null) {
            System.out.println("Data selecionada: " + dataSelecionada);
            // Aqui você pode armazenar, formatar ou utilizar essa data como quiser
        } else {
            System.out.println("Nenhuma data foi selecionada.");
        }
    }
    // -----------------------------------------------------

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
        // Adiciona a lista de Pagamentos disponíveis no banco na choicebox
        setChoiceBoxPagamento();

        // Adiciona a lista de Produto disponíveis no banco na choicebox
        setChoiceBoxProduto();

        // Adiciona a lista de Clientes disponíveis no banco na choicebox
        setChoiceBoxCliente();

        // Adiciona a data
        setDataPickerData();
         
        // Impede o campo "Quantidade" de inserir dados não números ou com ponto flutuante
        txtfQuantidadeProdutos.setTextFormatter(new TextFormatter<>(change ->
        {
            String newText = change.getControlNewText();

            if (newText.matches("[0-9]*"))
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
    void actionConfirmar(ActionEvent event) throws IOException, SQLException
    {
        try
        {
            // Faz as conversões
            ped_quantidade = Integer.parseInt(txtfQuantidadeProdutos.getText());
            
            LocalDate dataSelecionada = cxData.getValue();

            if (dataSelecionada == null) {
                System.out.println("Data não selecionada!");
                return;
            }
            
            {
                sqlite.adicionarPedido(choiceCliente.getValue(), choiceProduto.getValue(), choicePagamento.getValue(), dataSelecionada, ped_quantidade);
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
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