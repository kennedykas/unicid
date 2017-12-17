package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.unicid.bean.Entrevista;
import br.edu.unicid.util.TransactionManager;

public class EntrevistaDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	public boolean salvar(Entrevista entrevista) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
	    	String sql = "INSERT INTO entrevista (codigo, curso, nome, email, cpf, celular, data) values (?, ?, ?, ?, ?, ?, ?)"; 
	    	
			ps = connection.prepareStatement(sql);
			ps.setInt   (1, 0); //code
			ps.setString(2, entrevista.getNomeCurso()); 
			ps.setString(3, entrevista.getNome());
			ps.setString(4, entrevista.getEmail());
			ps.setString(5, entrevista.getCpf());
			ps.setString(6, entrevista.getCelular());
			ps.setString(7, entrevista.getData());
			
			return (ps.executeUpdate() > 0) ? Boolean.TRUE : Boolean.FALSE; 
		});
	}

	public List<Entrevista> obterTodasEntrevistas() {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
			
	    	ps = connection.prepareStatement("SELECT codigo, curso, nome, email, cpf, celular, data FROM entrevista");
			
			rs = ps.executeQuery();
			
			List<Entrevista> list = new ArrayList<>();
			
			while(rs.next()) {
				int    codigo  = rs.getInt(1);
				String curso   = rs.getString(2);
				String nome    = rs.getString(3);
				String email   = rs.getString(4);
				String cpf     = rs.getString(5);
				String celular = rs.getString(6);
				String data    = rs.getString(7);

				list.add(new Entrevista(codigo, curso, nome, email, cpf, celular, data));
			}
			return list;
		});
	}
	
	// DELETE
	public boolean excluir(int codigo) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("DELETE FROM entrevista WHERE codigo=?");
			ps.setInt(1, codigo);
			
			return (ps.execute()) ? Boolean.TRUE : Boolean.FALSE;
		});
	}
}
