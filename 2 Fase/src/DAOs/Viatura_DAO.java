package src.DAOs;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import src.SubViaturas.*;

public class Viatura_DAO implements Map <String, Viatura>{

    private static Viatura_DAO singleton = null;

    private Viatura_DAO() {
        try (
            Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
            Statement stm = con.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS motores (" +
                        "Codigo INT NOT NULL PRIMARY KEY," +
                        "Tipo_Motor INT DEFAULT 0);"; // 1-> CombustaoDiesel , 2-> CombustaoGasolina , 3-> Eletrico , 4-> Hibrido com CombustaoDiesel , 5-> Hibrido com CombustaoGasolina
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS motoresCombustao (" +
                    "Codigo int NOT NULL PRIMARY KEY," +
                    "Oleo varchar(45) DEFAULT ''," +
                    "Filtro_Oleo varchar(45) DEFAULT ''," +
                    "Combustivel varchar(45) DEFAULT ''," +
                    "Ar_do_Motor varchar(45) DEFAULT ''," +
                    "Conversor varchar(45) DEFAULT ''," +
                    "Bateria_Arranque varchar(45) DEFAULT ''," +
                    "FOREIGN KEY (Codigo) REFERENCES motores(Codigo));";// JUNTA OS MOTORES NO SUBVIATURA E REESCREVE AS FUNCOES ABAIXO
            stm.executeUpdate(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS combustaoGasolina (" +
                    "Codigo int NOT NULL PRIMARY KEY," +
                    "Velas_Ignicao varchar(45) DEFAULT ''," +
                    "Valvula_Aceleracao varchar(45) DEFAULT ''," +
                    "FOREIGN KEY (Codigo) REFERENCES motoresCombustao(Codigo));";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS combustaoDiesel (" +
                    "Codigo int NOT NULL PRIMARY KEY," +
                    "Velas_Incandescencia varchar(45) DEFAULT ''," +
                    "Filtro_Particulas varchar(45) DEFAULT ''," +
                    "FOREIGN KEY (Codigo) REFERENCES motoresCombustao(Codigo));";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS motoresEletricos (" +
                    "Codigo int NOT NULL PRIMARY KEY," +
                    "Bateria varchar(45) DEFAULT ''," +
                    "FOREIGN KEY (Codigo) REFERENCES motores(Codigo));";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS motoresHibridos (" +
                    "Codigo int NOT NULL PRIMARY KEY," +
                    "Motor_Eletrico int NOT NULL," +
                    "Motor_Combustao int NOT NULL," +
                    "FOREIGN KEY (Motor_Eletrico) REFERENCES motoresEletricos(Codigo)," +
                    "FOREIGN KEY (Motor_Combustao) REFERENCES motoresCombustao(Codigo)," +
                    "FOREIGN KEY (Codigo) REFERENCES motores(Codigo));";
            stm.executeUpdate(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS viaturas (" +
                    "Matricula varchar(45) NOT NULL PRIMARY KEY," +
                    "Pneus varchar(45) DEFAULT ''," +
					"Rodas varchar(45) DEFAULT ''," +
					"Direcao varchar(45) DEFAULT ''," +
                    "Injetores varchar(45) DEFAULT ''," +
                    "Travoes varchar(45) DEFAULT ''," +
                    "Filtro_de_Ar varchar(45) DEFAULT ''," +
                    "Ficha_de_veiculo varchar(45) DEFAULT ''," +
                    "Motor int NOT NULL," +
                    "FOREIGN KEY (Motor) REFERENCES motores(Codigo));";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            
            e.printStackTrace();// Se der erro a criar a tabela
            throw new NullPointerException(e.getMessage());
        }
    }

    public static Viatura_DAO getInstance() {
		if (Viatura_DAO.singleton == null) {
			Viatura_DAO.singleton = new Viatura_DAO();
		}
		return Viatura_DAO.singleton;
	}

    public static void buildInstance(){
		if (Viatura_DAO.singleton == null) {
			Viatura_DAO.singleton = new Viatura_DAO();
		}
	}

@Override
public void clear() {
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement()) {
			stm.executeUpdate("UPDATE viaturas SET Viatura=NULL");
			stm.executeUpdate("TRUNCATE viaturas");
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
}

@Override
public boolean containsKey(Object key) {
    boolean n;
		try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs =
					 stm.executeQuery("SELECT Matricula FROM viaturas WHERE Matricula='"+key.toString()+"'")) {
			n = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return n;
}

@Override
public boolean containsValue(Object value) {
    Viatura a = (Viatura) value;
    return this.containsKey(a.getMatricula());
}

@Override
public Set<Entry<String, Viatura>> entrySet() {

    throw new UnsupportedOperationException("Unimplemented method 'entrySet'");
}

@Override
public Viatura get(Object key) {
    Viatura v = null;
		try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT * FROM viaturas WHERE Matricula='"+key+"'")) {
                if (rs.next()) {  
                        Motor m = null;
                    
                    String sql = "SELECT * FROM motores WHERE Codigo='"+rs.getInt("Motor")+"'";
                    try (ResultSet rsa = stm.executeQuery(sql)) {
                        if (rsa.next()) {  
                          Integer tipo = rsa.getInt("Tipo_Motor");
                          if(tipo == 1 || tipo == 2) m = getMotorCombustao(rsa.getInt("Codigo"),tipo);
                            
                    else if (tipo == 3)  m = getMotorEletrico(rsa.getInt("Codigo"), tipo);
                    
                    else if (tipo == 4 || tipo == 5) {
                            sql = "SELECT * FROM motoresHibrido WHERE Codigo='" + rsa.getInt("Codigo") + "'";
                            try (ResultSet rsaux = stm.executeQuery(sql)){
                                if(rsaux.next()) {
                                  m = new MotorHibrido(rsaux.getInt("Codigo"), tipo,getMotorCombustao(rsaux.getInt("Motor_Combustao"),tipo),getMotorEletrico(rsaux.getInt("Motor_Eletrico"),tipo));                                
                          }
                          else{//exception
                        }   
                        }  
                    }
    
                    
                    v = new Viatura(rs.getString("Matricula"),rs.getString("Pneus"), rs.getString("Rodas"),
                    rs.getString("Direcao"),rs.getString("Injetores"),rs.getString("Travoes"),rs.getString("Filtro_de_Ar"),rs.getString("Ficha_de_veiculo"),m);
                } 
                else {//exception
                }
            } 
        } 
        else{ // exception
        }
     } catch (SQLException e) {
                
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
            return v;
}

@Override
public boolean isEmpty() {
    return this.size() == 0;
}

@Override
public Set<String> keySet() {
        
        Set<String> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Matricula FROM viaturas")) {
             while (rs.next()) {
                String aux = rs.getString("Matricula"); 
                res.add(aux);                                 
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
}

@Override
public Viatura put(String arg0, Viatura arg1) {
        Viatura res = null;
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()) {
                    Motor m = arg1.getMotor();
                    stm.executeUpdate(
                        "INSERT INTO motores " +
                                    "VALUES ('"+ m.getCodigo()+ "', '" +
                                                m.getTipoMotor()+"') " +
                                    "ON DUPLICATE KEY UPDATE Tipo_Motor=Values(Tipo_Motor)");

                int tipo = m.getTipoMotor();
                if(tipo== 1 || tipo == 2) putMotorCombustao(m,tipo);
                else if (tipo == 3) putMotorEletrico(m);
                else if (tipo == 4 || tipo == 5) putMotorHibrido(m,tipo);
                    
                try(
            PreparedStatement pstm = con.prepareStatement("INSERT INTO viaturas(Matricula,Pneus,Rodas,Direcao,Injetores,Travoes,Filtro_de_Ar,Ficha_de_veiculo,Motor)" 
            + "VALUES (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE Pneus=Values(Pneus),Rodas=Values(Rodas),Direcao=Values(Direcao)," +
           "Injetores=Values(Injetores),Travoes=Values(Travoes),Filtro_de_Ar=Values(Filtro_de_Ar),Ficha_de_veiculo=Values(Ficha_de_veiculo),Motor=Values(Motor)")) {
                    pstm.setString(1,arg1.getMatricula());
                    pstm.setString(2,arg1.getPneus());
                    pstm.setString(3,arg1.getRodas());
                    pstm.setString(4,arg1.getDirecao());
                    pstm.setString(5,arg1.getInjetores());
                    pstm.setString(6,arg1.getTravoes());
                    pstm.setString(7,arg1.getFiltroAr());
                    pstm.setString(8,arg1.getFichaVeiculo());
                    pstm.setInt(9,arg1.getMotor().getCodigo());
                    pstm.execute();
                }
                res = arg1;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
            return res;
}

@Override
public void putAll(Map<? extends String, ? extends Viatura> m) {
    for(Viatura v : m.values()) {
        this.put(v.getMatricula(), v);
    }
}

@Override
public Viatura remove(Object key) {

        Viatura a = this.get(key);
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()){
                stm.executeUpdate("DELETE FROM viaturas WHERE Matricula='"+key+"'");
                int codigo = a.getMotor().getCodigo();
                stm.executeUpdate("DELETE FROM motores WHERE Codigo='"+codigo+"'");
                int tipo = a.getMotor().getTipoMotor();
                if(tipo == 1) {
                    stm.executeUpdate("DELETE FROM motoresCombustao WHERE Codigo='"+codigo+"'");
                    stm.executeUpdate("DELETE FROM CombustaoDiesel WHERE Codigo='"+codigo+"'");
                } else if(tipo == 2){
                    stm.executeUpdate("DELETE FROM motoresCombustao WHERE Codigo='"+codigo+"'");
                    stm.executeUpdate("DELETE FROM CombustaoGasolina WHERE Codigo='"+codigo+"'");
                } else if(tipo == 3){
                    stm.executeUpdate("DELETE FROM motoresEletricos WHERE Codigo='"+codigo+"'");
                } else if(tipo == 4 || tipo == 5){
                    stm.executeUpdate("DELETE FROM motoresHibridos WHERE Codigo='"+codigo+"'");// pode ser preciso remover tb dos de combustao e eletrico
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
            return a;
}

@Override
public int size() {
    
    int i = 0;
		try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT count(*) FROM viaturas")) {
			if(rs.next()) {
				i = rs.getInt(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return i;
}

@Override
public Collection<Viatura> values() {

    Collection<Viatura> res = new HashSet<>();
		try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT Matricula FROM viaturas")) { 
			while (rs.next()) {
				String idt = rs.getString("Matricula"); 
				Viatura a = this.get(idt);                    
				res.add(a);                
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return res;
}  
 

public MotorEletrico getMotorEletrico(int key, int tipo) throws SQLException {

        MotorEletrico m = null;
        try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT * FROM motoresEletricos WHERE Codigo='"+key+"'")) {
                if (rs.next()) {  
              m = new MotorEletrico(key,tipo,rs.getString("Bateria"));                                
        }
        else{//exception
    }   
    }
        return m;
}

public MotorCombustao getMotorCombustao(int key, int tipo) throws SQLException {

    MotorCombustao m = null;
        try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT * FROM motoresCombustao WHERE Codigo='"+key+"'")) {
                if (rs.next()) {
                                    String oleo = rs.getString("Oleo");
                                    String filtroOleo = rs.getString("Filtro_Oleo");
                                    String combustivel = rs.getString("Combustivel");
                                    String arMotor = rs.getString("Ar_do_Motor");
                                    String conversor = rs.getString("Conversor");
                                    String bateriaA = rs.getString("Bateria_Arranque");

                    if(tipo == 1 || tipo == 4) {
                           String sql = "SELECT * FROM combustaoDiesel WHERE Codigo='" + rs.getInt("Codigo") + "'";
                            try (ResultSet rss = stm.executeQuery(sql)){
                                if(rss.next()) {
                                    m = new CombustaoDiesel(key,tipo,oleo,filtroOleo,combustivel,arMotor,conversor,bateriaA,rss.getString("Velas_Incandescencia"),rss.getString("Filtro_Particulas"));
                                }
                                else {  //exception
                                }
                            }

                          }

                    else if (tipo == 2 || tipo == 5) {
                           String sql = "SELECT * FROM combustaoGasolina WHERE Codigo='" + rs.getInt("Codigo") + "'";
                            try (ResultSet rss = stm.executeQuery(sql)){
                                if(rss.next()) {
                                    m = new CombustaoGasolina(key,tipo,oleo,filtroOleo,combustivel,arMotor,conversor,bateriaA,rss.getString("Velas_Ignicao"),rss.getString("Valvula_Aceleracao"));
                                }
                                else {  //exception
                                }
                            }

                                   }
                                else {
                                    //exception
                                }   }
                            else{
                                //exception
                            }}
                        //catch
                        return m;
                    }


public void putMotorCombustao( Motor m , int tipo) throws SQLException{
    MotorCombustao mc = (MotorCombustao) m;
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()) {

                    stm.executeUpdate(
                        "INSERT INTO motoresCombustao " +
                                    "VALUES ('"+ mc.getCodigo()+ "', '" +
                                                mc.getOleo()+ "', '" +
                                                mc.getFiltrosOleo()+ "', '" +
                                                mc.getCombustivel()+ "', '" +
                                                mc.getArMotor()+ "', '" +
                                                mc.getConversor()+ "', '" +
                                                mc.getBateriaArranque()+ "') " +
"ON DUPLICATE KEY UPDATE Oleo=Values(Oleo), Filtro_Oleo=Values(Filtro_Oleo), Combustivel=Values(Combustivel), Ar_do_Motor=Values(Ar_do_Motor), Conversor=Values(Conversor), Bateria_Arranque=Values(Bateria_Arranque)");

            if (tipo == 1 || tipo == 4){
                CombustaoDiesel cd = (CombustaoDiesel) m;
                stm.executeUpdate(
                        "INSERT INTO combustaoDiesel " +
                                    "VALUES ('"+ cd.getCodigo()+ "', '" +
                                                cd.getVelas_Inc()+ "', '" +
                                                cd.getFiltro_Particulas()+ "') " +
                        "ON DUPLICATE KEY UPDATE Velas_Incandescencia=Values(Velas_Incandescencia), Filtro_Particulas=Values(Filtro_Particulas)");

            } else if (tipo == 2 || tipo == 5) {
                CombustaoGasolina cg = (CombustaoGasolina) m;
                stm.executeUpdate(
                        "INSERT INTO combustaoGasolina " +
                                    "VALUES ('"+ cg.getCodigo()+ "', '" +
                                                cg.getVelas_Ignicao()+ "', '" +
                                                cg.getValvulaAceleracao()+ "') " +
                        "ON DUPLICATE KEY UPDATE Velas_Ignicao=Values(Velas_Ignicao), Valvula_Aceleracao=Values(Valvula_Aceleracao)");
            }
}
}

public void putMotorEletrico( Motor m) throws SQLException{
    MotorEletrico me = (MotorEletrico) m;
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()) {

                    stm.executeUpdate(
                        "INSERT INTO motoresEletricos " +
                                    "VALUES ('"+ me.getCodigo()+ "', '" +
                                                me.getBateria()+ "') " +
                        "ON DUPLICATE KEY UPDATE Bateria=Values(Bateria)");

}
}

public void putMotorHibrido( Motor m , int tipo) throws SQLException{
    MotorHibrido mh = (MotorHibrido) m;
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()) {

                    stm.executeUpdate(
                        "INSERT INTO motoresHibridos " +
                                    "VALUES ('"+ mh.getCodigo()+ "', '" +
                                                mh.getMotorEletrico().getCodigo()+ "', '" +
                                                mh.getMotorCombustao().getCodigo()+ "') " +
                    "ON DUPLICATE KEY UPDATE Motor_Eletrico=Values(Motor_Eletrico), Motor_Combustao=Values(Motor_Combustao)");

                    putMotorCombustao(mh.getMotorCombustao(),tipo);
                    putMotorEletrico(mh.getMotorEletrico());
        }
    }

}