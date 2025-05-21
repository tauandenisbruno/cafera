/*  Criado em 20 de abril de 2025
 *  Última edição em 08 de maio de 2025
 * 
 *  Código: Tauan
 *  Banco: Tauan, Larissa e Aisha
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Responsável pela conexão com o MySQL e os métodos reponsáveis pela captura e inserção de
 * dados no banco.
 */

package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
    
    // Para mostrar todos os usuários no console para fins de testes
    public static void MostrarUsuarios()
    {
        String sql_query = "SELECT * FROM FUNCIONARIO";

        try
        (
            Connection conn = DriverManager.getConnection(sql_local);
            Statement select = conn.createStatement();
            ResultSet resultado = select.executeQuery(sql_query);
        )
            {
                    while (resultado.next())
                    {
                        String nome = resultado.getString("NOME");
                        int senha = resultado.getInt("SENHA");

                        System.out.println("NOME: " + nome + " SENHA: " + senha);
                    }
                    conn.close();
                    select.close();
                    System.out.println("Sucesso");
            }

            catch(SQLException e)
            {
                sql_erro = e.getErrorCode();
                System.out.println("SQLite > Erro: " + e.getMessage());
            }
    }

    // Mostra todos os dados na tabela
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
                System.out.println("SQLite > Sucesso: Listar");
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
        String sql_query = "DELETE FROM PRODUTO WHERE id = ?";

       try
       (
        Connection conn = DriverManager.getConnection(sql_local);
        PreparedStatement excluir = conn.prepareStatement(sql_query);
       )
           {
                excluir.setInt(1, id);
                excluir.executeUpdate();
                System.out.println("SQLite > Sucesso (Remover produto ID: " + id + ")");
           }

        catch(SQLException e)
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
            System.out.println("ERRO: " + e.getMessage() + "\nCAUSA: " + e.getCause() + "\nCOD: " + e.getErrorCode());
        }
    }

    // Obtém a lista de categorias disponível no banco
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
            System.out.println("ERRO: " + e.getMessage() + "\nCAUSA: " + e.getCause() + "\nCOD: " + e.getErrorCode());
        }

        return categorias;
    }

    // Obtém a lista de categorias disponível no banco
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
            System.out.println("ERRO: " + e.getMessage() + "\nCAUSA: " + e.getCause() + "\nCOD: " + e.getErrorCode());
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
            System.out.println("ERRO: " + e.getMessage() + "\nCOD: " + e.getErrorCode() + "\nCAUSE: " + e.getCause());
            sql_erro = e.getErrorCode();
        }
    }
}
