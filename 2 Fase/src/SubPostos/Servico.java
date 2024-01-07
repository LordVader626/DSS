
package src.SubPostos;
import src.SubViaturas.Viatura;

public class Servico {

    private String id;
    private int tipo; 
    private String descricao;
    boolean notificacao;
    private Viatura viatura;

    public Servico() {
        this.id = "";
        this.tipo = 0;
        this.descricao = "";
        this.notificacao = false;
        this.viatura = new Viatura();
    }


    public Servico(String id, int tipo, String descricao, boolean notificacao, Viatura viatura) {
        
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.notificacao = notificacao;
        this.viatura = viatura;

    }

    public Servico(Servico servico) {
        this.id = servico.getID();
        this.tipo = servico.getTipo();
        this.descricao = servico.getDescricao();
        this.notificacao = servico.getNotificacao();
        this.viatura = servico.getViatura();
    }

    public String getID(){
        return this.id;
    }
    public void setID(String id){
        this.id = id;
    }

    public int getTipo(){
        return this.tipo;
    }
    public void setTipo(int tipo){
        this.tipo = tipo;
    }

    public String getDescricao(){
        return this.descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public boolean getNotificacao(){
        return this.notificacao;
    }
    public void setNotificacao(boolean notif){
        this.notificacao = notif;
    }

    public Viatura getViatura(){
        return this.viatura;
    }
    public void setViatura(Viatura viatura){
        this.viatura = viatura;
    }


    public Servico clone() {
    return new Servico(this);
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return this.id.equals(servico.getID())
               && this.tipo == servico.getTipo()
               && this.descricao.equals(servico.getDescricao())
               && this.viatura.equals(servico.getViatura());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("ID: ").append(this.id).append("\n");
        String tipoS = null;
            if(this.tipo == 0) tipoS = "universal"; 
            else if(this.tipo == 1) tipoS = "combustão"; 
            else if (this.tipo == 2) tipoS = "combustão diesel";
            else if (this.tipo == 3) tipoS = "combustão gasolina";
            else if (this.tipo == 4) tipoS = "elétrico";
            else if (this.tipo == 5) tipoS = "check-up";
        sb.append("Tipo: ").append(tipoS).append("\n");
        sb.append("Descrição: ").append(this.descricao).append("\n");
        sb.append("Notificação: ").append(this.notificacao).append("\n");
        sb.append("Viatura: ").append(this.viatura.getMatricula()).append("\n");

        return sb.toString();
    }
}
