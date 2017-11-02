package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.unicid.bean.Professor;
import br.edu.unicid.util.TransactionManager;

public class ProfessorDAO {

	private PreparedStatement ps;
	private ResultSet rs;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");

	// SAVE
	public boolean salvar(Professor professor) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {

			ps = connection.prepareStatement(
					"INSERT INTO professor (codigo, nome, email, senha, data) values (?, ?, ?, ?, ?)");
			ps.setInt   (1, 0); //code
			ps.setString(2, professor.getNomeProfessor()); //nomeEquipe
			ps.setString(3, professor.getEmailProfessor()); //nomeArquivo
			ps.setString(4, professor.getSenhaProfessor()); //obs
			Date date = new Date();
			professor.setData(dateFormat.format(date)); // Setando a data no bean
			ps.setString(5, professor.getData());
			
			return (ps.executeUpdate() > 0) ? true : false;
		});
	}
	
	// LOGIN
	public String login(Professor professor) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codigo, nome, data, ativo FROM professor WHERE email=? and senha=?");
			ps.setString(1, professor.getEmailProfessor());
			ps.setString(2, professor.getSenhaProfessor());
			rs = ps.executeQuery();
			if (rs.next()) {
				professor.setCodigo(rs.getInt(1));
				professor.setNomeProfessor(rs.getString(2));
				professor.setData(rs.getString(3));
				
				return (rs.getBoolean(4) == false) ? "unverified" : "ok";
			} else {
				ps = connection.prepareStatement("SELECT codigo FROM professor WHERE email=?");
				ps.setString(1, professor.getEmailProfessor());
				rs = ps.executeQuery();

				return (rs.next()) ? "senha" : "email"; 
			}				
		});
	}
	
	// AUTENTICAR
	public boolean autenticar(Professor professor) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("UPDATE professor SET ativo=? WHERE email=? AND data=?");
			ps.setBoolean(1, true);
			ps.setString(2, professor.getEmailProfessor());
			ps.setString(3, professor.getData()); 

			return (ps.executeUpdate() > 0) ? true : false;
		});
	}
	
	// RECUPERAR SENHA
	public String recuperarSenha(Professor professor) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("SELECT nome, email, senha FROM professor WHERE nome=?");
			ps.setString(1, professor.getNomeProfessor());
			rs = ps.executeQuery();
			if (rs.next()) {
				professor.setNomeProfessor(rs.getString(1));
				professor.setEmailProfessor(rs.getString(2));
				professor.setSenhaProfessor(rs.getString(3));
				return "ok";
			} else 
				return "nome";
		});
	}
		
	// RETORNA LISTA COM CODIGO E NOME DE TODOS OS PROFESSORES ATIVOS
	public List<Professor> professores() {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("SELECT codigo, nome FROM professor WHERE ativo=?");
			ps.setInt(1, 1);
			rs = ps.executeQuery();
			
			List<Professor> list = new ArrayList<Professor>();
			
			while(rs.next()) {
				int codigo = rs.getInt(1);
				String nomeProfessor = rs.getString(2);
				list.add(new Professor(codigo, nomeProfessor));
			}
			return list;
		});
	}
	
}
