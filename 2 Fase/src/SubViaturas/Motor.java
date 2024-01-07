package src.SubViaturas;

public abstract class Motor {

    private int codigo;
    private int tipoMotor; // 1->CombustaoGasolina, 2-> CombustaoDiesel 3-> Eletrico 4-> Hibrido com Diesel 5-> Hibrido com Gasolina

    public Motor() {
        this.codigo = 0;
        this.tipoMotor = 0;
    }


    public Motor( int codigo, int tipoMotor) {
        this.codigo = codigo;
        this.tipoMotor = tipoMotor;
    }

    public Motor(Motor motor) {

        this.codigo = motor.getCodigo();
        this.tipoMotor = motor.getTipoMotor();
    }

    public int getCodigo(){
        return this.codigo;
    }
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public int getTipoMotor(){
        return this.tipoMotor;
    }
    public void setTipoMotor(int tipoMotor){
        this.tipoMotor = tipoMotor;
    }


    public abstract Motor clone();
    

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Motor motor = (Motor) o;
        return this.tipoMotor == motor.getTipoMotor() && this.tipoMotor == motor.getCodigo();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Código: ").append(this.codigo).append("\n");
        sb.append("Tipo de Motor: ").append(this.tipoMotor).append("\n");

        return sb.toString();
    }
}
