package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.unicid.bean.Curso;
import br.edu.unicid.util.TransactionManager;

public class CursoDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	// RETORNA LISTA COM CODIGO E NOME DE TODOS OS CURSOS
	public List<Curso> cursos() {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("SELECT codigo, nome FROM curso");
			rs = ps.executeQuery();
			
			List<Curso> list = new ArrayList<Curso>();
			
			while(rs.next()) {
				int codigo = rs.getInt(1);
				String nome = rs.getString(2);
				list.add(new Curso(codigo, nome));
			}
			return list;
		});
	}
	
	// RETORNA LISTA COM NOMES DOS CURSOS ATRAVES DOS CODIGOS 
	public String nomesCursos(String codsCursos) {
		
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {

	    	String[]cods = codsCursos.split(","); // SEPARANDO OS CODIGOS PASSADOS
	    	String result = ""; 
		
	    	for (String i : cods) { // PERCORRE TODOS OS CODIGOS PASSADOS BUSCANDO O NOME 
				ps = connection.prepareStatement("SELECT nome FROM curso WHERE codigo=?");
				ps.setInt(1, Integer.parseInt(i));
				rs = ps.executeQuery();
				if (rs.next()) 
					result += rs.getString(1) + ", "; // ARMAZENA NOME ENCONTRADO
			}
			return result;
		});
	}
}
