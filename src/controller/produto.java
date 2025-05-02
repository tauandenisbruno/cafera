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
