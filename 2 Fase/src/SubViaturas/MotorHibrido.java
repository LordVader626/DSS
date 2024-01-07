package src.SubViaturas;

public class MotorHibrido extends Motor{
    
    private MotorCombustao motorCombustao;
    private MotorEletrico motorEletrico;

    public MotorHibrido() {
        super();
        this.motorCombustao = new MotorCombustao();
        this.motorEletrico = new MotorEletrico();
    }


    public MotorHibrido(int codigo, int tipoMotor, MotorCombustao mc, MotorEletrico me) {
        super(codigo,tipoMotor);
        this.motorCombustao = mc;
        this.motorEletrico = me;

    }

    public MotorHibrido(MotorHibrido motorHibrido) {
        super(motorHibrido);
        this.motorCombustao = motorHibrido.getMotorCombustao();
        this.motorEletrico = motorHibrido.getMotorEletrico();
    }


    public MotorCombustao getMotorCombustao(){
        return this.motorCombustao;
    }
    public void setMotorCombustao(MotorCombustao mc){
        this.motorCombustao = mc;
    }

    public MotorEletrico getMotorEletrico(){
        return this.motorEletrico;
    }
    public void setMotorEletrico(MotorEletrico me){
        this.motorEletrico = me;
    }


    @Override
    public MotorHibrido clone() {
    return new MotorHibrido(this);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        MotorHibrido mh = (MotorHibrido) o;
        return this.motorCombustao.equals(mh.getMotorCombustao())
        && this.motorEletrico.equals(mh.getMotorEletrico());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append (super.toString());
        sb.append("Motor de Combustão-> ").append("\n").append(this.motorCombustao).append("\n");
        sb.append("Motor de Elétrico-> ").append("\n").append(this.motorEletrico).append("\n");

        return sb.toString();
    }
}

