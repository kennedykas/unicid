package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;

import br.edu.unicid.bean.Imagem;
import br.edu.unicid.util.TransactionManager;

public class ImagemDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	// EXCLUIR
	public boolean excluir(int codigo) {
	    TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
	    	this.ps = connection.prepareStatement(
	    			"DELETE FROM imagem WHERE codigo=?");
	    	this.ps.setInt(1, codigo);
			
			return (this.ps.execute()) ? true : false;
	    });
	}
		
	/** SALVA IMAGEM
	 * @param Objeto imagem
	 * @return Int código da imagem
	 * */
	public int salvar(Imagem imagem) {
    	TransactionManager txManager = new TransactionManager();
    	return txManager.doInTransactionWithReturn((connection) -> {
	    	
    		String SQL = "INSERT INTO imagem (codigo, nome_imagem, imagem) values (?, ?, ?)";
    		
    		this.ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
    		this.ps.setInt   (1, 0); 
    		this.ps.setString(2, imagem.getNomeImagem()); 
    		this.ps.setBytes (3, imagem.getImagem());
									
			if(this.ps.executeUpdate() > 0) // CASO A IMAGEM TENHA SIDO SALVA
				this.rs = this.ps.getGeneratedKeys(); // OBTEM A PK

			return (this.rs.next()) ? this.rs.getInt(1) : 0; // RETORNA A PK DA DISCIPLINA
	    });
	}
	
	// RETORNA IMAGEM
	public Imagem obtemImagem(int codImagem) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
						
			this.ps = connection.prepareStatement(
					"SELECT nome_imagem, imagem FROM imagem WHERE codigo=?");
			this.ps.setInt   (1, codImagem);
			
			this.rs = this.ps.executeQuery();
			
			if(this.rs.next()) {
				String nome    = this.rs.getString(1);
				byte[] imagem  = this.rs.getBytes (2);
				return new Imagem(codImagem, nome, imagem);
			}
			return new Imagem();
		});
	}
	
	// ALTERAR
	public boolean alterar(Imagem imagem) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
						
	    	this.ps = connection.prepareStatement(
					"UPDATE imagem SET nome_imagem=?, imagem=? WHERE codigo=?");
	    	this.ps.setString(1, imagem.getNomeImagem()); 
	    	this.ps.setBytes (2, imagem.getImagem()); 
	    	this.ps.setInt   (3, imagem.getCodigo()); 
			
			return (this.ps.executeUpdate() > 0) ? true : false;
		});
	}
	
}
