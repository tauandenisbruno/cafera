/*  Criado em 08 de mario de 2025
 *  Última edição em 08 de maio de 2025
 * 
 *  Código: Tauan
 *  Desing: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Popup que pergunta ao usuário se ele realmente quer excluir um produto do banco.
 * 
 */

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class fxconfirmar
{
    private fxadm fxadm;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSim;

    @FXML
    private Label labelMensagem;

    public void setFxadm(fxadm fxadm)
    {
        this.fxadm = fxadm;
    }

    @FXML
    void actionCancelar(ActionEvent event)
    {
        System.out.println("btn: Cancelar");
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void actionSim(ActionEvent event)
    {
        System.out.println("btn: Sim");

        // Seleciona e obtém o campo ID da tabela "Produto" e passa para os métodos públicos da classe fxadm
        produto produtoSelecionado = fxadm.getProdutoSelecionado();
        if (produtoSelecionado != null) 
        {
            int idProduto = produtoSelecionado.getProdutoID();
            fxadm.excluirProduto(idProduto);
        }
        
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
