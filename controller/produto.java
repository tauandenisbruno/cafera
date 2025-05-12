/*  Criado em 01 de maio de 2025
 *  Última edição em 02 de maio de 2025
 * 
 *  Código: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Responsável pelo instânciamento realizado automaticamente pela classe "sqlite" para cada novo produto
 *  que ele retornar do banco de dados e assim ser chamado e inserido na tabela para ser exibido ao usuário.
 */

package controller;

public class produto
{
    private int id, estoque;
    private String nome;
    private double preco;

    public produto(int id, String nome, double preco, int estoque)
    {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public int getProdutoID()
    {
        return id;
    }

    public String getProdutoNome()
    {
        return nome;
    }

    public double getProdutoPreco()
    {
        return preco;
    }

    public int getProdutoEstoque()
    {
        return estoque;
    }
}
