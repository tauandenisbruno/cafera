/*  Criado em 20 de abril de 2025
 *  Última edição em 30 de abril de 2025
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
import java.sql.SQLException;

public class sqlite
{
    private static String sql_local = "jdbc:sqlite:./src/banco.db";
    private static String sql_query = "SELECT * FROM produto";

    // Para mostrar todos os dados no console para fins de testes
    static void MostrarTudo()
    {
       try
       (
        Connection conn = DriverManager.getConnection(sql_local);
        Statement select = conn.createStatement();
        ResultSet resultado = select.executeQuery(sql_query);
       )
           {
                while (resultado.next())
                {
                    int ID = resultado.getInt("ID");
                    String NOME = resultado.getString("NOME");
                    double PRECO = resultado.getDouble("PRECO");
                    int EST = resultado.getInt("ESTOQUE");


                    System.out.println("id: " + ID + " NOME: " + NOME + " PREÇO: " + PRECO + " ESTOQUE: " + EST);
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
}
