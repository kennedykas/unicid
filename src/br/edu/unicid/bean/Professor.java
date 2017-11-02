package br.edu.unicid.bean;

public class Professor {

	private int codigo; //codigo
	private String nomeProfessor; //nome do professor
	private String emailProfessor; //email do professor
	private String senhaProfessor; //senha do professor
	private String data; //dia em que o professor se cadastrou 
	
	// Construtores
	public Professor() {}
	
	public Professor(int codigo, String nomeProfessor, String emailProfessor, String senhaProfessor, String data) {
		super();
		this.codigo = codigo;
		this.nomeProfessor = nomeProfessor;
		this.emailProfessor = emailProfessor;
		this.senhaProfessor = senhaProfessor;
		this.data = data;
	}
	
	public Professor(int codigo, String nomeProfessor) {
		super();
		this.codigo = codigo;
		this.nomeProfessor = nomeProfessor;
	}

	// Getters and setters
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public String getEmailProfessor() {
		return emailProfessor;
	}

	public void setEmailProfessor(String emailProfessor) {
		this.emailProfessor = emailProfessor;
	}

	public String getSenhaProfessor() {
		return senhaProfessor;
	}

	public void setSenhaProfessor(String senhaProfessor) {
		this.senhaProfessor = senhaProfessor;
	}
	public String getData() {
		return this.data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
