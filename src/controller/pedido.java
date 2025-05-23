/*  Criado em 22 de maio de 2025
 *  Última edição em 22 de maio de 2025
 * 
 *  Código: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Responsável pelo instânciamento realizado automaticamente pela classe "sqlite" para cada novo pedido
 *  que ele retornar do banco de dados e assim ser chamado e inserido na tabela para ser exibido ao usuário.
 */

package controller;

public class pedido 
{
    private int id, quantidade;
    private String cliente, produto, pagamento, data;
    private Double preco, total;

    public pedido(int id, String cliente, String data, String produto, String pagamento, Double preco, int quantidade, Double total)
    {
        this.id = id;
        this.cliente = cliente;
        this.data = data;
        this.produto = produto;
        this.pagamento = pagamento;
        this. preco = preco;
        this.quantidade = quantidade;
        this.total = total;
    }

    public int getPedidoId()
    {
        return id;
    }

    public String getPedidoNomeCliente()
    {
        return cliente;
    }

    public String getPedidoData()
    {
        return data;
    }

    public String getPedidoNomeProduto()
    {
        return produto;
    }

    public String getPedidoPagamento()
    {
        return pagamento;
    }

    public Double getPedidoPrecoUnitario()
    {
        return preco;
    }

    public int getPedidoUnidades()
    {
        return quantidade;
    }

    public Double getPedidoPrecoTotal()
    {
        return total;
    }
}
