package src.SubViaturas;

public class MotorCombustao extends Motor {

    private String oleo;
    private String filtros_oleo;
    private String combustivel;
    private String ar_do_motor;
    private String conversor;
    private String bateria_arranque;

    public MotorCombustao() {
        super();
        this.oleo = "";
        this.filtros_oleo = "";
        this.combustivel = "";
        this.ar_do_motor = "";
        this.conversor = "";
        this.bateria_arranque = "";
    }


    public MotorCombustao(int codigo, int tipoMotor, String oleo, String filtros_oleo, String combustivel, String ar_do_motor, String conversor, String bateria_arranque) {
        super(codigo,tipoMotor);
        this.oleo = oleo;
        this.filtros_oleo = filtros_oleo;
        this.combustivel = combustivel;
        this.ar_do_motor = ar_do_motor;
        this.conversor = conversor;
        this.bateria_arranque = bateria_arranque;
    }

    public MotorCombustao(MotorCombustao motorCombustao) {
        super(motorCombustao);
        this.oleo = motorCombustao.getOleo();
        this.filtros_oleo = motorCombustao.getFiltrosOleo();
        this.combustivel = motorCombustao.getCombustivel();
        this.ar_do_motor = motorCombustao.getArMotor();
        this.conversor = motorCombustao.getConversor();
        this.bateria_arranque = motorCombustao.getBateriaArranque();
    }

    public String getOleo(){
        return this.oleo;
    }
    public void setOleo(String oleo){
        this.oleo = oleo;
    }

    public String getFiltrosOleo(){
        return this.filtros_oleo;
    }
    public void setFiltrosOleo(String filtrosOleo){
        this.filtros_oleo = filtrosOleo;
    }

    public String getCombustivel(){
        return this.combustivel;
    }
    public void setCombustivel(String combustivel){
        this.combustivel = combustivel;
    }

    public String getArMotor(){
        return this.ar_do_motor;
    }
    public void setArMotor(String armotor){
        this.ar_do_motor = armotor;
    }

    public String getConversor(){
        return this.conversor;
    }
    public void setConversor(String conversor){
        this.conversor = conversor;
    }

    public String getBateriaArranque(){
        return this.bateria_arranque;
    }
    public void setBateriaArranque(String bateriaA){
        this.bateria_arranque = bateriaA;
    }


    @Override
    public MotorCombustao clone() {
    return new MotorCombustao(this);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        MotorCombustao mc = (MotorCombustao) o;
        return this.oleo.equals(mc.getOleo())
               && this.filtros_oleo.equals(mc.getFiltrosOleo())
               && this.combustivel.equals(mc.getCombustivel())
               && this.ar_do_motor.equals(mc.getArMotor())
               && this.conversor.equals(mc.getConversor())
               && this.bateria_arranque.equals(mc.getBateriaArranque());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append (super.toString());
        sb.append("Óleo: ").append(this.oleo).append("\n");
        sb.append("Filtros de Óleo: ").append(this.filtros_oleo).append("\n");
        sb.append("Combustivel: ").append(this.combustivel).append("\n");
        sb.append("Ar do Motor: ").append(this.ar_do_motor).append("\n");
        sb.append("Conversor: ").append(this.conversor).append("\n");
        sb.append("Bateria de Arranque: ").append(this.bateria_arranque).append("\n");

        return sb.toString();
    }
}
