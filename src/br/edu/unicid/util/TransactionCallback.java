package br.edu.unicid.util;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionCallback<T> {

	public T execute(Connection connection) throws SQLException;
	
}