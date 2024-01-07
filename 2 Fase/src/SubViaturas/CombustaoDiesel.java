package src.SubViaturas;

public class CombustaoDiesel extends MotorCombustao{

    private String velas_incasdescencia;
    private String filtro_particulas;

    public CombustaoDiesel() {
        super();
        this.velas_incasdescencia = "";
        this.filtro_particulas = "";
    }


    public CombustaoDiesel(int codigo, int tipoMotor, String oleo, String filtros_oleo, String combustivel, String ar_do_motor, String conversor, String bateria_arranque,
    String velas_inc, String filtroP) {

        super(codigo,tipoMotor,oleo,filtros_oleo,combustivel,ar_do_motor,conversor,bateria_arranque);
        this.velas_incasdescencia = velas_inc;
        this.filtro_particulas = filtroP;
    }

    public CombustaoDiesel(CombustaoDiesel combustaoDiesel) {
        super(combustaoDiesel);
        this.velas_incasdescencia = combustaoDiesel.getVelas_Inc();
        this.filtro_particulas = combustaoDiesel.getFiltro_Particulas();
    }

    public String getVelas_Inc(){
        return this.velas_incasdescencia;
    }
    public void setVelas_Inc(String velas){
        this.velas_incasdescencia = velas;
    }

    public String getFiltro_Particulas(){
        return this.filtro_particulas;
    }
    public void setFiltros_Particulas(String filtrop){
        this.filtro_particulas = filtrop;
    }

    @Override
    public CombustaoDiesel clone() {
    return new CombustaoDiesel(this);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        CombustaoDiesel cd = (CombustaoDiesel) o;
        return this.velas_incasdescencia.equals(cd.getVelas_Inc())
               && this.filtro_particulas.equals(cd.getFiltro_Particulas());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append (super.toString());
        sb.append("Velas de Incandescência: ").append(this.velas_incasdescencia).append("\n");
        sb.append("Filtro de Partículas: ").append(this.filtro_particulas).append("\n");

        return sb.toString();
    }
}
