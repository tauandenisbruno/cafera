/*  Criado em 15 de abril de 2025
 *  Última edição em 15 de abril de 2025
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
    private String mensagem = "Houve um erro!";

    @FXML
    private Button btnFechar;

    @FXML
    private Label lbMensagem;

    public void setErro(String err)
    {
        if (err != null)
        {
            this.mensagem = err;
            this.lbMensagem.setText(mensagem);
        }
    }

    @FXML
    void initialize()
    {
        lbMensagem.setText(mensagem);
    }

    @FXML
    void actionFechar(ActionEvent event)
    {
        Stage popup = (Stage) btnFechar.getScene().getWindow();
        popup.close();
    }

}
