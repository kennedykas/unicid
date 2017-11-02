package br.edu.unicid.bean;

public class Imagem {

	private int    codigo;
	private String nomeImagem;
	private byte[] imagem; // Array com os bytes da imagem submetida
	
	public Imagem() {}
	
	public Imagem(int codigo, String nomeImagem, byte[] imagem) {
		super();
		this.codigo     = codigo;
		this.nomeImagem = nomeImagem;
		this.imagem     = imagem;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNomeImagem() {
		return nomeImagem;
	}
	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}
	public byte[] getImagem() {
		return imagem;
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
}
