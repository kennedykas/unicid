package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.unicid.bean.Aluno;
import br.edu.unicid.bean.Professor;
import br.edu.unicid.util.TransactionManager;

public class AlunoDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	// CHECA SE ALUNO EXISTE 
	public boolean existe(int codigo) {
	    TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
			
	    	ps = connection.prepareStatement("SELECT cpf FROM usuario WHERE cpf=?");
			ps.setInt(1, codigo);
			rs = ps.executeQuery();
			
			return (rs.next()) ? true : false;
	    });
	}
	
	// EXCLUIR
	public boolean excluir(int codigo) {
	    TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
	    	ps = connection.prepareStatement("DELETE FROM aluno WHERE codigo=?");
			ps.setInt(1, codigo);
			
			return (ps.execute()) ? true : false;
	    });
	}
		
	// SAVE
	public boolean salvar(Aluno aluno) {
    	TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {
	    	
    		ps = connection.prepareStatement(
    				"INSERT INTO aluno (codigo, codCurso, rgm, nome, email, senha, cpf, celular, data) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt   (1, 0); 
			ps.setInt   (2, aluno.getCodCurso()); 
			ps.setInt   (3, aluno.getRgm()); 
			ps.setString(4, aluno.getNome());
			ps.setString(5, aluno.getEmail()); 
			ps.setString(6, aluno.getSenha());
			ps.setString(7, aluno.getCpf());
			ps.setString(8, aluno.getCelular());
			ps.setString(9, aluno.getData());
			
			return (ps.executeUpdate() > 0) ? true : false;
	    });
	}
	
	public boolean updateStudent(Aluno aluno) {
    	TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {
	    	
    		ps = connection.prepareStatement(
    				"UPDATE aluno SET codCurso=?, rgm=?, nome=?, email=?, senha=?, cpf=?, celular=? WHERE codigo=?");
 
			ps.setInt   (1, aluno.getCodCurso()); 
			ps.setInt   (2, aluno.getRgm()); 
			ps.setString(3, aluno.getNome());
			ps.setString(4, aluno.getEmail()); 
			ps.setString(5, aluno.getSenha());
			ps.setString(6, aluno.getCpf());
			ps.setString(7, aluno.getCelular());
			ps.setInt   (8, aluno.getCodigo());
			
			return (ps.executeUpdate() > 0) ? true : false;
	    });
	}
		
	// LOGIN
	public String login(Aluno aluno) {
		TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {
			
    		ps = connection.prepareStatement("SELECT codigo, codCurso, rgm, nome, email, senha, cpf, celular, data, ativo FROM aluno WHERE rgm=? and senha=?");
			ps.setInt(1, aluno.getRgm());
			ps.setString(2, aluno.getSenha());
			rs = ps.executeQuery();
			if (rs.next()) {
				aluno.setCodigo  (rs.getInt(1));
				aluno.setCodCurso(rs.getInt(2));
				aluno.setRgm     (rs.getInt(3));
				aluno.setNome    (rs.getString(4));
				aluno.setEmail   (rs.getString(5));
				aluno.setSenha   (rs.getString(6));
				aluno.setCpf     (rs.getString(7));
				aluno.setCelular (rs.getString(8));
				aluno.setData    (rs.getString(9));
				
				return (!rs.getBoolean(10)) ? "unverified" : "ok";
			
			} else {
				ps = connection.prepareStatement("SELECT rgm FROM aluno WHERE rgm=?");
				ps.setInt(1, aluno.getRgm());
				rs = ps.executeQuery();
				
				return (rs.next()) ? "senha" : "rgm"; 
			}
		}); 
	}
	
	// RECUPERAR SENHA
	public String recuperarSenha(Aluno aluno) {
		TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {
		
    		ps = connection.prepareStatement("SELECT rgm, nome, email, senha FROM aluno WHERE rgm=? AND nome=?");
			ps.setInt(1, aluno.getRgm());
			ps.setString(2, aluno.getNome());
			rs = ps.executeQuery();
			if (rs.next()) {
				aluno.setRgm(rs.getInt(1));
				aluno.setNome(rs.getString(2));
				aluno.setEmail(rs.getString(3));
				aluno.setSenha(rs.getString(4));
			
				return "ok";
			} else {
				ps = connection.prepareStatement("SELECT rgm FROM aluno WHERE rgm=?");
				ps.setInt(1, aluno.getRgm());
				rs = ps.executeQuery();
	
				return (rs.next()) ? "nome" : "rgm"; 
			}
		});
	}
	
	// AUTENTICAR
	public boolean autentica(Aluno aluno) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
	    	ps = connection.prepareStatement("UPDATE aluno SET ativo=? WHERE data=? AND email=?");
			ps.setBoolean(1, true);
			ps.setString(2, aluno.getData()); 
			ps.setString(3, aluno.getEmail());
			
			return (ps.executeUpdate() > 0) ? true : false;
	    });
	}
			
	// RETURN NAME
	public String getNome(int codigo) {
		TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {

    		ps = connection.prepareStatement("SELECT codigo, nome FROM aluno WHERE codigo=?");
			ps.setInt(1, codigo);
			rs = ps.executeQuery();
			
			return (rs.next()) ? rs.getString(2) : "Nome n�o encontrado";  			
		});
	}

	// RETURN RGM
	public int getRgm(int codigo) {
		TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {
		
    		ps = connection.prepareStatement("SELECT codigo, rgm FROM aluno WHERE codigo=?");
			ps.setInt(1, codigo);
			rs = ps.executeQuery();
			
			return (rs.next()) ? rs.getInt(2) : 0;
		});
	}
	
	// ALTERAR
	public boolean alterar(Aluno aluno) {
	    TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
	    	ps = connection.prepareStatement("UPDATE aluno SET rgm=?, nome=?, email=?, senha=? WHERE codigo=?");
			ps.setInt(1, aluno.getRgm()); 
			ps.setString(2, aluno.getNome());
			ps.setString(3, aluno.getEmail());
			ps.setString(4, aluno.getSenha());
			ps.setInt(5, aluno.getCodigo());
			
			return (ps.executeUpdate() > 0) ? true : false;
	    });
	}

}
