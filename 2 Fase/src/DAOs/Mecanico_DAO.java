package src.DAOs;

import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import src.SubPostos.Mecanico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mecanico_DAO implements Map<String, Mecanico>{

    private static Mecanico_DAO singleton = null;

    private Mecanico_DAO() {
        try (
            Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
            Statement stm = con.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS mecanicos (" +
                    "Id varchar(45) NOT NULL PRIMARY KEY," +
                    "Nome varchar(45) DEFAULT ''," +
					"Password varchar(45) DEFAULT '');";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            
            e.printStackTrace();// Se der erro a criar a tabela
            throw new NullPointerException(e.getMessage());
        }
    }

    public static Mecanico_DAO getInstance() {
		if (Mecanico_DAO.singleton == null) {
			Mecanico_DAO.singleton = new Mecanico_DAO();
		}
		return Mecanico_DAO.singleton;
	}

    public static void buildInstance(){
		if (Mecanico_DAO.singleton == null) {
			Mecanico_DAO.singleton = new Mecanico_DAO();
		}
	}

@Override
public void clear() {
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement()) {
			stm.executeUpdate("UPDATE mecanicos SET Mecanico=NULL");
			stm.executeUpdate("TRUNCATE mecanicos");
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
					 stm.executeQuery("SELECT Id FROM mecanicos WHERE Id='"+key.toString()+"'")) {
			n = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return n;
}

@Override
public boolean containsValue(Object value) {
    Mecanico a = (Mecanico) value;
    return this.containsKey(a.getID());
}

@Override
public Set<Entry<String, Mecanico>> entrySet() {
    
    throw new UnsupportedOperationException("Unimplemented method 'entrySet'");
}

@Override
public Mecanico get(Object key) {
    Mecanico m = null;
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
         PreparedStatement stm = con.prepareStatement("SELECT * FROM mecanicos WHERE Id=?")) {
        stm.setString(1, (String) key);
        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                m = new Mecanico(rs.getString("Id"), rs.getString("Nome"), rs.getString("Password"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException(e.getMessage());
    }
    return m;
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
             ResultSet rs = stm.executeQuery("SELECT Id FROM mecanicos")) { 
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
public Mecanico put(String arg0, Mecanico arg1) {
        Mecanico res = null;
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()) {
                try(
            PreparedStatement pstm = con.prepareStatement("INSERT INTO mecanicos(Id,Nome,Password)" 
            + "VALUES (?,?,?)")) {
                    pstm.setString(1,arg1.getID());
                    pstm.setString(2,arg1.getNome());
                    pstm.setString(3,arg1.getPassword());
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
public void putAll(Map<? extends String, ? extends Mecanico> m) {
    for(Mecanico mec : m.values()) {
        this.put(mec.getID(), mec);
    }
}

@Override
public Mecanico remove(Object key) {

        Mecanico a = this.get(key);
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()){
                stm.executeUpdate("DELETE FROM mecanicos WHERE Id='"+key+"'");
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
			 ResultSet rs = stm.executeQuery("SELECT count(*) FROM mecanicos")) {
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
public Collection<Mecanico> values() {

    Collection<Mecanico> res = new HashSet<>();
		try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT Id FROM mecanicos")) { 
			while (rs.next()) {
				String idt = rs.getString("Id"); 
				Mecanico a = this.get(idt);                    
				res.add(a);                
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return res;
}  
 
}

