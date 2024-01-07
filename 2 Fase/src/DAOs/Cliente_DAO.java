package src.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import src.SubClientes.Cliente;
import src.SubViaturas.Viatura;

public class Cliente_DAO implements Map<String,Cliente>{
    
    private static Cliente_DAO singleton = null;

    private Cliente_DAO() {
        try (
            Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
            Statement stm = con.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS clientes (" +
                    "Nif varchar(45) NOT NULL PRIMARY KEY," +
                    "Nome varchar(45) DEFAULT ''," +
                    "Contacto varchar(45) DEFAULT ''," +
					"ViaturaID varchar(45) DEFAULT ''," +
                    "FOREIGN KEY(ViaturaID) REFERENCES viaturas(Matricula));";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            
            e.printStackTrace();// Se der erro a criar a tabela
            throw new NullPointerException(e.getMessage());
        }
    }

    public static Cliente_DAO getInstance() {
		if (Cliente_DAO.singleton == null) {
			Cliente_DAO.singleton = new Cliente_DAO();
		}
		return Cliente_DAO.singleton;
	}

    public static void buildInstance(){
		if (Cliente_DAO.singleton == null) {
			Cliente_DAO.singleton = new Cliente_DAO();
		}
	}

@Override
public void clear() {
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement()) {
			stm.executeUpdate("UPDATE clientes SET Cliente=NULL");
			stm.executeUpdate("TRUNCATE clientes");
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
					 stm.executeQuery("SELECT Nif FROM clientes WHERE Nif='"+key.toString()+"'")) {
			n = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return n;
}

@Override
public boolean containsValue(Object value) {
    Cliente a = (Cliente) value;
    return this.containsKey(a.getNIF());
}

@Override
public Set<Entry<String, Cliente>> entrySet() {
    
    throw new UnsupportedOperationException("Unimplemented method 'entrySet'");
}

@Override
public Cliente get(Object key) {
    Cliente c = null;
    try (Connection conn = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM clientes WHERE Nif='"+key.toString()+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                Viatura v = Viatura_DAO.getInstance().get(rs.getString("ViaturaID"));
                c = new Cliente(rs.getString("Nif"), rs.getString("Nome"), rs.getString("Contacto"),v);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
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
             ResultSet rs = stm.executeQuery("SELECT Nif FROM clientes")) { 
             while (rs.next()) {
                String aux = rs.getString("Nif"); 
                res.add(aux);                                 
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
}

@Override
public Cliente put(String arg0, Cliente arg1) {
        Cliente res = null;
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()) {
                try(
            PreparedStatement pstm = con.prepareStatement("INSERT INTO clientes(Nif,Nome,Contacto,ViaturaID)" 
            + "VALUES (?,?,?,?)")) {
                    pstm.setString(1,arg1.getNIF());
                    pstm.setString(2,arg1.getNome());
                    pstm.setString(3,arg1.getContacto());
                    pstm.setString(4, arg1.getViatura().getMatricula());
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
public void putAll(Map<? extends String, ? extends Cliente> u) {
    for(Cliente util : u.values()) {
        this.put(util.getNIF(), util);
    }
}

@Override
public Cliente remove(Object key) {

        Cliente a = this.get(key);
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()){
                stm.executeUpdate("DELETE FROM clientes WHERE Nif='"+key+"'");
                Viatura_DAO.getInstance().remove(a.getViatura().getMatricula());//NAO TENHO A CERTEZA
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
			 ResultSet rs = stm.executeQuery("SELECT count(*) FROM clientes")) {
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
public Collection<Cliente> values() {

    Collection<Cliente> res = new HashSet<>();
		try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement();
			 ResultSet rs = stm.executeQuery("SELECT Nif FROM clientes")) { 
			while (rs.next()) {
				String idt = rs.getString("Nif"); 
				Cliente a = this.get(idt);                    
				res.add(a);                
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return res;
}  
 
}


