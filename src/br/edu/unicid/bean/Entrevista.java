package br.edu.unicid.bean;

public class Entrevista {

	private int    codigo;
	private String nome;
	private String email;
	private String cpf;
	private String celular;
	private String nomeCurso;
	private String data;
	
	public Entrevista() {}

	public Entrevista(int codigo, String curso, String nome, String email, String cpf, String celular, String data) {
		this.codigo    = codigo;
		this.nomeCurso = curso;
		this.nome      = nome;
		this.email     = email;
		this.cpf       = cpf;
		this.celular   = celular;
		this.data      = data;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
