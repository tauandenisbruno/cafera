/*  Criado em 18 de abril de 2025
 *  Última edição em 11 de maio de 2025
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
import javafx.stage.Modality;
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
    private Button btnPedidosAtualizar;

    @FXML
    private Button btnProdutosAtualizar;

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
        // Ele pega automaticamente os dados dos métodos Get da classe "produto"
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

        // Ativa o botão EXCLUIR da tabela "Produto"
        tbviewProdutos.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
        {
            if (newValue != null)
            {
                btnProdutoRemove.setDisable(false);
            }
            else
            {
                btnProdutoRemove.setDisable(true);
            }
        });

        // Ativa o botão EXCLUIR da tabela "Pedidos"
        tbviewPedidos.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
        {
            if (newValue != null)
            {
                btnPedidosRemove.setDisable(false);
            }
            else
            {
                btnPedidosRemove.setDisable(true);
            }
        });

        // Ativa o botão EDITAR da tabela "Produto"
        tbviewProdutos.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
        {
            if (newValue != null)
            {
                btnProdutoEdit.setDisable(false);
            }
            else
            {
                btnProdutoEdit.setDisable(true);
            }
        });

        // Ativa o botão EDITAR da tabela "Pedidos"
        tbviewPedidos.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
        {
            if (newValue != null)
            {
                btnPedidosEdit.setDisable(false);
            }
            else
            {
                btnPedidosEdit.setDisable(true);
            }
        });
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
    void actionPedidosAtualizar(ActionEvent event)
    {
        System.out.println("btn: Atualizar tabela pedido");
    }

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
    void actionPedidosEdit(ActionEvent event)
    {
        System.out.println("btn: Editar pedido");
    }

    // Ação dos botões de "Produtos"
    @FXML
    void actionProdutosAtualizar(ActionEvent event)
    {
        tbviewProdutos.setItems(sqlite.MostrarProdutos());
    }

    @FXML
    void actionProdutoAdd(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popupAddProduto.fxml"));
        Parent root = loader.load();
        //fxpopupAddProduto AddIem = loader.getController();
        //AddIem.setFxadm(this);
        Stage AddStage = new Stage();
        AddStage.setScene(new Scene(root));
        AddStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        AddStage.setResizable(false);
        AddStage.setTitle("Adicionar novo item");
        AddStage.show();
    }

    @FXML
    void actionProdutoEdit(ActionEvent event)
    {
        System.out.println("btn: Editar produto");
    }

    // Excluir produto
    @FXML
    void actionProdutoRemove(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popupRemProduto.fxml"));
        Parent root = loader.load();
        fxpopupRemProduto conf = loader.getController();
        conf.setFxadm(this);
        Stage confStage = new Stage();
        confStage.setScene(new Scene(root));
        confStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        confStage.setResizable(false);
        confStage.setTitle("Aviso");
        confStage.show();
    }

    // Método público para poder ser acessado pela classe fxconfirmar
    public produto getProdutoSelecionado()
    {
        return tbviewProdutos.getSelectionModel().getSelectedItem();
    }

    // Método público para poder excluir o produto pela classe fxconfirmar
    public void excluirProduto(int idProduto)
    {
        sqlite.excluirProduto(idProduto);
        tbviewProdutos.setItems(sqlite.MostrarProdutos());
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
