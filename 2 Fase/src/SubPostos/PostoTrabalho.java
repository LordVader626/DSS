package src.SubPostos;
import java.util.ArrayList;
import java.util.List;

public class PostoTrabalho {

    private String nome;
    private String especializacao;
    private Mecanico mecanico;
    private List<Servico> servicos;
    

    public PostoTrabalho() {
        this.nome = "";
        this.especializacao = "";
        this.mecanico = new Mecanico();
        this.servicos = new ArrayList<>();
    }


    public PostoTrabalho(String nome, String esp, Mecanico mecanico, List<Servico> servicos) {
        
        this.nome = nome;
        this.especializacao = esp;
        this.mecanico = mecanico;
        this.servicos = servicos;

    }

    public PostoTrabalho(PostoTrabalho postoTrabalho) {
        this.nome = postoTrabalho.getNome();
        this.especializacao = postoTrabalho.getEspecializacao();
        this.mecanico = postoTrabalho.getMecanico();
        this.servicos = postoTrabalho.getServicos();
    }

    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEspecializacao(){
        return this.especializacao;
    }
    public void setEspecializacao(String esp){
        this.especializacao = esp;
    }

    public Mecanico getMecanico(){
        return this.mecanico;
    }
    public void setMecanico(Mecanico mecanico){
        this.mecanico = mecanico;
    }

    public List<Servico> getServicos(){
        return this.servicos;
    }
    public void setServicos(List<Servico> servicos){
        this.servicos = servicos;
    }


    public PostoTrabalho clone() {
    return new PostoTrabalho(this);
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        PostoTrabalho pt = (PostoTrabalho) o;
        return this.nome.equals(pt.getNome())
               && this.especializacao.equals(pt.getEspecializacao())
               && this.mecanico.equals(pt.getMecanico())
               && this.servicos.equals(pt.getServicos());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Especialização: ").append(this.especializacao).append("\n");
        sb.append("Mecânico-> ").append("\n").append(this.mecanico).append("\n");
        

        return sb.toString();
    }
}
