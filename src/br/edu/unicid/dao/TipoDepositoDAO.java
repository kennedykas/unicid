package br.edu.unicid.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.unicid.bean.TipoDeposito;
import br.edu.unicid.util.TransactionManager;

public class TipoDepositoDAO {

	private PreparedStatement ps;
	private ResultSet rs;

	public List<TipoDeposito> getDepositTypes() {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
			
	    	ps = connection.prepareStatement("SELECT * FROM tipo_deposito GROUP BY codigo");
			
			rs = ps.executeQuery();
			
			List<TipoDeposito> list = new ArrayList<TipoDeposito>();
			
			while(rs.next())
				list.add(new TipoDeposito(rs.getInt(1), rs.getString(2)));
			
			return list;
		});
	}
	
	public String getDepositTypeNameByCode(int depositTypeCode) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
			
			ps = connection.prepareStatement("SELECT nome FROM tipo_deposito WHERE codigo=?");
			ps.setInt(1, depositTypeCode);
			rs = ps.executeQuery();

			return (rs.next()) ? rs.getString(1) : "";
	    });
	}
}
