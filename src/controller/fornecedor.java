package controller;

public class fornecedor
{
    private int id;
    private String nome, contato;

    public fornecedor(int id, String nome, String contato)
    {
        this.id = id;
        this.nome = nome;
        this.contato = contato;
    }

    public int getFornecedorID()
    {
        return id;
    }

    public String getFornecedorNome()
    {
        return nome;
    }

    public String getFornecedorContato()
    {
        return contato;
    }
}
