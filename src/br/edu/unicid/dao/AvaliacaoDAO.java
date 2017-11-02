package br.edu.unicid.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.unicid.bean.Avaliacao;
import br.edu.unicid.util.ConnectionFactory;

public class AvaliacaoDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
//	private Avaliacao avaliacao;
	
	// Construtor
	public AvaliacaoDAO() throws Exception {
		//chama a classe connetion e estabelece a conexao
		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			//forço ele a me mostrar o erro enquanto eu desenvolvo
			throw new Exception("erro: \n" + e.getMessage());
		}
	}

	// Save
	public boolean salvar(Avaliacao avaliacao) throws Exception {
		try {
			String SQL = "INSERT INTO avaliacoes (codigo, codigoGrupo, nomeProfessor, avaliacao, data) values (?, ?, ?, ?, ?)";

			ps = conn.prepareStatement(SQL);
			ps.setInt(1, 0); //code
			ps.setInt(2, avaliacao.getCodGrupo()); //codGrupo
			ps.setString(3, avaliacao.getNomeProfessor()); //codProfessor
			ps.setString(4, avaliacao.getAvaliacao()); //avaliacao
			ps.setDate(5, new java.sql.Date(System.currentTimeMillis())); //data
			
			if(ps.executeUpdate() > 0)
				return true;
			else
				return false;
			
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}
	
	// Lista todos
	public List<Avaliacao> todasAvaliacoes(int codGrupo) throws Exception {
		try {
			ps = conn.prepareStatement("SELECT * FROM avaliacoes WHERE codigoGrupo=?");
			ps.setInt(1, codGrupo);
			rs = ps.executeQuery();
			List<Avaliacao> list = new ArrayList<Avaliacao>();
			while(rs.next()) {
				// Voçê pode tanto usar o indice como o nome da coluna
				int codigo = rs.getInt(1);
				int codigoGrupo = rs.getInt(2);
				String nomeProfessor = rs.getString(3);
				String avaliacao = rs.getString(4);
				Date data = rs.getDate(5);
				
				list.add(new Avaliacao(codigo, codigoGrupo, nomeProfessor, avaliacao, data));
			}
			return list;
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}

}
