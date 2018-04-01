package br.edu.unicid.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.unicid.bean.Deposit;
import br.edu.unicid.util.TransactionManager;

public class DepositDAO {
	
	private PreparedStatement ps;
	private ResultSet rs;

	public boolean save(Deposit deposit) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	     	
	    	ps = connection.prepareStatement(
	    			"INSERT INTO deposits (code, professor, student, date, type, value, comments) values (?, ?, ?, ?, ?, ?, ?)");
	    	
			ps.setInt       (1, 0);
			ps.setInt       (2, deposit.getProfessor()); 
			ps.setInt       (3, deposit.getStudent());
			ps.setDate      (4, java.sql.Date.valueOf(deposit.getDate()));
			ps.setInt       (5, deposit.getType());
			ps.setBigDecimal(6, deposit.getValue());
			ps.setString    (7, deposit.getComments());
						
			return (ps.executeUpdate() > 0) ? true : false;
		});
	}
	
	public boolean delete(int code) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
	    	
			ps = connection.prepareStatement("DELETE FROM deposits WHERE code=?");
			ps.setInt(1, code);
			
			return (ps.execute()) ? true : false;
		});
	}
	
	public boolean change(Deposit deposit) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
						
			ps = connection.prepareStatement(
					"UPDATE deposits SET student=?, date=?, type=?, value=?, comments=? WHERE code=?");
			ps.setInt       (1, deposit.getStudent()); 
			ps.setDate      (2, java.sql.Date.valueOf(deposit.getDate())); 
			ps.setInt       (3, deposit.getType());
			ps.setBigDecimal(4, deposit.getValue());
			ps.setString    (5, deposit.getComments());
			ps.setInt       (6, deposit.getCode());
						
			return (ps.executeUpdate() > 0) ? true : false;
		});
	}
	
	public List<Deposit> getDepositsByProfessorCode(int professorCode) {
		return deposits(professorCode, "SELECT * FROM deposits WHERE professor=? ORDER BY student, date");
	}
	
	public List<Deposit> getDeposits(int studentCode) {
		return deposits(studentCode, "SELECT * FROM deposits WHERE student=? ORDER BY date");
	}
	
	public List<Deposit> deposits(Integer userCode, String query) {
		TransactionManager txManager = new TransactionManager();
	    return txManager.doInTransactionWithReturn((connection) -> {
		
    		ps = connection.prepareStatement(query);
    		ps.setInt(1, userCode);
			rs = ps.executeQuery();
			
			List<Deposit> list = new ArrayList<>();
			
			while(rs.next()) {
				int        code      = rs.getInt(1);
				int        professor = rs.getInt(2);
				int        student   = rs.getInt(3);
				LocalDate  date      = java.time.LocalDate.parse(rs.getDate(4).toString());
				int        type      = rs.getInt(5);
				BigDecimal value     = rs.getBigDecimal(6);
				String     comments  = rs.getString(7);
				list.add(new Deposit(code, professor, student, date, type, value, comments));
			}
			
			return list;
		});
	}
	
}
