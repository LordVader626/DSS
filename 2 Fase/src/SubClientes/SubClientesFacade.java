package src.SubClientes;

import src.DAOs.Cliente_DAO;

public class SubClientesFacade implements ISubClientes{

    private Cliente_DAO clientes = Cliente_DAO.getInstance();


    public boolean validaCliente( String nome, String nif){
        return this.clientes.containsKey(nif) && this.clientes.get(nif).getNome().equals(nome);
    }
    public boolean verLigacaoViaturaCliente(String matricula, String nif) {
        return this.clientes.get(nif).getViatura().getMatricula().equals(matricula);
    }
}
