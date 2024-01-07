package src;

import java.util.List;

import src.SubClientes.*;
import src.SubPostos.*;
import src.SubViaturas.*;

public class ES_IdealFacade implements IES_Ideal{
    //Juntar todas as interfaces dos subsistemas e por todas as funcoes aqui
    
    private ISubPostos postos;
	private ISubViaturas viaturas;
	private ISubClientes clientes;

    public ES_IdealFacade(){
		this.postos = new SubPostosFacade();
		this.viaturas = new SubViaturasFacade();
		this.clientes = new SubClientesFacade();
	}
    
    //  Clientes
    public boolean validaCliente( String nome, String nif){
        return this.clientes.validaCliente(nome,nif);
    }
    public boolean verificaLigacaoViaturaCliente(String matricula, String nif){
       return this.clientes.verLigacaoViaturaCliente(matricula,nif);
    }

    // Viaturas
    public boolean validaViatura(String matricula){
        return this.viaturas.validaViatura(matricula);
    }
    public int getTipoMotor(String matricula){
        return this.viaturas.getTipoMotor(matricula);
    }
    public Viatura getViatura(String matricula){
        return this.viaturas.getViatura(matricula);
    }
    public void atualizaFichaVeiculo(String estado, Viatura v , int nserv){
        this.viaturas.atualizaFichaVeiculo(estado, v , nserv);
    }
    public void realizarServico(Servico servico){ 
        this.viaturas.realizarServico(servico.getTipo(),servico.getDescricao(),servico.getViatura());

    }

    // Postos
    
    public boolean verificaSePostosCheios(String esp){
        return this.postos.verificaSePostosCheios(esp);
    }
    public void agendaServico(int tipo,String descricao,Viatura viatura,boolean notif, PostoTrabalho posto){

        this.postos.agendaServico(tipo,descricao,viatura,notif,posto);
    }
    public List<String> getListaNomesPostos(){
        return this.postos.getListaNomesPostos();
    }
    public boolean validaMecanico(String nome,String password,String nomePosto){
        return this.postos.validaMecanico(nome,password,nomePosto);
    }
    public PostoTrabalho getPostoTrabalho(String nomePosto) {
        return this.postos.getPostoTrabalho(nomePosto);
    }
    public PostoTrabalho getPostoDisponivel(String esp){
        return this.postos.getPostoDisponivel(esp);
    }
    public List<Servico> getListaServicos(String nomePosto){
        return this.postos.getListaServicos(nomePosto);
    }

    public int nrServicosViatura( String matricula) {
       return this.postos.nrServicosViatura(matricula);
    }

    public Servico getServicoARealizar(String nomePosto){
       return this.postos.getServicoARealizar(nomePosto);
    }

    public void apagarServico( Servico servico){
        this.postos.apagarServico(servico);
    }

    public List<Servico> realizarCheckup(Viatura viatura){
        return this.postos.realizarCheckup(viatura);
    }
    
}
