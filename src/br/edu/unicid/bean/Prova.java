package br.edu.unicid.bean;

public class Prova {

	private int    codigo;
	private int    codProfessor;
	private int    codDisciplina;
	private String titulo;
	private String questoes;
	private float  valorTotal;
	private float  valorQuestoes;
	private int    tempo; // TEMPO QUE ALUNO LEVOU
	private String data;
	private float  nota; // NOTA QUE O ALUNO TIROU NA PROVA
	// PERMITE A REALIZACAO DA PROVA MESMO APOS A DATA DETERMINADA
	private boolean allowAfterDate;
	// PERMITE MULTIPLAS TENTATIVAS
	private boolean allowMultipleAttempts;
	
	public Prova() {}
	
	public Prova(int codigo, int codProfessor, int codDisciplina, String titulo, String questoes, float valorTotal, float valorQuestoes, int tempo, String data, boolean allowAfterDate, boolean allowMultipleAttempts) {
		super();
		this.codigo        = codigo;
		this.codProfessor  = codProfessor;
		this.codDisciplina = codDisciplina;
		this.titulo        = titulo;
		this.questoes      = questoes;
		this.valorTotal    = valorTotal;
		this.valorQuestoes = valorQuestoes;
		this.tempo         = tempo;
		this.data          = data;
		this.allowAfterDate = allowAfterDate;
		this.allowMultipleAttempts = allowMultipleAttempts;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodProfessor() {
		return codProfessor;
	}
	public void setCodProfessor(int codProfessor) {
		this.codProfessor = codProfessor;
	}
	public int getCodDisciplina() {
		return codDisciplina;
	}
	public void setCodDisciplina(int codDisciplina) {
		this.codDisciplina = codDisciplina;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getQuestoes() {
		return questoes;
	}
	public void setQuestoes(String questoes) {
		this.questoes = questoes;
	}
	public float getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
	public float getValorQuestoes() {
		return valorQuestoes;
	}
	public void setValorQuestoes(float valorQuestoes) {
		this.valorQuestoes= valorQuestoes;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isAllowMultipleAttempts() {
		return allowMultipleAttempts;
	}
	public void setAllowMultipleAttempts(boolean allowMultipleAttempts) {
		this.allowMultipleAttempts = allowMultipleAttempts;
	}
	public boolean isAllowAfterDate() {
		return allowAfterDate;
	}
	public void setAllowAfterDate(boolean allowAfterDate) {
		this.allowAfterDate = allowAfterDate;
	}
	public float getNota() {
		return nota;
	}
	public void setNota(float nota) {
		this.nota = nota;
	}
}
