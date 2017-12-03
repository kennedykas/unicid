package br.edu.unicid.bean;

public class ProvasRealizadas {
	
	private int codigo;
	private int codProva;
	private int codProfessor;
	private int codAluno;
	private double nota;
	private String tempo;
	private String data;
	
	public ProvasRealizadas() {}
	
	public ProvasRealizadas(int codigo, int codProva, int codProfessor, int codAluno, double nota, String tempo, String data) {
		super();
		this.codigo = codigo;
		this.codProva = codProva;
		this.codProfessor = codProfessor;
		this.codAluno = codAluno;
		this.nota = nota;
		this.tempo = tempo;
		this.data = data;
	}
	
	public ProvasRealizadas(int codProva, int codAluno, double nota, String tempo, String data) {
		super();
		this.codProva = codProva;
		this.codAluno = codAluno;
		this.nota = nota;
		this.tempo = tempo;
		this.data = data;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodProva() {
		return codProva;
	}
	public void setCodProva(int codProva) {
		this.codProva = codProva;
	}
	public int getCodProfessor() {
		return codProfessor;
	}
	public void setCodProfessor(int codProfessor) {
		this.codProfessor = codProfessor;
	}
	public int getCodAluno() {
		return codAluno;
	}
	public void setCodAluno(int codAluno) {
		this.codAluno = codAluno;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
	public String getTempo() {
		return tempo;
	}
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
