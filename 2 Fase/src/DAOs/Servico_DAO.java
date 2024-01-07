package src.DAOs;

import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import src.SubPostos.Servico;
import src.SubViaturas.Viatura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Servico_DAO implements Map< String , Servico >{

    private static Servico_DAO singleton = null;

    private Servico_DAO() {
        try (
            Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
            Statement stm = con.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS servicos (" +
                    "Id varchar(45) NOT NULL PRIMARY KEY," +
                    "Tipo int DEFAULT 0," +
					"Descricao varchar(45) DEFAULT ''," +
                    "Notificacao BIT NOT NULL," +
					"ViaturaID varchar(45) DEFAULT ''," +
                    "FOREIGN KEY (ViaturaID) REFERENCES viaturas(Matricula));";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS servicos_postoTrabalho (" +
                    "IdServico varchar(45) NOT NULL PRIMARY KEY," +
                    "NomePosto varchar(45) DEFAULT ''," +
                    "FOREIGN KEY (IdServico) REFERENCES servicos(Id)," +
                    "FOREIGN KEY (NomePosto) REFERENCES postos(Nome));";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            
            e.printStackTrace();// Se der erro a criar a tabela
            throw new NullPointerException(e.getMessage());
        }
    }

    public static Servico_DAO getInstance() {
		if (Servico_DAO.singleton == null) {
			Servico_DAO.singleton = new Servico_DAO();
		}
		return Servico_DAO.singleton;
	}

    public static void buildInstance(){
		if (Servico_DAO.singleton == null) {
			Servico_DAO.singleton = new Servico_DAO();
		}
	}

    public int nrServicosViatura( String matricula) {
        int i = 0;
		try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT count(*) FROM servicos WHERE ViaturaID='"+matricula+"'")) {
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

    public void setPosto(String idServico, String nomePosto){
        try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()) {
                try(
            PreparedStatement pstm = con.prepareStatement("INSERT INTO servicos_postoTrabalho(IdServico,NomePosto)" 
            + "VALUES (?,?)")) {
                    pstm.setString(1,idServico);
                    pstm.setString(2,nomePosto);
                    pstm.execute();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
    }

@Override
public void clear() {
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement()) {
			stm.executeUpdate("UPDATE servicos SET Servico=NULL");
			stm.executeUpdate("TRUNCATE servicos");
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
					 stm.executeQuery("SELECT Id FROM servicos WHERE Id='"+key.toString()+"'")) {
			n = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return n;
}

@Override
public boolean containsValue(Object value) {
    Servico a = (Servico) value;
    return this.containsKey(a.getID());
}

@Override
public Set<Entry<String, Servico>> entrySet() {
    
    throw new UnsupportedOperationException("Unimplemented method 'entrySet'");
}

@Override
public Servico get(Object key) {
    Servico s = null;
		try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT * FROM servicos WHERE Id='"+key+"'")) {
                if (rs.next()) {
                Viatura v = new Viatura(Viatura_DAO.getInstance().get(rs.getString("ViaturaID")));
                    
                    s = new Servico(rs.getString("Id"),rs.getInt("Tipo"), rs.getString("Descricao"),rs.getBoolean("Notificacao"),v);
                } 
            }
      catch (SQLException e) {
                
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
            return s;
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
             ResultSet rs = stm.executeQuery("SELECT Id FROM servicos")) { 
             while (rs.next()) {
                String aux = rs.getString("Id"); 
                res.add(aux);                                 
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
}

@Override
public Servico put(String arg0, Servico arg1) {
        Servico res = null;
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()) {
                try(
            PreparedStatement pstm = con.prepareStatement("INSERT INTO servicos(Id,Tipo,Descricao,Notificacao,ViaturaID)" 
            + "VALUES (?,?,?,?,?)")) {
                    pstm.setString(1,arg1.getID());
                    pstm.setInt(2,arg1.getTipo());
                    pstm.setString(3,arg1.getDescricao());
                    pstm.setBoolean(4,arg1.getNotificacao());
                    pstm.setString(5,arg1.getViatura().getMatricula());
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
public void putAll(Map<? extends String, ? extends Servico> m) {
    for(Servico s : m.values()) {
        this.put(s.getID(), s);
    }
}

@Override
public Servico remove(Object key) {

        Servico a = this.get(key);
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()){
                stm.executeUpdate("DELETE FROM servicos_postoTrabalho WHERE IdServico='"+key+"'");
                stm.executeUpdate("DELETE FROM servicos WHERE Id='"+key+"'");
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
			 ResultSet rs = stm.executeQuery("SELECT count(*) FROM servicos")) {
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
public Collection<Servico> values() {

    Collection<Servico> res = new HashSet<>();
		try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT Id FROM servicos")) { 
			while (rs.next()) {
				String idt = rs.getString("Id"); 
				Servico a = this.get(idt);                    
				res.add(a);                
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return res;
}  
 
}
