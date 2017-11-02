package br.edu.unicid.bean;

import java.util.Date;

public class Avaliacao {
	private int codigo;
	private int codGrupo;
	private String nomeProfessor;
	private String avaliacao;
	private Date data;
	
	public Avaliacao(int codigo, int codGrupo, String nomeProfessor, String avaliacao, Date data) {
		super();
		this.codigo = codigo;
		this.codGrupo = codGrupo;
		this.nomeProfessor = nomeProfessor;
		this.avaliacao = avaliacao;
		this.data = data;
	}
	
	public Avaliacao() {}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodGrupo() {
		return codGrupo;
	}

	public void setCodGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public String getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
		
}
