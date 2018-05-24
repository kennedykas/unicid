package br.edu.unicid.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

	/**
     * Executa uma logica de negocio (callback) dentro de um contexto transacional
     *  e retorna o resultado da operacao.
     */
//	@SuppressWarnings("unchecked")
	public <T> T doInTransactionWithReturn(final TransactionCallback<T> callback) throws TransactionException {
    	
    	Connection connection = null;
        
        try {
        	connection = ConnectionFactory.getConnection(); // abre conexao
        	connection.setAutoCommit(false);                // inicia a transacao
            
            T result = callback.execute(connection);        // executa a logica

            connection.commit();                            // comita a transacao
            
            return result;
            
        } catch (Exception e) {
        	if (connection != null) 
        		
        		try { connection.rollback(); } // desfaz alteracoes enviadas pro banco
        		
        		catch (SQLException e1) { e1.printStackTrace();	} 
        	
            throw new RuntimeException(e);
        	
        } finally {
            if (connection != null) 
            	try { connection.close(); }	catch (SQLException e) {} 
        }
    }
	
	/**
	 * Executa uma Unidade de Trabalho dentro de um contexto transacional.
	 */
	public void doInTransaction(final TransactionVoidCallback callback) throws TransactionException {
		doInTransactionWithReturn(new TransactionCallback<Void>() {
			@Override
			public Void execute(Connection connection) {
				callback.execute(connection);
				return null;
			}
		});
	}
}