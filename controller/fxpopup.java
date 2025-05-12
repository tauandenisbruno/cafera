/*  Criado em 15 de abril de 2025
 *  Última edição em 18 de abril de 2025
 * 
 *  Código: Tauan
 *  Desing: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Tela de aviso, possui o método setErro(String err) para definir uma mensagem personalizada.
 * 
 */

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class fxpopup
{
    @FXML
    private Button btnFechar;

    @FXML
    private Label lbMensagem;

    public void setErro(String err)
    {
        // Verifica se a passagem de parâmetro não é nula e não esteja preenchida com espaços vazios
        if (err != null && !err.isBlank()) 
        {
            this.lbMensagem.setText(err);
        }
    }

    @FXML
    void initialize()
    {
        // Inicializa a lbMensagem com a mensagemm padrão
        lbMensagem.setText("Houve um erro!");
    }

    @FXML
    void actionFechar(ActionEvent event)
    {
        // Fecha esta própria janela de aviso
        Stage popup = (Stage) btnFechar.getScene().getWindow();
        popup.close();
    }
}
