package src.SubClientes;

public interface ISubClientes {

    public boolean validaCliente( String nome, String nif);

    public boolean verLigacaoViaturaCliente(String matricula, String nif);
}
