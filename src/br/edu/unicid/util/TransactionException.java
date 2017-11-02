package br.edu.unicid.util;

public class TransactionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TransactionException(Throwable throwable) {
        super(throwable);
    }
    
}