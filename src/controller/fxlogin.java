/*  Criado em 14 de abril de 2025
 *  Última edição em 20 de maio de 2025
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class fxlogin
{
    // Variáveis estáticas
    private static String version = "v0.0.12"; // Versão do programa
    private static int usr = 2; // Tipo de usuário (0 : Funcionário / 1 : Administrdor / >1 : inválido)
    private static String[] login = {"Funcionario", "Administrador"}; // Login dos usuários cadastrados
    private static int[] pwd = {12345, 54321}; // As senhas dos usuários cadastrados

    // Variáveis padrão
    private int pwdconv; // Armazena a senha convertida de "txtfSenha()"
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
        
        // Impede qualquer coisa que não sejam números de serem digitadas no campo "senha" com limite de 10 caracteres
        txtfSenha.setTextFormatter(new TextFormatter<>(change ->
        {
            String newText = change.getControlNewText();

            if (newText.matches("^\\d{0,10}$"))
            {
                return change;
            }
            else
            {
                return null;
            }
        }));

        // Limita o número mácximo de caracteres a 15
        txtfUsuario.setTextFormatter(new TextFormatter<>(change ->
        {
            String newText = change.getControlNewText();

            if(newText.matches("^\\S{0,15}$"))
            {
                return change;
            }
            else
            {
                return null;
            }
        }));
    }

    // Aqui é feito o teste para verificar nome e usuário para fazer login
    @FXML
    void actionConfirmar(ActionEvent event) throws IOException
    {
        try
        { 
            // Verifica se já não tem outro popup aberto
            if (popstage != null && popstage.isShowing()) 
            {
                return;
            }

            // Recebe a conversão do campo de senha para int
            pwdconv = Integer.parseInt(txtfSenha.getText()); 

            // Verifica o login
            if ((txtfUsuario.getText().equals(login[0])) && (pwdconv == pwd[0]))
            {
                // Funcionario...
                usr = 0;
                
                System.out.println("Logado como \"" + getUsr() + "\"");
                
                // Chama a tela do Funcionario
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adm.fxml"));
                Parent root = loader.load();
                Stage admstage = new Stage();
                admstage.setMinWidth(800);
                admstage.setMinHeight(650);
                admstage.setScene(new Scene(root));
                admstage.setResizable(true);
                admstage.setTitle("Funcionario");
                admstage.show();

                // Fecha janela de login
                Stage fxlogin = (Stage) btnConfirmar.getScene().getWindow();
                fxlogin.close();
            }
            else if ((txtfUsuario.getText().equals(login[1])) && (pwdconv == pwd[1]))
            {
                // Administrador...
                usr = 1;

                System.out.println("Logado como \"" + getUsr() + "\"");
                       
                txtfSenha.clear();
                txtfUsuario.clear();

                // Chama a tela do administrador
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adm.fxml"));
                Parent root = loader.load();
                Stage admstage = new Stage();
                admstage.setMinWidth(800);
                admstage.setMinHeight(650);
                admstage.setScene(new Scene(root));
                admstage.setResizable(true);
                admstage.setTitle("Administrador");
                admstage.show();

                // Fecha janela de login
                Stage fxlogin = (Stage) btnConfirmar.getScene().getWindow();
                fxlogin.close();
            }
            else
            {
                // Senha ou usuário inválidos...
                usr = 2;

                // Popup de erro
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popup.fxml"));
                Parent root = loader.load();
                fxpopup popup = loader.getController();
                popup.setErro("Usuário ou senha inválidos!");
                popstage = new Stage();
                popstage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
                popstage.setScene(new Scene(root));
                popstage.setResizable(false);
                popstage.setTitle("Aviso");
                popstage.show();

                System.out.println("Erro: Usuário ou senha inválidos!");
            }
        }
        catch (NumberFormatException e) 
        {
            // Caso não tenha digitado nada em um ou ambos os campos
            if (popstage != null && popstage.isShowing())
            {
                return;
            }

            // Popup de erro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popup.fxml"));
            Parent root = loader.load();
            fxpopup popup = loader.getController();
            popup.setErro("O campo não pode estar vazio!");
            popstage = new Stage();
            popstage.setScene(new Scene(root));
            popstage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
            popstage.setResizable(false);
            popstage.setTitle("Aviso");
            popstage.show();

            System.out.println("Erro: " + e.getCause() + "\nMotivo: " + e.getMessage());
        }
    }

    // Retorna o ID Int do usuário que fez login
    public static int getID() 
    {
        return usr;
    }

    // Retorna o nome String do usuário que fez login
    public static String getUsr()
    {
        String usr = "erro";
        switch (getID())
        {
            case 0:
                usr = login[0];
                break;
            
            case 1:
                usr = login[1];
                break;

            default:
                usr = "Inválido";
                break;
        }
        return usr;
    }
}
