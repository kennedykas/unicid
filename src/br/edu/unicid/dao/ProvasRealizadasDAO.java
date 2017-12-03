package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.unicid.bean.ProvasRealizadas;
import br.edu.unicid.util.TransactionManager;

public class ProvasRealizadasDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	// SAVE
	public boolean salvar(ProvasRealizadas provasRealizadas) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
			
	    	// CHECA SE O TESTE JA FOI FEITO PELO USUARIO
	    	ps = connection.prepareStatement(
	    			"SELECT codigo FROM provas_realizadas WHERE codAluno=? AND codProva=?");
			ps.setInt(1, provasRealizadas.getCodAluno());
			ps.setInt(2, provasRealizadas.getCodProva());

			rs = ps.executeQuery();
			
			// CASO O TESTE JA TENHA SIDO FEITO ELE E APENAS ATUALIZADO
			if(rs.next()) {
				ps = connection.prepareStatement(
						"UPDATE provas_realizadas SET nota=?, tempo=?, data=? WHERE codigo=?");

				ps.setDouble(1, provasRealizadas.getNota());
				ps.setString(2, provasRealizadas.getTempo());
				ps.setString(3, provasRealizadas.getData());
				ps.setInt   (4, rs.getInt(1));

			// SALVA O TESTE
			} else { 
				ps = connection.prepareStatement(
						"INSERT INTO provas_realizadas (codigo, codProva, codProfessor, codAluno, nota, tempo, data) values (?, ?, ?, ?, ?, ?, ?)");
				
				ps.setInt   (1, 0);
				ps.setInt   (2, provasRealizadas.getCodProva()); 
				ps.setInt   (3, provasRealizadas.getCodProfessor()); 
				ps.setInt   (4, provasRealizadas.getCodAluno());
				ps.setDouble(5, provasRealizadas.getNota());
				ps.setString(6, provasRealizadas.getTempo());
				ps.setString(7, provasRealizadas.getData());
				
			}

			return (ps.executeUpdate() > 0) ? true : false;
		});
	}
	
	// EXCLUIR
	public boolean excluir(int codigo) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("DELETE FROM provas_realizadas WHERE codigo=?");
			ps.setInt(1, codigo);
			
			return (ps.execute()) ? true : false;
		});
	}

	// GET ALL ALUNOS QUES JA REALIZARAM A PROVA DE DETERMINADO PROFESSOR
	public List<ProvasRealizadas> provasRealizadasProfessor(int codigoProfessor) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codigo, codProva, codProfessor, codAluno, nota, tempo, data FROM provas_realizadas WHERE codProfessor=? GROUP BY codigo");
			ps.setInt(1, codigoProfessor);
			rs = ps.executeQuery();
			
			List<ProvasRealizadas> list = new ArrayList<ProvasRealizadas>();
			
			while(rs.next()) {
				int       codigo = rs.getInt(1);
				int     codProva = rs.getInt(2);
				int codProfessor = rs.getInt(3);
				int     codAluno = rs.getInt(4);
				Double      nota = rs.getDouble(5);
				String     tempo = rs.getString(6);
				String      data = rs.getString(7);
				list.add(new ProvasRealizadas(codigo, codProva, codProfessor, codAluno, nota, tempo, data));
			}
			return list;
		});
	}	

	//Lista todas provas realizadas para o aluno
	public List<ProvasRealizadas> provasRealizadasAluno(int codigoAluno) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codProva, codAluno, nota, tempo, data FROM provas_realizadas WHERE codAluno=?");
			ps.setInt(1, codigoAluno);
			rs = ps.executeQuery();
			
			List<ProvasRealizadas> list = new ArrayList<>();
			
			while(rs.next()) {
				int codProva = rs.getInt(1);
				int codAluno = rs.getInt(2);
				Double  nota = rs.getDouble(3);
				String tempo = rs.getString(4);
				String  data = rs.getString(5);
				list.add(new ProvasRealizadas(codProva, codAluno, nota, tempo, data));
			}
			return list;
		});
	}
	
	// OBTEM CODIGO PROVAS REALIZADAS PELO ALUNO
	public List<Integer> obtemProvas(int codigoAluno) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement(
					"SELECT codProva, codAluno FROM provas_realizadas WHERE codAluno=?");
			ps.setInt(1, codigoAluno);
			rs = ps.executeQuery();
			
			List<Integer> list = new ArrayList<Integer>();
			
			while(rs.next()) 
				list.add(rs.getInt(1));
			
			return list;
		});
	}
}
