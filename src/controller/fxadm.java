/*  Criado em 18 de abril de 2025
 *  Última edição em 23 de maio de 2025
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
import java.time.LocalDate;

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
    private Button btnClienteEdit;

    @FXML
    private Button btnClienteAdd;

    @FXML
    private Button btnClienteAtualizar;

    @FXML
    private Button btnClienteRemove;

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
    private Button btnFornecedorAdd;

    @FXML
    private Button btnFornecedorRemove;

    @FXML
    private Button btnFornecedorEdit;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lbIDUsuario;

    // Aonde os elementos da janela são inicializados
    @FXML 
    public void initialize()
    {
        lbIDUsuario.setText(fxlogin.getUsr());

        // Prepara as tabelas
        // Ele pega automaticamente os dados dos métodos Get da classe "produto"
        tbcProdutosID.setCellValueFactory(new PropertyValueFactory<>("produtoID"));
        tbcProdutosNOME.setCellValueFactory(new PropertyValueFactory<>("produtoNome"));
        tbcProdutosPRECO.setCellValueFactory(new PropertyValueFactory<>("produtoPreco"));
        tbcProdutosESTOQUE.setCellValueFactory(new PropertyValueFactory<>("produtoEstoque"));
        tbcProdutosCATEGORIA.setCellValueFactory(new PropertyValueFactory<>("produtoCategoria"));
        tbcProdutosFORNECEDOR.setCellValueFactory(new PropertyValueFactory<>("produtoFornecedor"));

        // Ele pega automaticamente os dados dos métodos Get da classe "cliente"
        tbcClienteNome.setCellValueFactory(new PropertyValueFactory<>("ClienteNome"));
        tbcClienteCPF.setCellValueFactory(new PropertyValueFactory<>("ClienteCPF"));
        tbcClienteEmail.setCellValueFactory(new PropertyValueFactory<>("ClienteEmail"));

        // Ele pega automaticamente os dados dos métodos Get da classe "pedidos"
        tbcPedidosID.setCellValueFactory(new PropertyValueFactory<>("PedidoId"));
        tbcPedidosCLIENTE.setCellValueFactory(new PropertyValueFactory<>("PedidoNomeCliente"));
        tbcPedidosDATA.setCellValueFactory(new PropertyValueFactory<>("PedidoData"));
        tbcPedidosPRODUTO.setCellValueFactory(new PropertyValueFactory<>("PedidoNomeProduto"));
        tbcPedidosPAGAMENTO.setCellValueFactory(new PropertyValueFactory<>("PedidoPagamento"));
        tbcPedidosPRECO.setCellValueFactory(new PropertyValueFactory<>("PedidoPrecoUnitario"));
        tbcPedidosQUANTIDADE.setCellValueFactory(new PropertyValueFactory<>("PedidoUnidades"));
        tbcPedidosTOTAL.setCellValueFactory(new PropertyValueFactory<>("PedidoPrecoTotal"));

        // Ele pega automaticamente os dados dos métodos Get da classe "fornecedor"
        tbcFornecedorID.setCellValueFactory(new PropertyValueFactory<>("FornecedorID"));
        tbcFornecedorNOME.setCellValueFactory(new PropertyValueFactory<>("FornecedorNome"));
        tbcFornecedorCONTATO.setCellValueFactory(new PropertyValueFactory<>("FornecedorContato"));

        // Inicializa a tabela com os dados de fato
        tbviewProdutos.setItems(sqlite.MostrarProdutos());
        tbviewCliente.setItems(sqlite.MostrarClientes());
        tbviewPedidos.setItems(sqlite.MostrarPedidos());
        tbviewFornecedor.setItems(sqlite.MostrarFornecedores());

        // [PRODUTO - PRECO] Formata a tabela "Preço" para que exiba "R$" e também limita a casa decimal em 2
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

        // [PEDIDOS - PRECO] Formata a tabela "Preço" para que exiba "R$" e também limita a casa decimal em 2
        tbcPedidosPRECO.setCellFactory(_ -> new TableCell<pedido, Double>()
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

        // [PEDIDOS - VLR_TOTAL] Formata a tabela "Preço" para que exiba "R$" e também limita a casa decimal em 2
        tbcPedidosTOTAL.setCellFactory(_ -> new TableCell<pedido, Double>()
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

        // [RESTRIÇÕES] Verifica se é funcionário ou administrador ---------------------------------------
        if (fxlogin.getID() != 0) 
        {
            // Ativa o botão REMOVER da tabela "Produto" para adiministrador
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

            // Ativa o botão REMOVER da tabela "Fornecedores" para administrador
            tbviewFornecedor.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
            {
                if (newValue != null)
                {
                    btnFornecedorRemove.setDisable(false);
                }
                else
                {
                    btnFornecedorRemove.setDisable(true);
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

            // Ativa o botão EDITAR da tabela "Fornecedor"
            tbviewFornecedor.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
            {
                if (newValue != null)
                {
                    btnFornecedorEdit.setDisable(false);
                }
                else
                {
                    btnFornecedorEdit.setDisable(true);
                }
            });

            // Ativa o botão ADICIONAR da tabela "Produtos" para adiministrador
            btnProdutoAdd.setDisable(false);

            // Ativa o botão ADICIONAR da tabela "Fornecedor" para administrador
            btnFornecedorAdd.setDisable(false);     
        } // -------------------------------------------------------------------------------------------------

        // Ativa o botão REMOVER da tabela "Pedidos"
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

        // Ativa o botão REMOVER da tabela "Cliente"
        tbviewCliente.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
        {
            if (newValue != null)
            {
                btnClienteRemove.setDisable(false);
            }
            else
            {
                btnClienteRemove.setDisable(true);
            }
        });

        // Ativa o botão EDITAR da tabela "Cliente"
        tbviewCliente.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
        {
            if (newValue != null)
            {
                btnClienteEdit.setDisable(false);
            }
            else
            {
                btnClienteEdit.setDisable(true);
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
    private TableView<pedido> tbviewPedidos;

    @FXML
    private TableColumn<pedido, Integer> tbcPedidosID;

    @FXML
    private TableColumn<pedido, String> tbcPedidosCLIENTE;

    @FXML
    private TableColumn<pedido, String> tbcPedidosDATA;

    @FXML
    private TableColumn<pedido, String> tbcPedidosPRODUTO;

    @FXML
    private TableColumn<pedido, String> tbcPedidosPAGAMENTO;

    @FXML
    private TableColumn<pedido, Double> tbcPedidosPRECO;

    @FXML
    private TableColumn<pedido, Integer> tbcPedidosQUANTIDADE;

    @FXML
    private TableColumn<pedido, Double> tbcPedidosTOTAL;

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

    @FXML
    private TableColumn<produto, String> tbcProdutosCATEGORIA;

    @FXML
    private TableColumn<produto, String> tbcProdutosFORNECEDOR;

    // Tabela CLIENTES
    @FXML
    private TableView<cliente> tbviewCliente;

    @FXML
    private TableColumn<cliente, String> tbcClienteCPF;

    @FXML
    private TableColumn<cliente, String> tbcClienteEmail;

    @FXML
    private TableColumn<cliente, String> tbcClienteNome;

    // Tabela FORNECEDORES
    @FXML
    private TableView<fornecedor> tbviewFornecedor;

    @FXML
    private TableColumn<fornecedor, String> tbcFornecedorCONTATO;

    @FXML
    private TableColumn<fornecedor, Integer> tbcFornecedorID;

    @FXML
    private TableColumn<fornecedor, String> tbcFornecedorNOME;

    // Ação dos botões de "Pedidos"
    @FXML
    void actionPedidosAtualizar(ActionEvent event)
    {
         tbviewPedidos.setItems(sqlite.MostrarPedidos());
    }

    // ========================================================================================================
    @FXML
    void actionPedidosAdd(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pedido/popupAddPedido.fxml"));
        Parent root = loader.load();
        Stage AddStage = new Stage();
        fxpopupAddPedido add = loader.getController();
        add.setFxadm(this);
        AddStage.setScene(new Scene(root));
        AddStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        AddStage.setResizable(false);
        AddStage.setTitle("Adicionar novo item");
        AddStage.show();
    }

        @FXML
    void actionPedidosRemove(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pedido/popupRemPedido.fxml"));
        Parent root = loader.load();
        fxpopupRemPedido conf = loader.getController();
        conf.setFxadm(this);
        Stage confStage = new Stage();
        confStage.setScene(new Scene(root));
        confStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        confStage.setResizable(false);
        confStage.setTitle("Aviso");
        confStage.show();
    }

    @FXML
    void actionPedidosEdit(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pedido/popupEditPedido.fxml"));
        Parent root = loader.load();
        fxpopupEditPedido edit = loader.getController();
        edit.setFxadm(this); // mandar a instância atual para o popup de adicionar o produto
        Stage AddStage = new Stage();
        AddStage.setScene(new Scene(root));
        AddStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        AddStage.setResizable(false);
        AddStage.setTitle("Editar um item");
        AddStage.show();
    }

    // Método público para poder ser acessado pela classe fxconfirmar
    public pedido getPedidoSelecionado()
    {
        return tbviewPedidos.getSelectionModel().getSelectedItem();
    }

    // Método público para poder editar o produto pela classe fxpopupEditPedido
    public void editarPedido(int id, String cliente, String produto, String pagamento, LocalDate dataSelecionada, int quantidade)
    {
        sqlite.editarPedido(id, cliente, produto, pagamento, dataSelecionada, quantidade);
    }

    // Método público para poder excluir o pedido pela classe fxpopupRemPedido
    public void excluirPedido(int idPedido)
    {
        sqlite.excluirPedido(idPedido);
        tbviewPedidos.setItems(sqlite.MostrarPedidos());
    }

    // ========================================================================================================

    // [PRODUTO] AÇÃO DOS BOTÕES
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
        fxpopupAddProduto add = loader.getController();
        add.setFxamd(this);
        Stage AddStage = new Stage();
        AddStage.setScene(new Scene(root));
        AddStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        AddStage.setResizable(false);
        AddStage.setTitle("Adicionar novo item");
        AddStage.show();
    }

    @FXML
    void actionProdutoEdit(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popupEditProduto.fxml"));
        Parent root = loader.load();
        fxpopupEditProduto edit = loader.getController();
        edit.setFxadm(this); // mandar a instância atual para o popup de adicionar o produto
        Stage AddStage = new Stage();
        AddStage.setScene(new Scene(root));
        AddStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        AddStage.setResizable(false);
        AddStage.setTitle("Editar um item");
        AddStage.show();
    }

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

    // [CLIENTE] AÇÃO DOS BOTÕES
    @FXML
    void actionClienteAtualizar(ActionEvent event)
    {
        tbviewCliente.setItems(sqlite.MostrarClientes());
    }

    @FXML
    void actionClienteAdd(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cliente/popupAddCliente.fxml"));
        Parent root = loader.load();
        fxpopupAddCliente add = loader.getController();
        add.setFxadm(this);
        Stage AddStage = new Stage();
        AddStage.setScene(new Scene(root));
        AddStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        AddStage.setResizable(false);
        AddStage.setTitle("Cadastrar novo cliente");
        AddStage.show();
    }

    @FXML
    void actionClienteRemove(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cliente/popupRemCliente.fxml"));
        Parent root = loader.load();
        fxpopupRemCliente conf = loader.getController();
        conf.setFxadm(this);
        Stage confStage = new Stage();
        confStage.setScene(new Scene(root));
        confStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        confStage.setResizable(false);
        confStage.setTitle("Aviso");
        confStage.show();
    }

    @FXML
    void actionClienteEdit(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cliente/popupEditCliente.fxml"));
        Parent root = loader.load();
        fxpopupEditCliente edit = loader.getController();
        edit.setFxadm(this); // mandar a instância atual para o popup de adicionar o produto
        Stage AddStage = new Stage();
        AddStage.setScene(new Scene(root));
        AddStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        AddStage.setResizable(false);
        AddStage.setTitle("Editar dados do Cliente");
        AddStage.show();
    }

    // [FORNECEDOR] AÇÃO DOS BOTÕES
    @FXML
    void actionFornecedorAdd(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fornecedor/popupAddFornecedor.fxml"));
        Parent root = loader.load();
        fxpopupAddFornecedor add = loader.getController();
        add.setFxadm(this);
        Stage AddStage = new Stage();
        AddStage.setScene(new Scene(root));
        AddStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        AddStage.setResizable(false);
        AddStage.setTitle("Cadastrar novo fornecedor");
        AddStage.show();
    }

    @FXML
    void actionFornecedorRemove(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fornecedor/popupRemFornecedor.fxml"));
        Parent root = loader.load();
        fxpopupRemFornecedor conf = loader.getController();
        conf.setFxadm(this);
        Stage confStage = new Stage();
        confStage.setScene(new Scene(root));
        confStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        confStage.setResizable(false);
        confStage.setTitle("Aviso");
        confStage.show();
    }

    @FXML
    void actionFornecedorEdit(ActionEvent event) throws IOException
    {
        // Popup de confirmação
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fornecedor/popupEditFornecedor.fxml"));
        Parent root = loader.load();
        fxpopupEditFornecedor edit = loader.getController();
        edit.setFxadm(this); // mandar a instância atual para o popup de adicionar o produto
        Stage AddStage = new Stage();
        AddStage.setScene(new Scene(root));
        AddStage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela "pai"
        AddStage.setResizable(false);
        AddStage.setTitle("Editar dados do fornecedor");
        AddStage.show();
    }

    // Atualiza o conteúdo da tabela selecionada
    public void atualizarTabelas(int tabela)
    {
        if(tabela == 1)
        {
            tbviewProdutos.setItems(sqlite.MostrarProdutos());
        }
        else if (tabela == 2)
        {
            tbviewPedidos.setItems(sqlite.MostrarPedidos());
        }
        else if(tabela == 3)
        {
            tbviewFornecedor.setItems(sqlite.MostrarFornecedores());
        }
        else if (tabela == 4)
        {
            tbviewCliente.setItems(sqlite.MostrarClientes());
        }
    }

    // Método público para poder ser acessado pela classe fxpopupRemProduto
    public produto getProdutoSelecionado()
    {
        return tbviewProdutos.getSelectionModel().getSelectedItem();
    }

    // Método público para poder ser acessado pela classe fxpopupRemCliente
    public cliente getClienteSelecionado()
    {
        return tbviewCliente.getSelectionModel().getSelectedItem();
    }

    // Método público para poder ser acessado pela classe fxpopupRemFornecedor
    public fornecedor getFornecedorSelecionado()
    {
        return tbviewFornecedor.getSelectionModel().getSelectedItem();
    }

    // Método público para poder excluir o produto pela classe fxpopupRemProduto
    public void excluirProduto(int idProduto)
    {
        sqlite.excluirProduto(idProduto);
        tbviewProdutos.setItems(sqlite.MostrarProdutos());
    }

    // Método público para poder excluir o produto pela classe fxpopupRemFornecedor
    public void excluirFornecedor(int idFornecedor)
    {
        sqlite.excluirFornecedor(idFornecedor);
        tbviewFornecedor.setItems(sqlite.MostrarFornecedores());
    }

    // Método público para poder excluir o cliente pela classe fxpopupRemCliente
    public void excluirCliente(String CPF)
    {
        sqlite.excluirCliente(CPF);
        tbviewCliente.setItems(sqlite.MostrarClientes());
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
