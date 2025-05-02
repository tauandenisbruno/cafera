/*  Criado em 18 de abril de 2025
 *  Última edição em 30 de abril de 2025
 * 
 *  Código: Tauan
 *  Desing: Tauan, Arthur
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Tela do administrador. Possui todos os recuros e direitos para fazer alterações no sistema.
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
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class fxadm
{
    // Campo de texto
    @FXML
    private TextField txtfProcurar;    

    // Botões
    @FXML
    private Button btnProcurar;

    @FXML
    private Button btnPedidosAdd;

    @FXML
    private Button btnPedidosEdit;

    @FXML
    private Button btnPedidosRemove;

    @FXML
    private Button btnProdutoAdd;

    @FXML
    private Button btnProdutoEdit;

    @FXML
    private Button btnProdutoRemove;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lbIDUsuario;

    // Aonde os elementos da janela são inicializados
    @FXML 
    public void initialize()
    {
        lbIDUsuario.setText(fxlogin.getUsr());

        // Prepara a tabela para definir qual campo será colocado em cada coluna da tabela
        // Ele pega automaticamente os dados do métodos Get
        tbcProdutosID.setCellValueFactory(new PropertyValueFactory<>("produtoID"));
        tbcProdutosNOME.setCellValueFactory(new PropertyValueFactory<>("produtoNome"));
        tbcProdutosPRECO.setCellValueFactory(new PropertyValueFactory<>("produtoPreco"));
        tbcProdutosESTOQUE.setCellValueFactory(new PropertyValueFactory<>("produtoEstoque"));

        // Formata a tabela "Preço" para que exiba "R$" e também limita a casa decimal em 2
        tbcProdutosPRECO.setCellFactory(_ -> new TableCell<produto, Double>()
            {
                @Override
                protected void updateItem(Double preco, boolean empty)
                {
                    super.updateItem(preco, empty);
                    if (empty || preco == null)
                    {
                        setText(null);
                    }
                    else
                    {
                        setText(String.format("R$ %.2f", preco));
                    }
                }
            }
        );

        // Inicializa a tabela com os dados de fato
        tbviewProdutos.setItems(sqlite.MostrarProdutos());
    }

    // Tabela PEDIDOS
    @FXML
    private TableView<?> tbviewPedidos;
    
    @FXML
    private TableColumn<?, ?> tbcPedidosCLIENTE;

    @FXML
    private TableColumn<?, ?> tbcPedidosID;

    @FXML
    private TableColumn<?, ?> tbcPedidosPRODUTO;

    @FXML
    private TableColumn<?, ?> tbcPedidosQUANTIDADE;

    @FXML
    private TableColumn<?, ?> tbcPedidosTOTAL;

    // Tabela PRODUTOS
    @FXML
    private TableView<produto> tbviewProdutos;

    @FXML
    private TableColumn<produto, Integer> tbcProdutosESTOQUE;

    @FXML
    private TableColumn<produto, Integer> tbcProdutosID;

    @FXML
    private TableColumn<produto, String> tbcProdutosNOME;

    @FXML
    private TableColumn<produto, Double> tbcProdutosPRECO;

    // Ação dos botões de "Pedidos"
    @FXML
    void actionPedidosAdd(ActionEvent event)
    {
        System.out.println("btn: Adicionar pedido");
    }

    @FXML
    void actionPedidosRemove(ActionEvent event)
    {
        System.out.println("btn: Remover pedido");
    }

    @FXML
    void btnPedidosEdit(ActionEvent event)
    {
        System.out.println("btn: Editar pedido");
    }

    // Ação dos botões de "Produtos"
    @FXML
    void actionProdutoAdd(ActionEvent event)
    {
        System.out.println("btn: Adicionar produto");
    }

    @FXML
    void actionProdutoEdit(ActionEvent event)
    {
        System.out.println("btn: Editar produto");
    }

    @FXML
    void actionProdutoRemove(ActionEvent event)
    {
        System.out.println("btn: Remover produto");
    }

    // Ação do botão de procurar
    @FXML
    void actionProcurar(ActionEvent event)
    {
        System.out.println("btn: Procurar");
    }

    
    // Ação do botão de logout
    @FXML
    void actionLogout(ActionEvent event) throws IOException
    {
        // Faz logout
        Stage fxadm = (Stage) btnLogout.getScene().getWindow();
        fxadm.close();

        // Volta para a tela de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        fxadm = new Stage();
        fxadm.setScene(new Scene(root));
        fxadm.setResizable(false);
        fxadm.setTitle("Login");
        fxadm.show();

        System.out.println("Deslogado como \"" + fxlogin.getUsr() + "\"");
    }
}
