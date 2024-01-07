package src.SubPostos;

import java.util.List;

import src.SubViaturas.Viatura;

public interface ISubPostos {
    
    public boolean verificaSePostosCheios(String esp);

    public void agendaServico(int tipo,String descricao,Viatura viatura,boolean notif, PostoTrabalho posto);

    public PostoTrabalho getPostoDisponivel(String esp);

    public List<String> getListaNomesPostos();

    public boolean validaMecanico(String nome,String password,String nomePosto);

    public PostoTrabalho getPostoTrabalho(String nomePosto);

    public List<Servico> getListaServicos(String nomePosto);

    public int nrServicosViatura( String matricula);

    public Servico getServicoARealizar(String nomePosto);

    public void apagarServico( Servico servico);

    public List<Servico> realizarCheckup(Viatura viatura);
}
