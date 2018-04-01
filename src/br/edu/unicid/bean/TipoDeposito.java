package br.edu.unicid.bean;

public class TipoDeposito {

	private int    codigo;
	private String nome;
		
	public TipoDeposito() {}
	
	public TipoDeposito(int codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
