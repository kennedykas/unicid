package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.unicid.bean.Prova;
import br.edu.unicid.util.TransactionManager;

public class ProvaDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	// SAVE
	public boolean salvar(Prova prova) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
				"INSERT INTO prova (codigo, codProfessor, codDisciplina, titulo, questoes, valorTotal, valorQuestoes, tempo, data, allowAfterDate, allowMultipleAttempts) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt    (1, 0);
			ps.setInt    (2, prova.getCodProfessor());
			ps.setInt    (3, prova.getCodDisciplina()); 
			ps.setString (4, prova.getTitulo());
			ps.setString (5, prova.getQuestoes());
			ps.setFloat  (6, prova.getValorTotal());
			ps.setFloat  (7, prova.getValorQuestoes());
			ps.setInt    (8, prova.getTempo());
			ps.setString (9, prova.getData());
			ps.setBoolean(10, prova.isAllowAfterDate());
			ps.setBoolean(11, prova.isAllowMultipleAttempts());
			
			return (ps.executeUpdate() > 0) ? true : false;
		});
	}
	
	// DELETE
	public boolean excluir(int codigo) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("DELETE FROM prova WHERE codigo=?");
			ps.setInt(1, codigo);
			
			return (ps.execute()) ? true : false;
		});
	}

	// OBTEM TODAS AS PROVAS DE UM PROFESSOR
	public List<Prova> todasProvas(int codigoProfessor) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codigo, codProfessor, codDisciplina, titulo, questoes, valorTotal, valorQuestoes, tempo, data, allowAfterDate, allowMultipleAttempts FROM prova WHERE codProfessor=? GROUP BY codigo");
			ps.setInt(1, codigoProfessor);
			rs = ps.executeQuery();
	
			List<Prova> list = new ArrayList<Prova>();
			
			while(rs.next()) {
				int          codigo = rs.getInt(1);
				int    codProfessor = rs.getInt(2);
				int   codDisciplina = rs.getInt(3);
				String       titulo = rs.getString(4);
				String     questoes = rs.getString(5);
				float    valorTotal = rs.getFloat(6);
				float valorQuestoes = rs.getFloat(7);
				int           tempo = rs.getInt(8);
				String         data = rs.getString(9);
				boolean allowAfterDate = rs.getBoolean(10);
				boolean allowMultipleAttempts = rs.getBoolean(11);
				
				list.add(new Prova(codigo, codProfessor, codDisciplina, titulo, questoes, valorTotal, valorQuestoes, tempo, data, allowAfterDate, allowMultipleAttempts));
			}
			return list;
		});
	}
	
	// OBTEM TODAS AS PROVAS DE DETERMINADA(S) DISCIPLINA(S)
	public List<Prova> obterProvasPeloCodDisciplina(ArrayList<Integer> codigosDisciplinas) {
		TransactionManager txManager = new TransactionManager();
	    return (List<Prova>) txManager.doInTransactionWithReturn((connection) -> {
	    	
	    	List<Prova> provas = new ArrayList<Prova>();

	    	for(Integer i : codigosDisciplinas) {
	    		
				ps = connection.prepareStatement(
						"SELECT codigo, codProfessor, codDisciplina, titulo, questoes, valorTotal, valorQuestoes, tempo, data, allowAfterDate, allowMultipleAttempts FROM prova WHERE codDisciplina=? GROUP BY codigo");
				ps.setInt(1, i.intValue());
				rs = ps.executeQuery();
				
				while(rs.next()) {
					int          codigo = rs.getInt(1);
					int    codProfessor = rs.getInt(2);
					int   codDisciplina = rs.getInt(3);
					String       titulo = rs.getString(4);
					String     questoes = rs.getString(5);
					float    valorTotal = rs.getFloat(6);
					float valorQuestoes = rs.getFloat(7);
					int           tempo = rs.getInt(8);
					String         data = rs.getString(9);
					boolean allowAfterDate = rs.getBoolean(10);
					boolean allowMultipleAttempts = rs.getBoolean(11);
					
					provas.add(new Prova(codigo, codProfessor, codDisciplina, titulo, questoes, valorTotal, valorQuestoes, tempo, data, allowAfterDate, allowMultipleAttempts));
				}
	    	}
			return provas;
		});
	}
		
	// OBTEM TODOS OS DADOS DE UMA PROVA
	public Prova getProva(int codProva) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codigo, codProfessor, codDisciplina, titulo, questoes, valorTotal, valorQuestoes, tempo, data, allowAfterDate, allowMultipleAttempts FROM prova WHERE codigo=?");
			ps.setInt(1, codProva);
			rs = ps.executeQuery();
			if(rs.next()) {
				int          codigo = rs.getInt(1);
				int    codProfessor = rs.getInt(2);
				int   codDisciplina = rs.getInt(3);
				String       titulo = rs.getString(4);
				String     questoes = rs.getString(5);
				float    valorTotal = rs.getFloat(6);
				float valorQuestoes = rs.getFloat(7);
				int           tempo = rs.getInt(8);
				String         data = rs.getString(9);
				boolean allowAfterDate = rs.getBoolean(10);
				boolean allowMultipleAttempts = rs.getBoolean(11);
				
				return new Prova(codigo, codProfessor, codDisciplina, titulo, questoes, valorTotal, valorQuestoes, tempo, data, allowAfterDate, allowMultipleAttempts);
			}
			return new Prova();
		});
	}
	
	// OBTEM O TITULO DE DETERMINADA PROVA
	public String getTitulo(int codProva) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codigo, titulo FROM prova WHERE codigo=?");
			ps.setInt(1, codProva);
			rs = ps.executeQuery();
			
			return (rs.next()) ? rs.getString(2) : "";
		});
	}
	
	// OBTEM O VALOR TOTAL DE DETERMINADA PROVA 
	public float getValorTotal(int codProva) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codigo, valorTotal FROM prova WHERE codigo=?");
			ps.setInt(1, codProva);
			rs = ps.executeQuery();
		
			return (rs.next()) ? rs.getFloat(2) : 0f;
		});
	}
	
	// ALTERA AS INFORMACOES DA PROVA
	public boolean alterarInformacoes(Prova prova) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"UPDATE prova SET codDisciplina=?, titulo=?, valorTotal=?, valorQuestoes=?, tempo=?, data=?, allowAfterDate=?, allowMultipleAttempts=? WHERE codigo=?");
			ps.setInt    (1, prova.getCodDisciplina());
			ps.setString (2, prova.getTitulo());
			ps.setFloat  (3, prova.getValorTotal());
			ps.setFloat  (4, prova.getValorQuestoes());
			ps.setInt    (5, prova.getTempo());
			ps.setString (6, prova.getData()); 
			ps.setBoolean(7, prova.isAllowAfterDate()); 
			ps.setBoolean(8, prova.isAllowMultipleAttempts()); 
			ps.setInt    (9, prova.getCodigo());
		
			return (ps.executeUpdate() > 0) ? true : false;
		});	
	}
}
