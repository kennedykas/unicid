package br.edu.unicid.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import br.edu.unicid.bean.Palestra;
import br.edu.unicid.util.ConnectionFactory;

public class PalestraDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private Palestra palestra;

	public PalestraDAO() {
	}

	// Procurar Usuario
	/**
	 *
	 * @param idPalestra
	 * @return
	 * @throws Exception
	 */
	public Palestra procurarPalestra(int idPalestra) throws Exception {
		try {
			this.conn = ConnectionFactory.getConnection();

			String SQL = "SELECT * FROM palestra WHERE id_palestra=?";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, idPalestra);
			rs = ps.executeQuery();
			if (rs.next()) {
				int id_pales = rs.getInt("id_palestra");
				String titulo = rs.getString("titulo");
				String descricao = rs.getString("descricao");
				int vagas = rs.getInt("vagas");
				int contador = rs.getInt("contador");
				String horario = rs.getString("horario");
				palestra = new Palestra(id_pales, titulo, descricao, vagas,
						contador, horario);
			}

			return palestra;

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps, rs);
		}
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public Map<String, Boolean> getMapTemVaga() throws Exception {

		try {
			this.conn = ConnectionFactory.getConnection();

			String SQL = "Select id_palestra, (vagas - contador) vagas from palestra";
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			Map<String, Boolean> mapTemVaga = new HashMap<>();
			while (rs.next()) {
				mapTemVaga.put(rs.getString("id_palestra"),
						rs.getInt("vagas") > 0);
			}

			return mapTemVaga;

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps, rs);
		}
	}

	public boolean temVaga(int id_palestra) throws Exception {

		try {
			this.conn = ConnectionFactory.getConnection();

			String SQL = "SELECT * FROM palestra WHERE id_palestra=? AND contador<40";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, id_palestra);
			rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps, rs);
		}

	}

	public void atualizaContador(int id_palestra, int contador)
			throws Exception {
		if (id_palestra == -1 && contador == -1) {
			throw new Exception("O valor passado nao pode ser nulo");
		}
		try {
			this.conn = ConnectionFactory.getConnection();
			String SQL = "UPDATE palestra set contador=? WHERE  id_palestra =?";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, contador);
			ps.setInt(2, id_palestra);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao alterar dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps);
		}
	}

}
