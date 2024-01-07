package src;

import src.DAOs.*;
import src.IU.TextoUI;

public class ES_Ideal {

    public static void main(String[]args){
        
        Mecanico_DAO.buildInstance();
        PostoTrabalho_DAO.buildInstance();
        Viatura_DAO.buildInstance();
        Servico_DAO.buildInstance();
        Cliente_DAO.buildInstance();
        
        new TextoUI().run();
    }

}
