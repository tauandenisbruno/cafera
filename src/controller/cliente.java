/*  Criado em 22 de maio de 2025
 *  Última edição em 22 de maio de 2025
 * 
 *  Código: Tauan
 *  Linux Mint 22.1 - Vscodium 1.98.2
 * 
 *  Responsável pelo instânciamento realizado automaticamente pela classe "sqlite" para cada novo cliente
 *  que ele retornar do banco de dados e assim ser chamado e inserido na tabela para ser exibido ao usuário.
 */

package controller;

public class cliente
{
    private String nome, cpf, email;

    public cliente (String nome, String cpf, String email)
    {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getClienteNome()
    {
        return nome;
    }

    public String getClienteCPF()
    {
        return cpf;
    }

    public String getClienteEmail()
    {
        return email;
    }
}
