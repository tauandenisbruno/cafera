/*  Criado em 20 de abril de 2025
 *  Última edição em 08 de maio de 2025
 * 
 *  Código: Tauan
 *  Banco: Tauan, Larissa e Aisha
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Responsável pela conexão com o MySQL e os métodos reponsáveis pela captura e inserção de
 *  dados no banco.
 */

package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class sqlite
{
    private static String sql_local = "jdbc:sqlite:./src/banco.db";
    private static int sql_erro = 0;

    // Retorna o erro para futuro tratamento de exeções
    public static int getErro()
    {
        return sql_erro;
    }

    // Altera o codigo do erro
    public static void setErro(int codigo)
    {
        sql_erro = codigo;
    }

    // Mostra todos os dados da tabela PRODUTO
    public static ObservableList<produto> MostrarProdutos()
    {      
        ObservableList<produto> produtos = FXCollections.observableArrayList();
        String sql_query = """
                        SELECT 
                        p.ID_PRODUTO, 
                        p.NOME, 
                        p.PRECO, 
                        p.ESTOQUE, 
                        c.NOME AS CATEGORIA, 
                        f.NOME AS FORNECEDOR
                        FROM 
                        PRODUTO p
                        INNER JOIN CATEGORIA c ON p.ID_CATEGORIA = c.ID_CATEGORIA
                        INNER JOIN FORNECEDOR f ON p.ID_FORNECEDOR = f.ID_FORNECEDOR;
                         """;

       try
       (
        Connection conn = DriverManager.getConnection(sql_local);
        Statement select = conn.createStatement();
        ResultSet resultado = select.executeQuery(sql_query);
       )
           {
                while (resultado.next())
                {
                    produto prod = new produto
                    (
                        resultado.getInt("ID_PRODUTO"),
                        resultado.getString("NOME"),
                        resultado.getDouble("PRECO"),
                        resultado.getInt("ESTOQUE"),
                        resultado.getString("CATEGORIA"),
                        resultado.getString("FORNECEDOR")
                    );
                    produtos.add(prod);
                }
                conn.close();
                select.close();
                System.out.println("SQLite > Sucesso: Listar \"Produtos\"");
           }

        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }

        return produtos;
    }

    // Exclui um item da tabela PRODUTO
    public static void excluirProduto(int id)
    {
        try
        (
            Connection conn = DriverManager.getConnection(sql_local)
        )
        {
            try
            (
                Statement stmt = conn.createStatement()
            )
            {
                stmt.execute("PRAGMA foreign_keys = ON");
            }

            String sql_query = "DELETE FROM PRODUTO WHERE ID_PRODUTO = ?";

            try
            (
                PreparedStatement excluir = conn.prepareStatement(sql_query)
            )
            {
                excluir.setInt(1, id);
                excluir.executeUpdate();
                System.out.println("SQLite > Sucesso (Remover produto ID: " + id + ")");
            }
        }
        catch (SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }
    }

    // Retorna as IDs dos fornecedores e das categorias
    public static int obterId(Connection conn, String sql_query, String nome) throws SQLException
    {
        try
        (
            PreparedStatement getID = conn.prepareStatement(sql_query)
        )
        {
            getID.setString(1, nome);
            try
            (
                ResultSet resultado = getID.executeQuery()
            )
            {
                if (resultado.next())
                {
                    return resultado.getInt(1);
                }
                else
                {
                    throw new SQLException("Registro não encontrado");
                }
            }
        }
    }

    // Adiciona um item da tabela PRODUTO
    public static void adicionarProduto(int id, String nome, Double preco, int estoque, String categoria, String fornecedor)
    {
        try
        (
            Connection conn = DriverManager.getConnection(sql_local)
        )
        {
            // Obter os IDs da categoria e do fornecedor
            int idCategoria = obterId(conn, "SELECT ID_CATEGORIA FROM CATEGORIA WHERE NOME = ?", categoria);
            int idFornecedor = obterId(conn, "SELECT ID_FORNECEDOR FROM FORNECEDOR WHERE NOME = ?", fornecedor);

            // Cadastrar o produto
            String sql_query = "INSERT INTO PRODUTO (ID_PRODUTO, NOME, PRECO, ESTOQUE, ID_CATEGORIA, ID_FORNECEDOR) VALUES (?, ?, ?, ?, ?, ?)";
            try
            (
                PreparedStatement cadastrar = conn.prepareStatement(sql_query)
            )
            {
                cadastrar.setInt(1, id);
                cadastrar.setString(2, nome);
                cadastrar.setDouble(3, preco);
                cadastrar.setInt(4, estoque);
                cadastrar.setInt(5, idCategoria);
                cadastrar.setInt(6, idFornecedor);
                cadastrar.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }
    }

    // Obtém a lista de CATEGORIAS disponível no banco
    public static List<String> getCategorias()
    {
        List<String> categorias = new ArrayList<>();
        String sql_query = "SELECT NOME FROM CATEGORIA";

        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement getCategorias = conn.prepareStatement(sql_query);
            ResultSet resultado = getCategorias.executeQuery()
        )
            {
                while (resultado.next())
                {
                    categorias.add(resultado.getString("NOME"));
                }
            }
        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }

        return categorias;
    }

    // Obtém a lista de PAGAMENTOS disponível no banco
    public static List<String> getFormaPagamento()
    {
        List<String> pagamentos = new ArrayList<>();
        String sql_query = "SELECT TIPO FROM PAGAMENTO";

        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement getPagamentos = conn.prepareStatement(sql_query);
            ResultSet resultado = getPagamentos.executeQuery()
        )
            {
                while (resultado.next())
                {
                    pagamentos.add(resultado.getString("TIPO"));
                }
            }
        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }

        return pagamentos;
    }

    // Obtém a lista de FORNECEDORES disponível no banco
    public static List<String> getFornecedores()
    {
        List<String> fornecedor = new ArrayList<>();
        String sql_query = "SELECT NOME FROM FORNECEDOR";

        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement getFornecedor = conn.prepareStatement(sql_query);
            ResultSet resultado = getFornecedor.executeQuery()
        )
            {
                while (resultado.next())
                {
                    fornecedor.add(resultado.getString("NOME"));
                }
            }
        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }

        return fornecedor;
    }

    // Edita um item da tabela produto
    public static void editarProduto(int id, String nome, double preco, int estoque, String categoria, String fornecedor)
    {
        try
        (
            Connection conn = DriverManager.getConnection(sql_local)
        )
        {
            // Obter os IDs da categoria e do fornecedor
            int idCategoria = obterId(conn, "SELECT ID_CATEGORIA FROM CATEGORIA WHERE NOME = ?", categoria);
            int idFornecedor = obterId(conn, "SELECT ID_FORNECEDOR FROM FORNECEDOR WHERE NOME = ?", fornecedor);
            String sql_query = """
                    UPDATE PRODUTO 
                    SET NOME = ?, PRECO = ?, ESTOQUE = ?, ID_CATEGORIA = ?, ID_FORNECEDOR = ? 
                    WHERE ID_PRODUTO = ?
                    """;
            try
            (
                PreparedStatement update = conn.prepareStatement(sql_query)
            )
            {

                update.setString(1, nome);
                update.setDouble(2, preco);
                update.setInt(3, estoque);
                update.setInt(4, idCategoria);
                update.setInt(5, idFornecedor);
                update.setInt(6, id);

                update.executeUpdate();
                System.out.println("SQLite > Sucesso: Editar");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQLite > Erro: " + e.getMessage());
            sql_erro = e.getErrorCode();
        }
    }

    // Mostra todos os dados da tabela CLIENTE
    public static ObservableList<cliente> MostrarClientes()
    {      
        ObservableList<cliente> clientes = FXCollections.observableArrayList();
        String sql_query = "SELECT * FROM CLIENTE";

       try
       (
        Connection conn = DriverManager.getConnection(sql_local);
        Statement select = conn.createStatement();
        ResultSet resultado = select.executeQuery(sql_query);
       )
           {
                while (resultado.next())
                {
                    cliente cli = new cliente
                    (
                        resultado.getString("NOME"),
                        resultado.getString("CPF"),
                        resultado.getString("EMAIL")
                    );
                    clientes.add(cli);
                }
                conn.close();
                select.close();
                System.out.println("SQLite > Sucesso: Listar \"Clientes\"");
           }

        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }

        return clientes;
    }

    // Mostra todos os dados da tabela PEDIDOS
    public static ObservableList<pedido> MostrarPedidos()
    {      
        ObservableList<pedido> pedidos = FXCollections.observableArrayList();
        String sql_query =  """
                            SELECT 
                                P.ID_PEDIDO, 
                                C.NOME AS NOME_CLIENTE, 
                                P.DATA_PEDIDO, 
                                PR.NOME AS NOME_PRODUTO, 
                                PG.TIPO AS TIPO_PAGAMENTO,
                                PR.PRECO AS PRECO_UNITARIO,
                                IP.QUANTIDADE,
                                PR.PRECO * IP.QUANTIDADE AS VLR_TOTAL
                                
                            FROM PEDIDO P 
                            INNER JOIN CLIENTE C ON P.CPF_CLIENTE = C.CPF 
                            INNER JOIN PAGAMENTO PG ON P.ID_PAGAMENTO = PG.ID_PAGAMENTO 
                            INNER JOIN ITEM_PEDIDO IP ON P.ID_PEDIDO = IP.ID_PEDIDO 
                            INNER JOIN PRODUTO PR ON IP.ID_PRODUTO = PR.ID_PRODUTO 
                            INNER JOIN CATEGORIA CAT ON PR.ID_CATEGORIA = CAT.ID_CATEGORIA 
                            INNER JOIN FORNECEDOR F ON PR.ID_FORNECEDOR = F.ID_FORNECEDOR;
                            """;

       try
       (
        Connection conn = DriverManager.getConnection(sql_local);
        Statement select = conn.createStatement();
        ResultSet resultado = select.executeQuery(sql_query);
       )
           {
                while (resultado.next())
                {
                    pedido ped = new pedido
                    (
                        resultado.getInt("ID_PEDIDO"),
                        resultado.getString("NOME_CLIENTE"),
                        resultado.getString("DATA_PEDIDO"),
                        resultado.getString("NOME_PRODUTO"),
                        resultado.getString("TIPO_PAGAMENTO"),
                        resultado.getDouble("PRECO_UNITARIO"),
                        resultado.getInt("QUANTIDADE"),
                        resultado.getDouble("VLR_TOTAL")
                    );
                    pedidos.add(ped);
                }
                conn.close();
                select.close();
                System.out.println("SQLite > Sucesso: Listar \"Pedidos\"");
           }

        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }

        return pedidos;
    }

    // Mostra todos os dados da tabela FORNECEDOR
    public static ObservableList<fornecedor> MostrarFornecedores()
    {      
        ObservableList<fornecedor> fornecedores = FXCollections.observableArrayList();
        String sql_query =  "SELECT * FROM FORNECEDOR";

       try
       (
        Connection conn = DriverManager.getConnection(sql_local);
        Statement select = conn.createStatement();
        ResultSet resultado = select.executeQuery(sql_query);
       )
           {
                while (resultado.next())
                {
                    fornecedor forn = new fornecedor
                    (
                        resultado.getInt("ID_FORNECEDOR"),
                        resultado.getString("NOME"),
                        resultado.getString("CONTATO")
                    );
                    fornecedores.add(forn);
                }
                conn.close();
                select.close();
                System.out.println("SQLite > Sucesso: Listar \"Fornecedor\"");
           }

        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }

        return fornecedores;
    }

    // Adiciona um item da tabela CLIENTE
    public static void adicionarCliente(String nome, String cpf, String email)
    {
        String sql_query = "INSERT INTO CLIENTE (NOME, CPF, EMAIL) VALUES (?, ?, ?)";
        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement cadastrar = conn.prepareStatement(sql_query);
        )
        {
            cadastrar.setString(1, nome);
            cadastrar.setString(2, cpf);
            cadastrar.setString(3, email);

            cadastrar.executeUpdate();
            System.out.println("SQLite > Sucesso: Adicionar \"Cliente\"");
        }
        catch (SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }
    }

    // Exclui um item da tabela CLIENTE
    public static void excluirCliente(String cpf)
    {
        try
        (
            Connection conn = DriverManager.getConnection(sql_local)
        )
        {
            try
            (
                Statement pragma = conn.createStatement()
            )
            {
                pragma.execute("PRAGMA foreign_keys = ON");
            }

            String sql_query = "DELETE FROM CLIENTE WHERE CPF = ?";

            try
            (
                PreparedStatement excluir = conn.prepareStatement(sql_query)
            )
            {
                excluir.setString(1, cpf);
                excluir.executeUpdate();
                System.out.println("SQLite > Sucesso (Remover cliente CPF: " + cpf + ")");
            }
        }
        catch (SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }
    }

    // Edita um item da tabela CLIENTE
    public static void editarCliente(String nome, String CPF, String email)
    {
        String sql_query = "UPDATE CLIENTE SET NOME = ?, CPF = ?, EMAIL = ? WHERE CPF = ?";
        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement update = conn.prepareStatement(sql_query);
        )
        {
            update.setString(1, nome);
            update.setString(2, CPF);
            update.setString(3, email);
            update.setString(4, CPF);

            update.executeUpdate();
            System.out.println("SQLite > Sucesso: Editar \"Cliente\"");
        }
        catch (SQLException e)
        {
            System.out.println("SQLite > Erro: " + e.getMessage());
            sql_erro = e.getErrorCode();
        }
    }

      // Adiciona um item da tabela FORNECEDOR
    public static void adicionarFornecedor(String nome, String email)
    {
        String sql_query = "INSERT INTO FORNECEDOR (NOME, CONTATO) VALUES (?, ?)";
        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement cadastrar = conn.prepareStatement(sql_query);
        )
        {
            cadastrar.setString(1, nome);
            cadastrar.setString(2, email);

            cadastrar.executeUpdate();
            System.out.println("SQLite > Sucesso: Adicionar \"Fornecedor\"");
        }
        catch (SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }
    }

    // Edita um item da tabela FORNECEDOR
    public static void editarFornecedor(int id, String nome, String email)
    {
        String sql_query = "UPDATE FORNECEDOR SET ID_FORNECEDOR = ?, NOME = ?, CONTATO = ? WHERE ID_FORNECEDOR = ?";
        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement update = conn.prepareStatement(sql_query);
        )
        {
            update.setInt(1, id);
            update.setString(2, nome);
            update.setString(3, email);
            update.setInt(4, id);

            update.executeUpdate();
            System.out.println("SQLite > Sucesso: Editar \"Fornecedor\"");
        }
        catch (SQLException e)
        {
            System.out.println("SQLite > Erro: " + e.getMessage());
            sql_erro = e.getErrorCode();
        }
    }

    // Exclui um item da tabela FORNECEDOR
    public static void excluirFornecedor(int id)
    {
        try
        (
            Connection conn = DriverManager.getConnection(sql_local)
        )
        {
            try
            (
                Statement pragma = conn.createStatement()
            )
            {
                pragma.execute("PRAGMA foreign_keys = ON");
            }

            String sql_query = "DELETE FROM FORNECEDOR WHERE ID_FORNECEDOR = ?";

            try
            (
                PreparedStatement excluir = conn.prepareStatement(sql_query)
            )
            {
                excluir.setInt(1, id);
                excluir.executeUpdate();
                System.out.println("SQLite > Sucesso (Remover fornecedor ID: " + id + ")");
            }
        }
        catch (SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }
    }

    // ==================================================================================
    // Obtém a lista de PAGAMENTOS disponível no banco
    public static List<String> getPagamentos()
    {
        List<String> pagamentos = new ArrayList<>();
        String sql_query = "SELECT TIPO FROM PAGAMENTO";

        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement getPagamentos = conn.prepareStatement(sql_query);
            ResultSet resultado = getPagamentos.executeQuery()
        )
            {
                while (resultado.next())
                {
                    pagamentos.add(resultado.getString("TIPO"));
                }
            }
        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("ERRO: " + e.getMessage() + "\nCAUSA: " + e.getCause() + "\nCOD: " + e.getErrorCode());
        }

        return pagamentos;
    }

    // Obtém o CPF de um cliente
    private static String getCpfCliente(Connection conn, String nomeCliente) throws SQLException {
    String sql = "SELECT CPF FROM CLIENTE WHERE NOME = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, nomeCliente);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("CPF");
            } else {
                throw new SQLException("Cliente não encontrado: " + nomeCliente);
            }
        }
    }
}

    // Adiciona um pedido
    public static void adicionarPedido(String cliente, String produto, String pagamento, LocalDate data, int quantidade)
    {
        String sqlPedido = "INSERT INTO PEDIDO (CPF_CLIENTE, DATA_PEDIDO, ID_PAGAMENTO) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO ITEM_PEDIDO (ID_PEDIDO, ID_PRODUTO, QUANTIDADE) VALUES (?, ?, ?)";

        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement psPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psItem = conn.prepareStatement(sqlItem)
        )
        {
            int idPagamento = obterId(conn, "SELECT ID_PAGAMENTO FROM PAGAMENTO WHERE TIPO = ?", pagamento);
            String cpfCliente = getCpfCliente(conn, cliente); // Novo método auxiliar para obter CPF do cliente
            int idProduto = obterId(conn, "SELECT ID_PRODUTO FROM PRODUTO WHERE NOME = ?", produto);

            psPedido.setString(1, cpfCliente);
            psPedido.setString(2, data.toString());
            psPedido.setInt(3, idPagamento);

            psPedido.executeUpdate();

            ResultSet rs = psPedido.getGeneratedKeys();
            if (rs.next())
            {
                int idPedidoGerado = rs.getInt(1);
                {
                    psItem.setInt(1, idPedidoGerado);
                    psItem.setInt(2, idProduto);
                    psItem.setInt(3, quantidade);
                    psItem.executeUpdate();
                }
            }
        }
        catch (SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("ERRO: " + e.getMessage() + "\nCAUSA: " + e.getCause() + "\nCOD: " + e.getErrorCode());
        }
    }


    // Edita um item da tabela pedido
    public static void editarPedido(int idPedido, String cliente, String produto, String pagamento, LocalDate data, int quantidade)
    {
        String sqlUpdatePedido = "UPDATE PEDIDO SET CPF_CLIENTE = ?, DATA_PEDIDO = ?, ID_PAGAMENTO = ? WHERE ID_PEDIDO = ?";
        String sqlUpdateItem = "UPDATE ITEM_PEDIDO SET ID_PRODUTO = ?, QUANTIDADE = ? WHERE ID_PEDIDO = ?";

        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement psPedido = conn.prepareStatement(sqlUpdatePedido);
            PreparedStatement psItem = conn.prepareStatement(sqlUpdateItem)
        )
        {
            int idPagamento = obterId(conn, "SELECT ID_PAGAMENTO FROM PAGAMENTO WHERE TIPO = ?", pagamento);
            int idProduto = obterId(conn, "SELECT ID_PRODUTO FROM PRODUTO WHERE NOME = ?", produto);
            String cpfCliente = getCpfCliente(conn, cliente); // Usa o CPF como referência, não ID

            // Atualizar PEDIDO
            psPedido.setString(1, cpfCliente);
            psPedido.setString(2, data.toString());
            psPedido.setInt(3, idPagamento);
            psPedido.setInt(4, idPedido);
            psPedido.executeUpdate();

            // Atualizar ITEM_PEDIDO
            psItem.setInt(1, idProduto);
            psItem.setInt(2, quantidade);
            psItem.setInt(3, idPedido);
            psItem.executeUpdate();
            
            System.out.println("SQLite > Sucesso: Pedido editado (ID_PEDIDO: " + idPedido + ")");
        }
        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }
    }

    // Exclui um item da tabela pedido
    public static void excluirPedido(int idPedido)
    {
        try
        (
            Connection conn = DriverManager.getConnection(sql_local)
        )
        {
            conn.createStatement().execute("PRAGMA foreign_keys = ON");

            // Deleta na item_pedido
            String sqlDeleteItens = "DELETE FROM ITEM_PEDIDO WHERE ID_PEDIDO = ?";

            try
            (
                PreparedStatement psDeleteItens = conn.prepareStatement(sqlDeleteItens)
            )
            {
                psDeleteItens.setInt(1, idPedido);
                psDeleteItens.executeUpdate();
            }

            // Deleta o pedido
            String sqlDeletePedido = "DELETE FROM PEDIDO WHERE ID_PEDIDO = ?";
            try
            (
                PreparedStatement psDeletePedido = conn.prepareStatement(sqlDeletePedido)
            )
            {
                psDeletePedido.setInt(1, idPedido);
                int afetados = psDeletePedido.executeUpdate();

                if (afetados > 0)
                {
                    System.out.println("SQLite > Sucesso (Remover pedido ID: " + idPedido + ")");
                }
                else
                {
                    System.out.println("SQLite > Nenhum pedido encontrado com ID: " + idPedido);
                }
            }

        } catch (SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("SQLite > Erro: " + e.getMessage());
        }
    }

    // Obtém a lista de CLIENTES disponível no banco
    public static List<String> getClientes()
    {
        List<String> clientes = new ArrayList<>();
        String sql_query = "SELECT NOME FROM CLIENTE";

        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement getClientes = conn.prepareStatement(sql_query);
            ResultSet resultado = getClientes.executeQuery()
        )
            {
                while (resultado.next())
                {
                    clientes.add(resultado.getString("NOME"));
                }
            }
        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("ERRO: " + e.getMessage() + "\nCAUSA: " + e.getCause() + "\nCOD: " + e.getErrorCode());
        }

        return clientes;
    }

    // Obtém a lista de PRODUTOS disponível no banco
    public static List<String> getProdutos()
    {
        List<String> produtos = new ArrayList<>();
        String sql_query = "SELECT NOME FROM PRODUTO";

        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            PreparedStatement getProdutos = conn.prepareStatement(sql_query);
            ResultSet resultado = getProdutos.executeQuery()
        )
            {
                while (resultado.next())
                {
                    produtos.add(resultado.getString("NOME"));
                }
            }
        catch(SQLException e)
        {
            sql_erro = e.getErrorCode();
            System.out.println("ERRO: " + e.getMessage() + "\nCAUSA: " + e.getCause() + "\nCOD: " + e.getErrorCode());
        }

        return produtos;
    }

    // ==================================================================================
}
