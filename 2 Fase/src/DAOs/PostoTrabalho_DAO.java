package src.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import src.SubPostos.Mecanico;
import src.SubPostos.PostoTrabalho;
import src.SubPostos.Servico;

public class PostoTrabalho_DAO implements Map<String,PostoTrabalho>{
    private static PostoTrabalho_DAO singleton = null;

    private PostoTrabalho_DAO() {
        try (
            Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
            Statement stm = con.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS postos (" +
                    "Nome varchar(45) NOT NULL PRIMARY KEY," +
                    "Especializacao varchar(45) DEFAULT ''," +
					"Mecanico varchar(45) DEFAULT ''," +
                    "FOREIGN KEY (Mecanico) REFERENCES mecanicos(Id));";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            
            e.printStackTrace();// Se der erro a criar a tabela
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PostoTrabalho_DAO getInstance() {
		if (PostoTrabalho_DAO.singleton == null) {
			PostoTrabalho_DAO.singleton = new PostoTrabalho_DAO();
		}
		return PostoTrabalho_DAO.singleton;
	}

    public static void buildInstance(){
		if (PostoTrabalho_DAO.singleton == null) {
			PostoTrabalho_DAO.singleton = new PostoTrabalho_DAO();
		}
	}

@Override
public void clear() {
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
			 Statement stm = con.createStatement()) {
			stm.executeUpdate("UPDATE postos SET Posto=NULL");
			stm.executeUpdate("TRUNCATE postos");
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
					 stm.executeQuery("SELECT Nome FROM postos WHERE Nome='"+key.toString()+"'")) {
			n = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return n;
}

@Override
public boolean containsValue(Object value) {
    PostoTrabalho a = (PostoTrabalho) value;
    return this.containsKey(a.getNome());
}

@Override
public Set<Entry<String, PostoTrabalho>> entrySet() {
    
    throw new UnsupportedOperationException("Unimplemented method 'entrySet'");
}

@Override
public PostoTrabalho get(Object key) {
    PostoTrabalho p = null;
    try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
         PreparedStatement stm = con.prepareStatement("SELECT * FROM postos WHERE Nome=?")) {
        stm.setString(1, (String) key);
        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                Mecanico m = Mecanico_DAO.getInstance().get(rs.getString("Mecanico"));
                Servico s = null;
                List<Servico> listServ = new ArrayList<>();
                String sql = "SELECT * FROM servicos_postoTrabalho WHERE NomePosto=?";
                try (PreparedStatement servStm = con.prepareStatement(sql)) {
                    servStm.setString(1, (String) key);
                    try (ResultSet res = servStm.executeQuery()) {
                        while (res.next()) {
                            s = Servico_DAO.getInstance().get(res.getString("IdServico"));
                            listServ.add(s);
                        }
                    }
                }
                p = new PostoTrabalho(rs.getString("Nome"), rs.getString("Especializacao"), m, listServ);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException(e.getMessage());
    }
    return p;
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
             ResultSet rs = stm.executeQuery("SELECT Nome FROM postos")) { 
             while (rs.next()) {
                String aux = rs.getString("Nome"); 
                res.add(aux);                                 
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
}

@Override
public PostoTrabalho put(String arg0, PostoTrabalho arg1) {
        PostoTrabalho res = null;
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()) {
                try(
            PreparedStatement pstm = con.prepareStatement("INSERT INTO postos(Nome,Especializacao,Mecanico)" 
            + "VALUES (?,?,?)")) {
                    pstm.setString(1,arg1.getNome());
                    pstm.setString(2,arg1.getEspecializacao());
                    pstm.setString(3,arg1.getMecanico().getID());
                    pstm.execute();
                }

                List<Servico> aux = arg1.getServicos();
               String sql = "INSERT INTO servicos_postoTrabalho VALUES (?,?);";
            try(PreparedStatement spStm = con.prepareStatement(sql)){
                for(int i=0;i< aux.size();++i){
                    spStm.setString(1, aux.get(i).getID());
                    spStm.setString(2, arg1.getNome());
                    spStm.execute();
                }
            }

                res = arg1;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
            return res;
}

@Override
public void putAll(Map<? extends String, ? extends PostoTrabalho> m) {
    for(PostoTrabalho pt : m.values()) {
        this.put(pt.getNome(), pt);
    }
}

@Override
public PostoTrabalho remove(Object key) {

        PostoTrabalho a = this.get(key);
            try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
                Statement stm = con.createStatement()){
                stm.executeUpdate("DELETE FROM postos WHERE Nome='"+key+"'");
                stm.executeUpdate("DELETE FROM servicos_postoTrabalho WHERE NomePosto='"+key+"'");
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
			 ResultSet rs = stm.executeQuery("SELECT count(*) FROM postos")) {
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
public Collection<PostoTrabalho> values() {

    Collection<PostoTrabalho> res = new HashSet<>();
        try (Connection con = DriverManager.getConnection(dadosDAO.URL, dadosDAO.USERNAME, dadosDAO.PASSWORD);
    PreparedStatement stm = con.prepareStatement("SELECT Nome FROM postos");
    ResultSet rs = stm.executeQuery()) {
			while (rs.next()) {
				String idt = rs.getString("Nome"); 
				PostoTrabalho a = this.get(idt) ;                  
				res.add(a);                
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
		return res;
}  
 
}


