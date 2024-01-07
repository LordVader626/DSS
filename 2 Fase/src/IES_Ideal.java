package src;

import java.util.List;

import src.SubPostos.*;
import src.SubViaturas.*;


public interface IES_Ideal {


    //  Clientes

    public boolean validaCliente( String nome, String nif);

    public boolean verificaLigacaoViaturaCliente(String matricula, String nif);

    // Viaturas

    public boolean validaViatura(String matricula);

    public int getTipoMotor(String matricula);

    public Viatura getViatura(String matricula);

    public void atualizaFichaVeiculo(String estado, Viatura v , int nserv);

    public void realizarServico(Servico servico);

    public List<Servico> realizarCheckup(Viatura viatura);


    // Postos
    
    public boolean verificaSePostosCheios(String esp);

    public void agendaServico(int tipo,String descricao,Viatura viatura,boolean notif, PostoTrabalho posto);

    public List<String> getListaNomesPostos();

    public boolean validaMecanico(String nome,String password,String nomePosto);

    public PostoTrabalho getPostoTrabalho(String nomePosto) ;

    public PostoTrabalho getPostoDisponivel(String esp);

    public List<Servico> getListaServicos(String nomePosto);

    public int nrServicosViatura( String matricula) ;

    public Servico getServicoARealizar(String nomePosto);

    public void apagarServico( Servico servico);
    
}


