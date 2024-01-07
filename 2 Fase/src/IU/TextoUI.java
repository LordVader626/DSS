package src.IU;

import java.util.List;
import java.util.Scanner;

import src.ES_IdealFacade;
import src.IES_Ideal;
import src.SubPostos.PostoTrabalho;
import src.SubPostos.Servico;
import src.SubViaturas.Viatura;

public class TextoUI {

    
    private IES_Ideal model;
    private Scanner sc;


    public TextoUI() {
        this.model = new ES_IdealFacade();
        sc = new Scanner(System.in);
    }


    /**
     * Método que executa o menu principal.
     * Coloca a interface em execução.
     */

    public void run() {
        Menu menu = new Menu(new String[] {
                " Agendar serviço",
                " Login Funcionário"
        });

        
        menu.setHandler(1, () -> agendarServico());
        menu.setHandler(2, () -> loginTerminal());

        menu.run();
    }

    
    private void agendarServico() {
        System.out.println("Digite o nome do cliente: ");
        String nome = sc.nextLine();
        System.out.println("Digite o seu NIF: ");
        String nif = sc.nextLine();
        System.out.println("Digite a matricula da sua viatura: ");
        String matricula = sc.nextLine();

        if (!this.model.validaCliente(nome,nif)) System.out.println("O cliente não se encontra registado!");

        else if (!this.model.validaViatura(matricula) && !this.model.verificaLigacaoViaturaCliente(matricula,nif)) 
            System.out.println("A viatura não está associada ao cliente!");
            
        else criarServicos(matricula);
    }

    private void criarServicos(String matricula) {
        System.out.println("Escolha que tipo de serviço pretende: ");
        Menu menu = new Menu(new String[]{
                                "Universal",
                                "Motor de Combustão",
                                "Motor Elétrico",
                                "Motor Hibrido"
                                ,"Check-up"
                            });
        int tipo = this.model.getTipoMotor(matricula);
        int nrserv = this.model.nrServicosViatura(matricula);
        menu.setPreCondition(2, () -> tipo == 1 || tipo == 2);
        menu.setPreCondition(3, () -> tipo == 3);
        menu.setPreCondition(4, () -> tipo == 4 || tipo == 5);
        menu.setPreCondition(5, () -> nrserv == 0);
        
        menu.setHandler(1, () -> criarServicoUniversal(matricula));
        menu.setHandler(2, () -> criarServicoMotorCombustao(matricula));
        menu.setHandler(3, () -> criarServicoMotorEletrico(matricula));
        menu.setHandler(4, () -> criarServicoMotorHibrido(matricula));
        menu.setHandler(5, () -> agendaServico(5,"checkup",matricula,0));


        menu.run();
    
    }


    private void criarServicoUniversal(String matricula) {
        System.out.println("Escolha que serviço pretende: ");
        Menu menu = new Menu(new String[]{

                                "Substituição dos pneus",
                                "Calibragem das rodas",
                                "Substituição dos injetores",
                                "Substituição dos calços dos travões",
                                "Alinhamento da direção",
                                "Substituição do filtro de ar"});
        
        
        menu.setHandler(1, () -> agendaServico(0,"substituir pneus",matricula,0));
        menu.setHandler(2, () -> agendaServico(0,"calibrar rodas",matricula,0));
        menu.setHandler(3, () -> agendaServico(0,"substituir injetores",matricula,0));
        menu.setHandler(4, () -> agendaServico(0,"substituir travoes",matricula,0));
        menu.setHandler(5, () -> agendaServico(0,"alinhar direcao",matricula,0));
        menu.setHandler(6, () -> agendaServico(0,"substituir filtro ar",matricula,0));
        
        menu.run();
    }

    private void agendaServico(int tipo, String descricao, String matricula, int checkup) {
        System.out.println("Você pretende receber uma notificação quando o serviço for realizado? [S/N]");
        boolean notif;
        String s = sc.nextLine();
            if (s.equals("S") || s.equals("s")){
                notif = true;
            } else notif = false;

        String esp ;
        if (tipo == 0) esp = "universal";
        else if (tipo == 4) esp = "eletrico";
        else if (tipo == 5) esp = "checkup";
        else esp = "combustao";

        boolean aux = this.model.verificaSePostosCheios(esp);
        if (aux) {
            Viatura v = this.model.getViatura(matricula);
            PostoTrabalho pt = this.model.getPostoDisponivel(esp);
            this.model.agendaServico(tipo,descricao,v,notif,pt);
            int nserv = this.model.nrServicosViatura(v.getMatricula());

            if (tipo != 5 ) this.model.atualizaFichaVeiculo("a precisar reparacao",v,nserv);
            else this.model.atualizaFichaVeiculo("a precisar checkup",v,nserv);
            System.out.println("O serviço foi agendado no " + pt.getNome() +"!");

             if (tipo == 5 ) run();
             if (checkup == 0) {

            System.out.println("Pretende agendar outro serviço? [S/N]");
            String sa = sc.nextLine();
            if (sa.equals("S") || sa.equals("s")){
                criarServicos(matricula);
            } else run();
             }
        }
        else {System.out.println("Não é possivel agendar serviços deste tipo para hoje!"); 
              if (checkup == 0)  run();
    }
    }


    private void criarServicoMotorCombustao(String matricula) {
        System.out.println("Escolha que tipo de serviço pretende: ");
        Menu menu = new Menu(new String[]{
                                "Geral",
                                "Motor de Combustão a Diesel",
                                "Motor de Combustão a Gasolina"});

        int tipo = this.model.getTipoMotor(matricula);

        menu.setPreCondition(2, () -> tipo == 1 || tipo == 4);
        menu.setPreCondition(3, () -> tipo == 2 || tipo == 5);
        
        menu.setHandler(1, () -> criarServicoCombustaoGeral(matricula));
        menu.setHandler(2, () -> criarServicoCombustaoDiesel(matricula));
        menu.setHandler(3, () -> criarServicoCombustaoGasolina(matricula));

        menu.run();
    
    }

    private void criarServicoCombustaoGeral(String matricula) {
        System.out.println("Escolha que serviço pretende: ");
        Menu menu = new Menu(new String[]{
                                "Mudança de óleo do motor",
                                "Substituição dos filtros de óleo",
                                "Substituição dos filtros de combustivel",
                                "Substituição dos filtros de ar do motor",
                                "Substituição do conversor catalítico",
                                "Substituição da bateria de arranque"});

        
        menu.setHandler(1, () -> agendaServico(1,"mudar oleo motor",matricula,0));
        menu.setHandler(2, () -> agendaServico(1,"trocar filtro oleo",matricula,0));
        menu.setHandler(3, () -> agendaServico(1,"trocar filtro combustivel",matricula,0));
        menu.setHandler(4, () -> agendaServico(1,"trocar filtro ar motor",matricula,0));
        menu.setHandler(5, () -> agendaServico(1,"trocar conversor",matricula,0));
        menu.setHandler(6, () -> agendaServico(1,"trocar bateria arranque",matricula,0));

        menu.run();
    
    }

    private void criarServicoCombustaoDiesel(String matricula) {
        System.out.println("Escolha que serviço pretende: ");
        Menu menu = new Menu(new String[]{
                                "Substituição das velas de incandescência",
                                "Substituição dos filtros de particulas"});

        
        menu.setHandler(1, () -> agendaServico(2,"trocar velas incandescencia",matricula,0));
        menu.setHandler(2, () -> agendaServico(2,"trocar filtro particulas",matricula,0));

        menu.run();
    
    }

    private void criarServicoCombustaoGasolina(String matricula) {
        System.out.println("Escolha que serviço pretende: ");
        Menu menu = new Menu(new String[]{
                                "Substituição da válvula do acelerador ",
                                "Substituição das velas de ignição"});

        
        menu.setHandler(1, () -> agendaServico(3,"trocar valvula acelerador",matricula,0));
        menu.setHandler(2, () -> agendaServico(3,"trocar velas ignicao",matricula,0));

        menu.run();
    
    }

    private void criarServicoMotorEletrico(String matricula) {
        System.out.println("Escolha que serviço pretende: ");
        Menu menu = new Menu(new String[]{
                                "Avaliação do desempenho da bateria",
                                "Substituição da bateria"});

        
        menu.setHandler(1, () -> agendaServico(4,"avaliar bateria",matricula,0));
        menu.setHandler(2, () -> agendaServico(4,"trocar bateria",matricula,0));

        menu.run();
    
    }

    private void criarServicoMotorHibrido(String matricula) {
        System.out.println("Escolha o motor em que pretende realizar o serviço: ");
        Menu menu = new Menu(new String[]{
                                "Motor de Combustao",
                                "Motor Eletrico"});

        
        menu.setHandler(1, () -> criarServicoMotorCombustao(matricula));
        menu.setHandler(2, () -> criarServicoMotorEletrico(matricula));

        menu.run();
    
    }

    


    private void loginTerminal() {
        System.out.println("Escolha o seu posto:");
        List<String> opcoes = this.model.getListaNomesPostos();

        for (int i=0; i<opcoes.size();i++){
            System.out.println((i+1) + " -> " + opcoes.get(i));
        }
        int posto = sc.nextInt();
        sc.nextLine();
        loginPosto(opcoes.get(posto-1));
    }

    private void loginPosto(String nomePosto){

        System.out.println("Digite o seu nome: ");
        String nome = sc.nextLine();
        System.out.println("Digite a sua password: ");
        String password = sc.nextLine();
        boolean n = this.model.validaMecanico(nome,password,nomePosto);
        if(n){

            menuMecanico(nomePosto);
        }
        else System.out.println("Não existe nenhum mecânico com este nome e password neste posto!");
            
    }

    private void menuMecanico(String nomePosto){
         Menu menu = new Menu(new String[] {
                " Consultar serviços",
                " Iniciar serviço"
        });

        
        menu.setHandler(1, () -> consultarServicos(nomePosto));
        menu.setHandler(2, () -> iniciarServico(nomePosto));

        menu.run();
    }

    private void consultarServicos(String nomePosto){
        List<Servico> aux = this.model.getListaServicos(nomePosto);
        if (aux.size()==0) System.out.println("Não tem nenhum serviço a fazer!");
        else {
        System.out.println("Aqui segue a lista de serviços a fazer: ");
        for(Servico s : aux) System.out.println(s.toString()); 
    }
        
    }

    private void iniciarServico(String nomePosto){
        Servico s = this.model.getServicoARealizar(nomePosto);
        if (s!=null) {
            if (s.getTipo() == 5) realizarCheckup(s);
            else {
        boolean notif = s.getNotificacao();
        System.out.println("Conseguiu realizar o serviço? [S/N]");
            String sa = sc.nextLine();
            if (sa.equals("S") || sa.equals("s")){
                
                this.model.realizarServico(s);
                System.out.println("O serviço foi realizado!");

            } else {
                System.out.println("Indique o motivo: ");
                    sc.nextLine();

        }
                this.model.apagarServico(s);
                int nrserv = this.model.nrServicosViatura(s.getViatura().getMatricula());
                this.model.atualizaFichaVeiculo("reparado",s.getViatura(),nrserv);
                
                if (notif && nrserv == 0) System.out.println("Uma notificação foi enviada para o cliente para levar a sua viatura!");
            }
    } else System.out.println("Não existe nenhum serviço a realizar!");

}

private void realizarCheckup(Servico s) {

    List<Servico> listS = this.model.realizarCheckup(s.getViatura());
    this.model.apagarServico(s);
    int size = listS.size();
    if (size == 0) {
        this.model.atualizaFichaVeiculo("reparado",s.getViatura(),0);
        System.out.println("Não existe nenhum serviço a ser feito!");
}
    else{ 
        System.out.println("Existem "+ size +" serviços a serem feitos!");
        int i = 0;
        while(i<size){
        System.out.println("Pretende que o serviço '" + listS.get(i).getDescricao()+"' seja agendado ? [S/N]");
        String sa = sc.nextLine();
            if (sa.equals("S") || sa.equals("s")){
                Servico serv = listS.get(i);
                agendaServico(serv.getTipo(),serv.getDescricao(),serv.getViatura().getMatricula(),1);
               
            }
            i++;
        }
    }
}

}
