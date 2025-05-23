/*  Criado em 08 de maio de 2025
 *  Última edição em 11 de maio de 2025
 * 
 *  Código: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Responsável pelo popup com os campos para o usuário adicionar um novo produto no estoque
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class fxpopupAddProduto
{
    private int prod_id, prod_estoque;
    private String prod_nome, erro = "Dados inválidos!";
    private Double prod_preco;
    private fxadm fxadm;

    public void setFxamd(fxadm fxadm)
    {
        this.fxadm = fxadm;
    } 

    // Choicebox Categoria ---------------------------------
    @FXML
    private ChoiceBox<String> choiceCategoria;

    private void setChoiceBoxCategoria()
    {
        List <String> categorias = sqlite.getCategorias();
        choiceCategoria.getItems().addAll(categorias);
        if (!categorias.isEmpty())
        {
            choiceCategoria.setValue(categorias.get(0));
        }
    }
    // -----------------------------------------------------

    // Choicebox Fornecedor --------------------------------
    @FXML
    private ChoiceBox<String> choiceFonecedor;

    private void setChoiceBoxFornecedor()
    {
        List <String> fornecedores = sqlite.getFornecedores();
        choiceFonecedor.getItems().addAll(fornecedores);
        if (!fornecedores.isEmpty())
        {
            choiceFonecedor.setValue(fornecedores.get(0));
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
    private TextField txtfEstoqueProduto;

    @FXML
    private TextField txtfIdProduto;

    @FXML
    private TextField txtfNomeProduto;

    @FXML
    private TextField txtfPrecoProduto;

    @FXML
    public void initialize()
    {
        // Adiciona a lista de categorias disponíveis no banco na choicebox
        setChoiceBoxCategoria();

        // Adiciona a lista de fornecedores disponíveis no banco na choicebox
        setChoiceBoxFornecedor();
         
        // Impede o campo "Estoque" de inserir dados não números ou com ponto flutuante
        txtfEstoqueProduto.setTextFormatter(new TextFormatter<>(change ->
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

        // Impede o campo "ID" de inserir dados que não são números ou com ponto flutuante
        txtfIdProduto.setTextFormatter(new TextFormatter<>(change ->
        {
            String newText = change.getControlNewText();

            if (newText.matches("^\\d{0,5}$"))
            {
                return change;
            }
            else
            {
                return null;
            }
        }));

        // Formata o campo "Preço"...
        txtfPrecoProduto.setTextFormatter(new TextFormatter<>(change ->
        {
            // Substitui ponto por vírgula
            if (change.getText().equals(","))
            {
                change.setText(".");
            }
            // Impede de inserir mais de um ponto
            String text = change.getControlNewText();
            if (text.isEmpty() || text.equals("."))
            {
                return change;
            }
            // Limita a 5 antes do ponto e a 2 depois do ponto
            if (text.matches("^\\d{0,5}(\\.\\d{0,2})?$"))
            {
                return change;
            }
            // Caso contrário, impede todo o resto...
            else 
            {
                return null;
            }
        }));

        // Limita o capo "Estoque" a no máximo 5 caracteres
        txtfEstoqueProduto.setTextFormatter(new TextFormatter<>(change ->
        {
            String text = change.getControlNewText();
            if (text.matches("^\\d{0,5}$"))
            {
                return change;
            }
            else
            {
                return null;
            }
        }));

        // Condiciona o campo "Nome"
        txtfNomeProduto.setTextFormatter(new TextFormatter<>(change ->
        {
            String text = change.getControlNewText();

            // Limita o número de caracteres e impede de começar com espaços
            if(text.isEmpty() || (text.length() <= 125 && !text.startsWith(" ")))
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

    // Adiciona o produto na tabela
    @FXML
    void actionConfirmar(ActionEvent event) throws IOException
    {
        try
        {
            // Faz as conversões
            prod_id = Integer.parseInt(txtfIdProduto.getText());
            prod_estoque = Integer.parseInt(txtfEstoqueProduto.getText());
            prod_nome = txtfNomeProduto.getText();
            prod_preco = Double.parseDouble(txtfPrecoProduto.getText());

            // Verifica se "Nome" não é nulo ou esteja preenchidos com espaços em branco
            if(prod_nome != null && !prod_nome.trim().isEmpty())
            {
                
                sqlite.adicionarProduto(prod_id, prod_nome, prod_preco, prod_estoque, choiceCategoria.getValue(), choiceFonecedor.getValue());

                // Tratamento de exceção personalizada para o SQLite [UNIQUE]
                if(sqlite.getErro() == 19)
                {
                    erro = "Dados duplicados!";
                    throw new NumberFormatException("SQLite: Tentativa de inserir dados duplicados");
                }
                else if(sqlite.getErro() == 0)
                {
                    fxadm.atualizarTabelas(1);
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    stage.close();
                }
                else
                {
                    throw new NumberFormatException("SQLite > Erro: " + sqlite.getErro());
                }
            }
            else
            {
                // Exceção personalizada
                throw new NumberFormatException("Erro forçado: \"Nome\" está vazio ou em branco");
            }
        }
        catch(NumberFormatException e)
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

            System.out.println("Erro > " + e.getMessage());
            sqlite.setErro(0);
        }
    }
}
