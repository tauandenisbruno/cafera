/*  Criado em 20 de abril de 2025
 *  Última edição em 02 de maio de 2025
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
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class sqlite
{
    private static String sql_local = "jdbc:sqlite:./src/banco.db";
    
    // Para mostrar todos os usuários no console para fins de testes
    public static void MostrarUsuarios()
    {
        String sql_query = "SELECT * FROM usuario";

       try
       (
        Connection conn = DriverManager.getConnection(sql_local);
        Statement select = conn.createStatement();
        ResultSet resultado = select.executeQuery(sql_query);
       )
           {
                while (resultado.next())
                {
                    String nome = resultado.getString("nome");
                    int senha = resultado.getInt("senha");

                    System.out.println("NOME: " + nome + " SENHA: " + senha);
                }
                conn.close();
                select.close();
                System.out.println("Sucesso");
           }

        catch(SQLException e)
        {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    // Mostra todos os dados na tabela
    public static ObservableList<produto> MostrarProdutos()
    {      
        ObservableList<produto> produtos = FXCollections.observableArrayList();
        String sql_query = "SELECT * FROM produto";

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
                        resultado.getInt("ID"),
                        resultado.getString("NOME"),
                        resultado.getDouble("PRECO"),
                        resultado.getInt("ESTOQUE")
                    );
                    produtos.add(prod);
                }
                conn.close();
                select.close();
                System.out.println("SQLite > Sucesso (Atualizar)");
           }

        catch(SQLException e)
        {
            System.out.println("SQLite > Erro: " + e.getMessage());
        }

        return produtos;
    }

    // Exclui um item da tabela PRODUTO
    public static void excluirProduto(int id)
    {
        String sql_query = "DELETE FROM produto WHERE id = ?";

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
            System.out.println("SQLite > Erro (" + e.getMessage() + ")");
        }
    }
}
