package br.edu.unicid.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

import br.edu.unicid.bean.Grupo;
import br.edu.unicid.util.TransactionManager;

public class GrupoDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	// CONSTRUTOR
	public GrupoDAO() {}

	// SAVE
	public boolean salvar(Grupo grupo) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {

			this.ps = connection.prepareStatement(
					"INSERT INTO grupos (codigo, nomeGrupo, nomeArquivo, arquivo, observacao, data, caRepresentante, caIntegrantes, senhaGrupo) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			this.ps.setInt   (1, 0); //code
			this.ps.setString(2, grupo.getNomeGrupo());     //nomeEquipe
			this.ps.setString(3, grupo.getNomeArquivo());   //nomeArquivo
			this.ps.setBytes (4, grupo.getBytes());         //file
			this.ps.setString(5, grupo.getObservacao());    //obs
			this.ps.setDate  (6, new java.sql.Date(System.currentTimeMillis())); //data
			this.ps.setInt   (7, grupo.getRepresentante()); //ca do representante
			this.ps.setString(8, grupo.getIntegrantes());   //ca integrantes
			this.ps.setString(9, grupo.getSenhaGrupo());    //senha
			
			return (this.ps.executeUpdate() > 0) ? true : false;
	    });
	}
	
	// LOGIN
	public String login(Grupo grupo) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {

	    	this.ps = connection.prepareStatement(
					"SELECT codigo, nomeArquivo, arquivo, observacao, data, caRepresentante, caIntegrantes FROM grupos WHERE nomeGrupo=? and senhaGrupo=?");
	    	this.ps.setString(1, grupo.getNomeGrupo());
	    	this.ps.setString(2, grupo.getSenhaGrupo());
			
	    	this.rs = this.ps.executeQuery();
			
			if (this.rs.next()) {			
				
				grupo.setCodigo       (this.rs.getInt   (1));
				grupo.setNomeArquivo  (this.rs.getString(2));
				grupo.setBytes        (this.rs.getBytes (3));
				grupo.setObservacao   (this.rs.getString(4));
				grupo.setData         (this.rs.getDate  (5));
				grupo.setRepresentante(this.rs.getInt   (6));
				grupo.setIntegrantes  (this.rs.getString(7));
				
				return "ok";
			} else {
				
				this.ps = connection.prepareStatement("SELECT codigo FROM grupos WHERE nomeGrupo=?");
				this.ps.setString(1, grupo.getNomeGrupo());
				
				this.rs = this.ps.executeQuery();
				
				return (this.rs.next()) ? "senha" : "nome";
			}
		});
	}

	// LIST ALL GROUPS
	public List<Grupo> todosGrupos() {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {

	    	this.ps = connection.prepareStatement("SELECT * FROM grupos");
	    	this.rs = ps.executeQuery();
			
			List<Grupo> list = new ArrayList<Grupo>();
			while(rs.next()) {
			
				int    codigo        = this.rs.getInt   (1);
				String nomeGrupo     = this.rs.getString(2);
				String nomeArquivo   = this.rs.getString(3);
				byte[] arquivo       = this.rs.getBytes (4);
				String observacao    = this.rs.getString(5);
				Date   data          = this.rs.getDate  (6);
				int    representante = this.rs.getInt   (7);
				String integrantes   = this.rs.getString(8);
				String senhaGrupo    = this.rs.getString(9);
							
				list.add(
					new Grupo(
							codigo, 
							nomeGrupo, 
							nomeArquivo, 
							arquivo, 
							observacao, 
							data, 
							representante, 
							integrantes, 
							senhaGrupo
					)
				);
			}
			return list;
		});
	}
	
	// ALTERAR
	public boolean alterar(Grupo grupo) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {

	    	this.ps = connection.prepareStatement(
					"UPDATE grupos SET nomeArquivo=?, arquivo=?, observacao=?, data=? WHERE codigo=?");
	    	this.ps.setString(1, grupo.getNomeArquivo()); //nomeArquivo
	    	this.ps.setBytes (2, grupo.getBytes());       //file
	    	this.ps.setString(3, grupo.getObservacao());  //obs
	    	this.ps.setDate  (4, new java.sql.Date(System.currentTimeMillis())); //data
	    	this.ps.setInt   (5, grupo.getCodigo());
			
			return (this.ps.executeUpdate() > 0) ? true : false;
		});
	}
}
