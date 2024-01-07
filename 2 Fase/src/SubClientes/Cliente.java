package src.SubClientes;

import src.SubViaturas.Viatura;

public class Cliente {

    private String nif;
    private String nome;
    private String contacto;
    private Viatura viatura;
    

    public Cliente() {
        this.nif = "";
        this.nome = "";
        this.contacto = "";
        this.viatura = new Viatura();
    }


    public Cliente(String nif, String nome, String contacto,Viatura viatura) {
        
        this.nif = nif;
        this.nome = nome;
        this.contacto = contacto;
        this.viatura = viatura;
    }

    public Cliente(Cliente utilizador) {
        this.nif = utilizador.getNIF();
        this.nome = utilizador.getNome();
        this.contacto = utilizador.getContacto();
        this.viatura = utilizador.getViatura();
    }

    public String getNIF(){
        return this.nif;
    }
    public void setNIF(String nif){
        this.nif = nif;
    }

    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getContacto(){
        return this.contacto;
    }
    public void setContacto(String contacto){
        this.contacto = contacto;
    }

    public Viatura getViatura(){
        return this.viatura;
    }
    public void setViatura(Viatura viatura){
        this.viatura = viatura;
    }
    

    public Cliente clone() {
    return new Cliente(this);
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Cliente util = (Cliente) o;
        return this.nif.equals(util.getNIF())
               && this.nome.equals(util.getNome())
               && this.contacto.equals(util.getContacto())
               && this.viatura.equals(util.getViatura());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("NIF: ").append(this.nif).append("\n");
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Contacto: ").append(this.contacto).append("\n");
        
        return sb.toString();
    }
}
