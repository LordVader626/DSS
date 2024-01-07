package src.SubViaturas;


public interface ISubViaturas {
    
    public boolean validaViatura(String matricula);

    public int getTipoMotor(String matricula);

    public Viatura getViatura(String matricula);

    public void atualizaFichaVeiculo(String estado, Viatura v , int nserv);

    public void realizarServico(int tipoServico , String descricao , Viatura v);

}
