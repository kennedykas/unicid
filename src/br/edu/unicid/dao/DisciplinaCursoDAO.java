package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.edu.unicid.bean.DisciplinaCurso;
import br.edu.unicid.util.TransactionManager;

public class DisciplinaCursoDAO {

	private PreparedStatement ps;
	private ResultSet rs;
	
	// EXCLUIR
	public boolean excluir(int codigo) {
	    TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
	    	this.ps = connection.prepareStatement(
	    			"DELETE FROM disciplina_curso WHERE codigo=?");
	    	this.ps.setInt(1, codigo);
			
			return (this.ps.execute()) ? true : false;
	    });
	}
		
	// SAVE
	public boolean salvar(DisciplinaCurso disciplinaCurso) {
    	TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {
	    	
    		this.ps = connection.prepareStatement(
    				"INSERT INTO disciplina_Curso (codigo, codDisciplina, codCurso) values (?, ?, ?)");
    		this.ps.setInt(1, 0); 
    		this.ps.setInt(2, disciplinaCurso.getCodDisciplina()); 
			
			int affectedRows = 0;
			
			for(int i : disciplinaCurso.getCursos()) {
				this.ps.setInt(3, i);
				affectedRows = this.ps.executeUpdate();
			}

			return (affectedRows > 0) ? true : false;
	    });
	}
	
	// ALTERAR
	public boolean alterar(int[] codigosCursos, int codigoDisciplina) {
    	TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {
	    	
    		String insertQuery = 
    				"INSERT INTO disciplina_curso (codigo, codDisciplina, codCurso) values (?, ?, ?)";
    		String deleteQuery = 
    				"DELETE FROM disciplina_curso WHERE codDisciplina=?";
    		
    		// REMOVE TODOS OS REGISTROS DESTA DISCIPLINA REALACIONADA A QUALQUER CURSO
    		this.ps = connection.prepareStatement(deleteQuery);
    		this.ps.setInt(1, codigoDisciplina);
    		
    		this.ps.execute();
    		
    		// INSERE AS NOVAS RELACOES
    		this.ps = connection.prepareStatement(insertQuery);
    		this.ps.setInt(1, 0);
    		this.ps.setInt(2, codigoDisciplina);
    		
    		int affectedRows = 0;
    		
    		for(int i : codigosCursos) {
				this.ps.setInt(3, i);
				affectedRows = this.ps.executeUpdate();
			}

			return (affectedRows > 0) ? true : false;
	    });
	}	
	
	// RETURN COURSES BY COD DISCIPLINA
	public int[] cursosPeloCodigoDisciplina(int codigoDisciplina) {
		TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {

    		this.ps = connection.prepareStatement(
    				"SELECT codCurso FROM disciplina_curso WHERE codDisciplina=?");
    		this.ps.setInt(1, codigoDisciplina);
    		this.rs = this.ps.executeQuery();
			
			// LIST PARA ARMAZENAR O RESULT SET
			ArrayList<Integer> codigosCursos = new ArrayList<Integer>();
			
			while(this.rs.next())
				codigosCursos.add(this.rs.getInt(1));
			
			// ARRAY INT A SER RETORNADO
			int[] codsCursos = new int[codigosCursos.size()];
			
			// PASSANDO DADOS DO ARRAYLIST PARA O ARRAY
			for(int i = 0; i < codigosCursos.size(); i++) 
				codsCursos[i] = codigosCursos.get(i).intValue();
		
			return codsCursos;  			
		});
	}
	
	// RETURN DISCIPLINAS BY COD COURSE
	public ArrayList<Integer> obterCodDisciplinaPeloCodCurso(int codCurso) {
		TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {

    		this.ps = connection.prepareStatement(
    				"SELECT codDisciplina FROM disciplina_curso WHERE codCurso=?");
    		this.ps.setInt(1, codCurso);
    		this.rs = this.ps.executeQuery();
			
    		// LIST PARA ARMAZENAR O RESULT SET
    		ArrayList<Integer> codigosDisciplinas = new ArrayList<Integer>();
						
			while(this.rs.next())
				codigosDisciplinas.add(this.rs.getInt(1));
			
			return codigosDisciplinas;  			
		});
	}

}
