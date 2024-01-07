package src.SubPostos;
public class Mecanico {

    private String id;
    private String nome;
    private String password;
    

    public Mecanico() {
        this.id = "";
        this.nome = "";
        this.password = "";
    }


    public Mecanico(String id, String nome, String password) {
        
        this.id = id;
        this.nome = nome;
        this.password = password;
    }

    public Mecanico(Mecanico mecanico) {
        this.id = mecanico.getID();
        this.nome = mecanico.getNome();
        this.password = mecanico.getPassword();
    }

    public String getID(){
        return this.id;
    }
    public void setID(String id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public Mecanico clone() {
    return new Mecanico(this);
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Mecanico mecanico = (Mecanico) o;
        return this.id.equals(mecanico.getID())
               && this.nome.equals(mecanico.getNome())
               && this.password.equals(mecanico.getPassword());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("ID: ").append(this.id).append("\n");
        sb.append("Nome: ").append(this.nome).append("\n");
        
        return sb.toString();
    }
}
