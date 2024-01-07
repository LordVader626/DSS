package src.SubPostos;

import java.util.ArrayList;
import java.util.List;


import src.DAOs.PostoTrabalho_DAO;
import src.DAOs.Servico_DAO;
import src.SubViaturas.Viatura;

public class SubPostosFacade implements ISubPostos{
    
    private PostoTrabalho_DAO postos = PostoTrabalho_DAO.getInstance();
    private Servico_DAO servicos = Servico_DAO.getInstance();

    public boolean verificaSePostosCheios(String esp){ // se i for 0, quer dizer que todos os postos com aquela especialização estão cheios
        
        int i = 0;
    for(PostoTrabalho p : this.postos.values()) {
        if (p.getEspecializacao().equals(esp)) {
            if (p.getServicos().size() < 12) i++;
            
        }
    }
        return i != 0;
    }

    public void agendaServico(int tipo,String descricao,Viatura viatura,boolean notif, PostoTrabalho posto){
        String id = String.valueOf( this.servicos.size());

        Servico s = new Servico(id, tipo, descricao, notif, viatura);
        this.servicos.put(id, s);
        this.servicos.setPosto(id,posto.getNome());

    }

    public PostoTrabalho getPostoDisponivel(String esp){

        PostoTrabalho pt = null;
    for(PostoTrabalho p : this.postos.values()) {
        if (p.getEspecializacao().equals(esp) && p.getServicos().size() < 12) {
            pt = new PostoTrabalho(p);
    }
    }
    return pt;
    }

    public List<String> getListaNomesPostos(){

        List<String> list = new ArrayList<String>(this.postos.keySet());

        return list;
    }

    public boolean validaMecanico(String nome,String password,String nomePosto){
        return this.postos.get(nomePosto).getMecanico().getNome().equals(nome) && 
        this.postos.get(nomePosto).getMecanico().getPassword().equals(password);
    }

    public PostoTrabalho getPostoTrabalho(String nomePosto) {
        return this.postos.get(nomePosto);
    }

    public List<Servico> getListaServicos(String nomePosto){
        return this.postos.get(nomePosto).getServicos();
    }

    public int nrServicosViatura( String matricula) {
        return this.servicos.nrServicosViatura(matricula);
     }

     public Servico getServicoARealizar(String nomePosto){
       List <Servico> s = this.postos.get(nomePosto).getServicos();
       if (s.size()!=0) return s.get(0);
        return null;
     }

     public void apagarServico( Servico servico){
        this.servicos.remove(servico.getID());
    }

    public List<Servico> realizarCheckup(Viatura viatura){
        List<Servico> listS = new ArrayList<>();
        if (!viatura.getPneus().equals("novo")) {
                                                            Servico aux = new Servico();
                                                            aux.setTipo(0); 
                                                            aux.setDescricao("substituir pneus"); 
                                                            aux.setViatura(viatura);
                                                            listS.add(aux);
                                                        }
        if (!viatura.getRodas().equals("novo")) {
                                                            Servico aux = new Servico();
                                                            aux.setTipo(0); 
                                                            aux.setDescricao("calibrar rodas"); 
                                                            aux.setViatura(viatura);
                                                            listS.add(aux);
                                                        }
        if (!viatura.getDirecao().equals("novo")) {
                                                            Servico aux = new Servico();
                                                            aux.setTipo(0);
                                                            aux.setDescricao("alinhar direcao"); 
                                                            aux.setViatura(viatura);
                                                            listS.add(aux);
                                                        }
        if (!viatura.getInjetores().equals("novo")) {
                                                            Servico aux = new Servico();
                                                            aux.setTipo(0); 
                                                            aux.setDescricao("substituir injetores"); 
                                                            aux.setViatura(viatura);
                                                            listS.add(aux);
                                                        }
        if (!viatura.getTravoes().equals("novo")) {
                                                            Servico aux = new Servico();
                                                            aux.setTipo(0); 
                                                            aux.setDescricao("substituir travoes"); 
                                                            aux.setViatura(viatura);
                                                            listS.add(aux);
                                                        }
        if (!viatura.getFiltroAr().equals("novo")) {
                                                            Servico aux = new Servico();
                                                            aux.setTipo(0); 
                                                            aux.setDescricao("substituir filtro ar"); 
                                                            aux.setViatura(viatura);
                                                            listS.add(aux);
                                                        }

    return listS;
    }


}
