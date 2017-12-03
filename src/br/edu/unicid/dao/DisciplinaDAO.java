package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.edu.unicid.bean.Disciplina;
import br.edu.unicid.util.TransactionManager;

public class DisciplinaDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	// SAVE
	public int salvar(Disciplina disciplina) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
	    	String SQL = "INSERT INTO disciplina (codigo, codProfessor, nomeDisciplina, observacoes) values (?, ?, ?, ?)"; 
	    	
			ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setInt   (1, 0); //code
			ps.setInt   (2, disciplina.getCodProfessor()); 
			ps.setString(3, disciplina.getNomeDisciplina());
			ps.setString(4, disciplina.getObservacoes()); 
			
			if(ps.executeUpdate() > 0) // CASO A DISCIPLINA TENHA SIDO SALVA
				rs = ps.getGeneratedKeys(); 

			return (rs.next()) ? rs.getInt(1) : 0; // RETORNA A CHAVE PRIMARIA DA DISCIPLINA
		});
	}
	
	// DELETE
	public boolean excluir(int codigo) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("DELETE FROM disciplina WHERE codigo=?");
			ps.setInt(1, codigo);
			
			return (ps.execute()) ? true : false;
		});
	}

	// LISTA TODAS DISCIPLINAS DO PROFESSOR
	public List<Disciplina> todasDisciplinas(int codigoProfessor) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
			
	    	ps = connection.prepareStatement(
	    			"SELECT codigo, codProfessor, nomeDisciplina, observacoes FROM disciplina WHERE codProfessor=? GROUP BY codigo");
			ps.setInt(1, codigoProfessor);
			rs = ps.executeQuery();
			
			List<Disciplina> list = new ArrayList<Disciplina>();
			
			while(rs.next()) {
				int codigo = rs.getInt(1);
				int codProfessor = rs.getInt(2);
				String nomeDisciplina = rs.getString(3);
				String observacoes = rs.getString(4);
				list.add(new Disciplina(codigo, codProfessor, nomeDisciplina, observacoes));
			}
			return list;
		});
	}
	
	// ALTERAR
	public boolean alterar(Disciplina disciplina) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
						
			ps = connection.prepareStatement(
					"UPDATE disciplina SET nomeDisciplina=?, observacoes=? WHERE codigo=?");
			ps.setString(1, disciplina.getNomeDisciplina()); 
			ps.setString(2, disciplina.getObservacoes()); 
			ps.setInt   (3, disciplina.getCodigo()); 
			
			return (ps.executeUpdate() > 0) ? true : false;
		});
	}
	
	// RETURN JUST NAME AND CODE OF DISCIPLINA
	public List<Disciplina> disciplinasPeloCodProfessor(int codProfessor) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
			
	    	ps = connection.prepareStatement(
	    			"SELECT codigo, codProfessor, nomeDisciplina FROM disciplina WHERE codProfessor=? GROUP BY codigo");
			ps.setInt(1, codProfessor);
			rs = ps.executeQuery();
			
			List<Disciplina> list = new ArrayList<Disciplina>();
			
			while(rs.next()) {
				int codigo = rs.getInt(1);
				String nomeDisciplina = rs.getString(3);
				list.add(new Disciplina(codigo, nomeDisciplina));
			}
			return list;
		});
	}
	
	// RETURN NAME OF DISCIPLINA
	public String getNomeDisciplina(int codigoDisciplina) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
			
			ps = connection.prepareStatement(
					"SELECT codigo, nomeDisciplina FROM disciplina WHERE codigo=?");
			ps.setInt(1, codigoDisciplina);
			rs = ps.executeQuery();

			return (rs.next()) ? rs.getString(2) : "";
	    });
	}
}
