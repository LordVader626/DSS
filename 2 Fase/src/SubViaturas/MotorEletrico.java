package src.SubViaturas;

public class MotorEletrico extends Motor{

    private String bateria;

    public MotorEletrico() {
        super();
        this.bateria = "";
    }


    public MotorEletrico(int codigo, int tipoMotor, String bateria) {
        super(codigo, tipoMotor);
        this.bateria = bateria;
    }

    public MotorEletrico(MotorEletrico motorEletrico) {
        super(motorEletrico);
        this.bateria = motorEletrico.getBateria();
    }


    public String getBateria(){
        return this.bateria;
    }
    public void setBateria(String bateria){
        this.bateria = bateria;
    }


    @Override
    public MotorEletrico clone() {
    return new MotorEletrico(this);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        MotorEletrico me = (MotorEletrico) o;
        return this.bateria.equals(me.getBateria());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append (super.toString());
        sb.append("Bateria: ").append(this.bateria).append("\n");

        return sb.toString();
    }
}
