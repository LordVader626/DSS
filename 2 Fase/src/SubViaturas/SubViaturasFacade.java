package src.SubViaturas;


import src.DAOs.Viatura_DAO;


public class SubViaturasFacade implements ISubViaturas{
    //resolucao das funcoes

     private Viatura_DAO viaturas = Viatura_DAO.getInstance();

     public boolean validaViatura(String matricula){
        return this.viaturas.containsKey(matricula);
    }

    public int getTipoMotor(String matricula){
        return this.viaturas.get(matricula).getMotor().getTipoMotor();
    }

    public Viatura getViatura(String matricula){
        return this.viaturas.get(matricula);
    }
    public void atualizaFichaVeiculo(String estado, Viatura v , int nserv){
        
        if (estado.equals("reparado") && nserv != 0){
            
            v.setFichaVeiculo("a precisar reparacao");
            
        }

        else {
                v.setFichaVeiculo(estado);
        }
        this.viaturas.put(v.getMatricula(), v);
    
}

public void realizarServico(int tipoServico , String descricao , Viatura v){ 
        
        Motor motor = v.getMotor();
        int tipoMotor = motor.getTipoMotor();
        MotorHibrido mh = null;
        if (tipoServico == 0){
            if (descricao.equals("substituir pneus")) v.setPneus("novo");
            else if (descricao.equals("calibrar rodas")) v.setRodas("novo");
            else if (descricao.equals("substituir injetores")) v.setInjetores("novo");
            else if (descricao.equals("substituir travoes")) v.setTravoes("novo");
            else if (descricao.equals("alinhar direcao")) v.setDirecao("novo");
            else if (descricao.equals("substituir filtro ar")) v.setFiltroAr("novo");
        }

        else if (tipoServico == 1 ) { 
            MotorCombustao mc = null;
            
                    if (tipoMotor == 1 || tipoMotor == 2) mc = (MotorCombustao) motor;
                        else {
                            mh = (MotorHibrido) motor;  
                            mc = (MotorCombustao) mh.getMotorCombustao();
                        }
            if (descricao.equals("mudar oleo motor")) mc.setOleo("novo");
            else if (descricao.equals("trocar filtro oleo")) mc.setFiltrosOleo("novo");
            else if (descricao.equals("trocar filtro combustivel")) mc.setCombustivel("novo");
            else if (descricao.equals("trocar filtro ar motor")) mc.setArMotor("novo");
            else if (descricao.equals("trocar conversor")) mc.setConversor("novo");
            else if (descricao.equals("trocar bateria arranque")) mc.setBateriaArranque("novo");
            
            if (tipoMotor == 1 || tipoMotor == 2) v.setMotor(mc);     
            else {mh.setMotorCombustao(mc);
            v.setMotor(mh);}
        }
        
        else if (tipoServico == 2 ) { 
            CombustaoDiesel cd = null;
            
                    if (tipoMotor == 1 ) cd = (CombustaoDiesel) motor;
                        else {
                            mh = (MotorHibrido) motor;  
                            cd = (CombustaoDiesel) mh.getMotorCombustao();
                        }
            if (descricao.equals("trocar velas incandescencia")) cd.setVelas_Inc("novo");
            else if (descricao.equals("trocar filtro particulas")) cd.setFiltros_Particulas("novo");
                       
            if (tipoMotor == 1 ) v.setMotor(cd);     
            else {mh.setMotorCombustao(cd);
            v.setMotor(mh);}
        }

        else if (tipoServico == 3 ) { 
            CombustaoGasolina cg = null;
            
                    if (tipoMotor == 2 ) cg = (CombustaoGasolina) motor;
                        else {
                            mh = (MotorHibrido) motor;  
                            cg = (CombustaoGasolina) mh.getMotorCombustao();
                        }
            if (descricao.equals("trocar valvula acelerador")) cg.setValvulaAceleracao("novo");
            else if (descricao.equals("trocar velas ignicao")) cg.setVelas_Ignicao("novo");
                       
            if (tipoMotor == 2 ) v.setMotor(cg);     
            else {mh.setMotorCombustao(cg);
            v.setMotor(mh);}
        }
        else if (tipoServico == 4 ) { 
            MotorEletrico me = null;
           
                    if (tipoMotor == 3 ) me = (MotorEletrico) motor;
                        else {
                            mh = (MotorHibrido) motor;  
                            me = (MotorEletrico) mh.getMotorEletrico();
                        }
            if (descricao.equals("trocar bateria")) me.setBateria("novo");
            else if (descricao.equals("avaliar bateria")) {
                if (me.getBateria().equals("novo")) System.out.println("A bateria está em boas condições!");
                else System.out.println("A bateria deveria ser trocada!");
            }

                       
            if (tipoMotor == 3 ) v.setMotor(me);     
            else {mh.setMotorEletrico(me);
            v.setMotor(mh);}
        }

        this.viaturas.put(v.getMatricula(), v);

    }

}