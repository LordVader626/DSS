package src.SubViaturas;

public  class Viatura {

    private String matricula;
    private String pneus;
    private String rodas;
    private String direcao;
    private String injetores;
    private String travoes;
    private String filtro_de_ar;
    private String ficha_veiculo;
    private Motor motor;


    public Viatura() {
        this.matricula = "";
        this.pneus = "";
        this.rodas = "";
        this.direcao = "";
        this.injetores = "";
        this.travoes = "";
        this.filtro_de_ar = "";
        this.ficha_veiculo = "";
    }


    public Viatura(String matricula, String pneus, String rodas, String direcao, String injetores, String travoes, String filtro_de_ar, 
                    String ficha_veiculo, Motor motor) {
        
        this.matricula = matricula;
        this.pneus = pneus;
        this.rodas = rodas;
        this.direcao = direcao;
        this.injetores = injetores;
        this.travoes = travoes;
        this.filtro_de_ar = filtro_de_ar;
        this.ficha_veiculo = ficha_veiculo;
        this.motor = motor;
    }

    public Viatura(Viatura viatura) {
        this.matricula = viatura.getMatricula();
        this.pneus = viatura.getPneus();
        this.rodas = viatura.getRodas();
        this.direcao = viatura.getDirecao();
        this.injetores = viatura.getInjetores();
        this.travoes = viatura.getTravoes();
        this.filtro_de_ar = viatura.getFiltroAr();
        this.ficha_veiculo = viatura.getFichaVeiculo();
        this.motor = viatura.getMotor();
    }

    public String getMatricula(){
        return this.matricula;
    }
    public void setMatricula(String matricula){
        this.matricula = matricula;
    }

    public String getPneus(){
        return this.pneus;
    }
    public void setPneus(String pneus){
        this.pneus = pneus;
    }

    public String getRodas(){
        return this.rodas;
    }
    public void setRodas(String rodas){
        this.rodas = rodas;
    }

    public String getDirecao(){
        return this.direcao;
    }
    public void setDirecao(String direcao){
        this.direcao = direcao;
    }

    public String getInjetores(){
        return this.injetores;
    }
    public void setInjetores(String injetores){
        this.injetores = injetores;
    }

    public String getTravoes(){
        return this.travoes;
    }
    public void setTravoes(String travoes){
        this.travoes = travoes;
    }

    public String getFiltroAr(){
        return this.filtro_de_ar;
    }
    public void setFiltroAr(String filtro_de_ar){
        this.filtro_de_ar = filtro_de_ar;
    }

    public String getFichaVeiculo(){
        return this.ficha_veiculo;
    }
    public void setFichaVeiculo(String ficha_veiculo){
        this.ficha_veiculo = ficha_veiculo;
    }

    public Motor getMotor(){
        return this.motor;
    }
    public void setMotor(Motor Motor){
        this.motor = Motor;
    }


    public Viatura clone() {
    return new Viatura(this);
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Viatura viatura = (Viatura) o;
        return this.matricula.equals(viatura.getMatricula())
               && this.pneus.equals(viatura.getPneus())
               && this.rodas.equals(viatura.getRodas())
               && this.direcao.equals(viatura.getDirecao())
               && this.injetores.equals(viatura.getInjetores())
               && this.travoes.equals(viatura.getTravoes())
               && this.filtro_de_ar.equals(viatura.getFiltroAr())
               && this.ficha_veiculo.equals(viatura.getFichaVeiculo())
               && this.motor.equals(viatura.getMotor());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Matricula: ").append(this.matricula).append("\n");
        sb.append("Pneus: ").append(this.pneus).append("\n");
        sb.append("Rodas: ").append(this.rodas).append("\n");
        sb.append("Direção: ").append(this.direcao).append("\n");
        sb.append("Injetores: ").append(this.injetores).append("\n");
        sb.append("Travões: ").append(this.travoes).append("\n");
        sb.append("Filtro de Ar: ").append(this.filtro_de_ar).append("\n");
        sb.append("Ficha do veículo: ").append(this.ficha_veiculo).append("\n");
        sb.append("Motor: ").append(this.motor).append("\n");

        return sb.toString();
    }
}