package src.SubViaturas;

public class CombustaoGasolina extends MotorCombustao{
    private String velas_ignicao;
    private String valvulaAceleracao;

    public CombustaoGasolina() {
        super();
        this.velas_ignicao = "";
        this.valvulaAceleracao = "";
    }


    public CombustaoGasolina(int codigo, int tipoMotor, String oleo, String filtros_oleo, String combustivel, String ar_do_motor, String conversor, String bateria_arranque,
    String velasI, String valvula) {

        super(codigo,tipoMotor,oleo,filtros_oleo,combustivel,ar_do_motor,conversor,bateria_arranque);
        this.velas_ignicao = velasI;
        this.valvulaAceleracao = valvula;
    }

    public CombustaoGasolina(CombustaoGasolina combustaoGasolina) {
        super(combustaoGasolina);
        this.velas_ignicao = combustaoGasolina.getVelas_Ignicao();
        this.valvulaAceleracao = combustaoGasolina.getValvulaAceleracao();
    }

    public String getVelas_Ignicao(){
        return this.velas_ignicao;
    }
    public void setVelas_Ignicao(String velas){
        this.velas_ignicao = velas;
    }

    public String getValvulaAceleracao(){
        return this.valvulaAceleracao;
    }
    public void setValvulaAceleracao(String valvula){
        this.valvulaAceleracao = valvula;
    }

    @Override
    public CombustaoGasolina clone() {
    return new CombustaoGasolina(this);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        CombustaoGasolina cg = (CombustaoGasolina) o;
        return this.velas_ignicao.equals(cg.getVelas_Ignicao())
               && this.valvulaAceleracao.equals(cg.getValvulaAceleracao());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append (super.toString());
        sb.append("Velas de Ignição: ").append(this.velas_ignicao).append("\n");
        sb.append("Válvula de aceleração: ").append(this.valvulaAceleracao).append("\n");

        return sb.toString();
    }
}
