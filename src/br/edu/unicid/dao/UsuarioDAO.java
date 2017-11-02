package br.edu.unicid.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.unicid.bean.Usuario;
import br.edu.unicid.util.ConnectionFactory;

public class UsuarioDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private Usuario usuario;

	public UsuarioDAO() {
	}

	// método de salvar
	public void salvar(Usuario usuario) throws Exception {
		if (usuario == null) {
			throw new Exception("O valor passado NÃO pode ser nulo");
		}
		try {
			this.conn = ConnectionFactory.getConnection();
			String SQL;
			SQL = "INSERT INTO usuario (cpf, nome, email, senha, status) values( ?,  ?,  ?,  ?, ?)";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, usuario.getCpf());
			ps.setString(2, usuario.getNome());
			ps.setString(3, usuario.getEmail());
			ps.setString(4, usuario.getSenha());
			ps.setString(5, "1");
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps);
		}
	}

	// Procurar Usuario
	/**
	 *
	 * @param login
	 * @param senhalogin
	 * @return
	 * @throws Exception
	 */
	public Usuario procurarUsuario(String login) throws Exception {
		try {
			this.conn = ConnectionFactory.getConnection();

			String SQL = "SELECT * FROM usuario WHERE cpf=?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, login);
			rs = ps.executeQuery();
			if (rs.next()) {
				String cpf = rs.getString("cpf");
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				String status = rs.getString("status");
				int id_user = rs.getInt("id");
				usuario = new Usuario(cpf, nome, email, senha, status, id_user);
			}

			return usuario;

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps, rs);
		}
	}

	public boolean validaLogin(String login, String senhalogin)
			throws Exception {
		try {
			this.conn = ConnectionFactory.getConnection();

			String SQL = "SELECT * FROM usuario WHERE cpf=? AND senha=?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, login);
			ps.setString(2, senhalogin);
			rs = ps.executeQuery();

			return rs.next();

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps, rs);
		}

	}

	public void atualizaUsuario(Usuario usuario) throws Exception {
		if (usuario == null) {
			throw new Exception("O valor passado nao pode ser nulo");
		}
		try {
			this.conn = ConnectionFactory.getConnection();
			String SQL = "UPDATE usuario set nome=?,email=?,senha=?,status=? WHERE  cpf =?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getSenha());
			ps.setString(4, "1");
			ps.setString(5, usuario.getCpf());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao alterar dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps);
		}
	}

	public void atualizaUsuarioPalestra(Usuario usuario) throws Exception {
		if (usuario == null) {
			throw new Exception("O valor passado nao pode ser nulo");
		}
		try {
			this.conn = ConnectionFactory.getConnection();
			String SQL = "UPDATE usuario set status=? WHERE  cpf =?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, "2");
			ps.setString(2, usuario.getCpf());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao alterar dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) conn,
					ps);
		}
	}

}
