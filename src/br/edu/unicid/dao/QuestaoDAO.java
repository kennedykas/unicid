package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.unicid.bean.Questao;
import br.edu.unicid.util.TransactionManager;

public class QuestaoDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	// SAVE
	public boolean salvar(Questao questao) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {

			ps = connection.prepareStatement(
					"INSERT INTO questao (codigo, codProfessor, disciplina, pergunta, alternativaA, alternativaB, alternativaC, alternativaD, alternativaE, alternativaCorreta) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt   (1, 0); //code
			ps.setInt   (2, questao.getCodProfessor()); 
			ps.setInt   (3, questao.getDisciplina());
			ps.setString(4, questao.getPergunta());
			ps.setString(5, questao.getAlternativaA());
			ps.setString(6, questao.getAlternativaB());
			ps.setString(7, questao.getAlternativaC());
			ps.setString(8, questao.getAlternativaD());
			ps.setString(9, questao.getAlternativaE());
			ps.setString(10, questao.getAlternativaCorreta());
			
			return (ps.executeUpdate() > 0) ? true : false;
		});
	}
	
	// DELETE
	public boolean excluir(int codigo) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("DELETE FROM questao WHERE codigo=?");
			ps.setInt(1, codigo);

			return (ps.execute()) ? true : false;
		});
	}

	// OBTEM TODAS QUESTOES DE UM PROFESSOR 
	public List<Questao> todasQuestoes(int codigoProfessor) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codigo, codProfessor, disciplina, pergunta, alternativaA, alternativaB, alternativaC, alternativaD, alternativaE, alternativaCorreta FROM questao WHERE codProfessor=? GROUP BY codigo");
			ps.setInt(1, codigoProfessor);
			rs = ps.executeQuery();
			
			List<Questao> list = new ArrayList<Questao>();
			
			while(rs.next()) {
				int          codigo = rs.getInt(1);
				int    codProfessor = rs.getInt(2);
				int      disciplina = rs.getInt(3);
				String     pergunta = rs.getString(4);
				String alternativaA = rs.getString(5);
				String alternativaB = rs.getString(6);
				String alternativaC = rs.getString(7);
				String alternativaD = rs.getString(8);
				String alternativaE = rs.getString(9);
				String alternativaCorreta = rs.getString(10);
				list.add(new Questao(codigo, codProfessor, disciplina, pergunta, alternativaA, alternativaB, alternativaC, alternativaD, alternativaE, alternativaCorreta));
			}
			return list;
		});
	}
	
	// OBTEM UMA QUESTAO ESPECIFICA
	public Questao getQuestao(int codigoQuestao) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codigo, pergunta, alternativaA, alternativaB, alternativaC, alternativaD, alternativaE, alternativaCorreta FROM questao WHERE codigo=?");
			ps.setInt(1, codigoQuestao);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int          codigo = rs.getInt(1);
				String     pergunta = rs.getString(2);
				String alternativaA = rs.getString(3);
				String alternativaB = rs.getString(4);
				String alternativaC = rs.getString(5);
				String alternativaD = rs.getString(6);
				String alternativaE = rs.getString(7);
				String alternativaCorreta = rs.getString(8);
				return new Questao(codigo, pergunta, alternativaA, alternativaB, alternativaC, alternativaD, alternativaE, alternativaCorreta);
			}
			return new Questao();
		});
	}
	
	// ALTERAR
	public boolean alterar(Questao questao) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"UPDATE questao SET disciplina=?, pergunta=?, alternativaA=?, alternativaB=?, alternativaC=?, alternativaD=?, alternativaE=?, alternativaCorreta=? WHERE codigo=?");
			ps.setInt   (1, questao.getDisciplina()); 
			ps.setString(2, questao.getPergunta()); 
			ps.setString(3, questao.getAlternativaA()); 
			ps.setString(4, questao.getAlternativaB()); 
			ps.setString(5, questao.getAlternativaC()); 
			ps.setString(6, questao.getAlternativaD()); 
			ps.setString(7, questao.getAlternativaE()); 
			ps.setString(8, questao.getAlternativaCorreta());
			ps.setInt   (9, questao.getCodigo());
			
			return (ps.executeUpdate() > 0) ? true : false;
		});	
	}
}
