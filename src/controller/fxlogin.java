/*  Criado em 14 de abril de 2025
 *  Última edição em 15 de abril de 2025
 * 
 *  Código: Tauan
 *  Desing: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Tela de login, é a primera janela a ser exibida ao usuário quando o programa for rodado.
 * 
 */

package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class fxlogin
{
    private String version = "v0.0.3";
    private int pwdconv; // Armazena a senha convertida de "txtfSenha()"
    private int usr = 0; // Tipo de usuário (0 : OPERACIONAL / 1 : ADMINISTRADOR / >1 : INVÁLIDO)
    private int[] pwd = {12345, 54321}; // As senhas dos usuários cadastrados
    private String[] login = {"Funcionario", "Administrador"}; // Login dos usuários cadastrados
    private Stage popstage; // Variável do tipo Stage

    @FXML
    private Label lbVersion;

    @FXML
    private Button btnConfirmar;

    @FXML
    private PasswordField txtfSenha;

    @FXML
    private TextField txtfUsuario;

    @FXML
    public void initialize()
    {
        // Exibe a versão do programa
        lbVersion.setText(version);
        
        // Impede qualquer coisa que não sejam números de serem digitadas no campo "senha"
        txtfSenha.setTextFormatter(new TextFormatter<>(change ->
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

    @FXML
    void actionConfirmar(ActionEvent event) throws IOException
    {
        try
        { 
            pwdconv = Integer.parseInt(txtfSenha.getText()); // Recebe a conversão do campo de senha para int

            if ((txtfUsuario.getText().equals(login[0])) && (pwdconv == pwd[0]))
            {
                // Funcionario...
                usr = 0;
                
                System.out.println("Logado como \"Fucionário\""); // Aqui chamaria a tela do funcionário
                txtfSenha.clear();
                txtfUsuario.clear();
            }
            else if ((txtfUsuario.getText().equals(login[1])) && (pwdconv == pwd[1]))
            {
                // Administrador...
                usr = 1;

                System.out.println("Logado como \"Administrador\""); // Aqui chamaria a tela do administrador
                txtfSenha.clear();
                txtfUsuario.clear();
            }
            else
            {
                // Senha ou usuário inválidos...
                usr = 2;

                // Abre o popup de erro
                if (popstage != null && popstage.isShowing()) // Verifica se já não tem outro popup aberto
                {
                    return;
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popup.fxml"));
                Parent root = loader.load();
                fxpopup popup = loader.getController();
                popup.setErro("Usuário ou senha inválidos!");
                popstage = new Stage();
                popstage.setScene(new Scene(root));
                popstage.setResizable(false);
                popstage.setTitle("Aviso");
                popstage.show();

                System.out.println("Erro: Usuário ou senha inválidos!");
                txtfSenha.clear();
                txtfUsuario.clear();
            }

        }
        catch (NumberFormatException e) 
        {
            // Caso não tenha digitado nada em um ou ambos os campos

            // Popup de erro
            if (popstage != null && popstage.isShowing()) // Verifica se já não tem outro popup aberto
            {
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popup.fxml"));
            Parent root = loader.load();
            fxpopup popup = loader.getController();
            popup.setErro("O campo não pode estar vazio!");
            popstage = new Stage();
            popstage.setScene(new Scene(root));
            popstage.setResizable(false);
            popstage.setTitle("Aviso");
            popstage.show();

            System.out.println("Erro: " + e.getMessage());
        }
    }

    public int getUsr()
    {
        return usr;
    }

}
