package br.edu.unicid.util;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionVoidCallback {

	public void execute(Connection connection);
	
}