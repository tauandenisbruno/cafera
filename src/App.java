/*  Criado em 14 de abril de 2025
 *  Última edição em 14 de abril de 2025
 * 
 *  Código: Tauan
 *  Desing: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Classe principal, cuja função é iniciar a primeira tela que é a de login.
 * 
 */

//import controller.fxlogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setResizable(false);
        Scene mainscene = new Scene(root);
        primaryStage.setTitle("Login");
        primaryStage.setScene(mainscene);
        primaryStage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
