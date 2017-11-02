package br.edu.unicid.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.unicid.bean.Palestra;
import br.edu.unicid.bean.Usuario;
import br.edu.unicid.util.ConnectionFactory;

public class PalestraUserDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private Usuario usuario;
	private Palestra palestra;

	public PalestraUserDAO() {
	}

	// metodo de salvar
	public void salvarPalestra(Usuario user, String palestra) throws Exception {
		if (user == null) {
			throw new Exception("O valor passado NÃO pode ser nulo");
		}
		try {
			this.conn = ConnectionFactory.getConnection();
			String SQL;
			SQL = "INSERT INTO lista_pres (id, cpf, id_palestra) values(?, ?, ?)";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, user.getId());
			ps.setString(2, user.getCpf());
			ps.setString(3, palestra);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,	ps);
		}
	}

	public Map<String, String> getMapPalestrasCadastradas(String cpf)
			throws Exception {

		try {
			this.conn = ConnectionFactory.getConnection();

			String SQL = "Select cpf,id_palestra from lista_pres WHERE cpf=?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, cpf);
			rs = ps.executeQuery();
			Map<String, String> mapPalestraCadastrada = new HashMap<>();
			while (rs.next()) {
				mapPalestraCadastrada.put(
						rs.getString("id_palestra"),
						rs.getString("cpf"));
			}

			return mapPalestraCadastrada;

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,	ps, rs);
		}
	}

	/**
	 *
	 * @param cpf
	 * @return
	 * @throws Exception
	 */
	public List retornaPalestra(String cpf) throws Exception {
		try {
			this.conn = ConnectionFactory.getConnection();

			String SQL = "SELECT* FROM palestra INNER JOIN lista_pres ON palestra.id_palestra = lista_pres.id_palestra WHERE lista_pres.cpf = ?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, cpf);
			rs = ps.executeQuery();
			List<Palestra> listPalestra = new ArrayList<>();
			while (rs.next()) {
				int id_pales = rs.getInt("id_palestra");
				String titulo = rs.getString("titulo");
				String descricao = rs.getString("descricao");
				int vagas = rs.getInt("vagas");
				int contador = rs.getInt("contador");
				String horario = rs.getString("horario");
				listPalestra.add(new Palestra(
						id_pales, titulo, descricao,
						vagas, contador, horario));

			}

			return (List) listPalestra;

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps, rs);
		}
	}

}
