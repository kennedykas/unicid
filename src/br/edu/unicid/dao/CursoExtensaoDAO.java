package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.unicid.bean.CursoExtensao;
import br.edu.unicid.util.TransactionManager;

public class CursoExtensaoDAO {
	
	private PreparedStatement ps;
	private ResultSet rs;

	public boolean salvar(CursoExtensao cursoExtensao) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
	    	String SQL = "INSERT INTO curso_extensao (codigo, curso, nome, email, cpf, celular, data) values (?, ?, ?, ?, ?, ?, ?)"; 
	    	
			ps = connection.prepareStatement(SQL);
			ps.setInt   (1, 0); //code
			ps.setString(2, cursoExtensao.getNomeCurso()); 
			ps.setString(3, cursoExtensao.getNome());
			ps.setString(4, cursoExtensao.getEmail());
			ps.setString(5, cursoExtensao.getCpf());
			ps.setString(6, cursoExtensao.getCelular());
			ps.setString(7, cursoExtensao.getData());
			
			return (ps.executeUpdate() > 0) ? Boolean.TRUE : Boolean.FALSE; 
		});
	}
	
	public List<CursoExtensao> obterTodosCursosExtensao() {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
			
	    	ps = connection.prepareStatement("SELECT codigo, curso, nome, email, cpf, celular, data FROM curso_extensao");
			
			rs = ps.executeQuery();
			
			List<CursoExtensao> list = new ArrayList<>();
			
			while(rs.next()) {
				int    codigo  = rs.getInt(1);
				String curso   = rs.getString(2);
				String nome    = rs.getString(3);
				String email   = rs.getString(4);
				String cpf     = rs.getString(5);
				String celular = rs.getString(6);
				String data    = rs.getString(7);

				list.add(new CursoExtensao(codigo, curso, nome, email, cpf, celular, data));
			}
			return list;
		});
	}
	
	// DELETE
	public boolean excluir(int codigo) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("DELETE FROM curso_extensao WHERE codigo=?");
			ps.setInt(1, codigo);
			
			return (ps.execute()) ? Boolean.TRUE : Boolean.FALSE;
		});
	}
}
