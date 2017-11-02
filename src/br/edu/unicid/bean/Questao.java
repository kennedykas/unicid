package br.edu.unicid.bean;

public class Questao {
	
	private int codigo;
	private int codProfessor;
	private int disciplina;
	private String pergunta;
	private String alternativaA;
	private String alternativaB;
	private String alternativaC;
	private String alternativaD;
	private String alternativaE;
	private String alternativaCorreta;
	private String alternativaSelecionada;
	
	public Questao(){}
	public Questao(int codigo, int codProfessor, int disciplina, String pergunta, String alternativaA, String alternativaB, String alternativaC, String alternativaD, String alternativaE, String alternativaCorreta) {
		super();
		this.codigo = codigo;
		this.codProfessor = codProfessor;
		this.disciplina = disciplina;
		this.pergunta = pergunta;
		this.alternativaA = alternativaA;
		this.alternativaB = alternativaB;
		this.alternativaC = alternativaC;
		this.alternativaD = alternativaD;
		this.alternativaE = alternativaE;
		this.alternativaCorreta = alternativaCorreta;
	}
	public Questao(int codigo, String pergunta, String alternativaA, String alternativaB, String alternativaC, String alternativaD, String alternativaE, String alternativaCorreta) {
		super();
		this.codigo = codigo;
		this.pergunta = pergunta;
		this.alternativaA = alternativaA;
		this.alternativaB = alternativaB;
		this.alternativaC = alternativaC;
		this.alternativaD = alternativaD;
		this.alternativaE = alternativaE;
		this.alternativaCorreta = alternativaCorreta;
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
	public int getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(int disciplina) {
		this.disciplina = disciplina;
	}
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	public String getAlternativaA() {
		return alternativaA;
	}
	public void setAlternativaA(String alternativaA) {
		this.alternativaA = alternativaA;
	}
	public String getAlternativaB() {
		return alternativaB;
	}
	public void setAlternativaB(String alternativaB) {
		this.alternativaB = alternativaB;
	}
	public String getAlternativaC() {
		return alternativaC;
	}
	public void setAlternativaC(String alternativaC) {
		this.alternativaC = alternativaC;
	}
	public String getAlternativaD() {
		return alternativaD;
	}
	public void setAlternativaD(String alternativaD) {
		this.alternativaD = alternativaD;
	}
	public String getAlternativaE() {
		return alternativaE;
	}
	public void setAlternativaE(String alternativaE) {
		this.alternativaE = alternativaE;
	}
	public String getAlternativaCorreta() {
		return alternativaCorreta;
	}
	public void setAlternativaCorreta(String alternativaCorreta) {
		this.alternativaCorreta = alternativaCorreta;
	}
	public String getAlternativaSelecionada() {
		return alternativaSelecionada;
	}
	public void setAlternativaSelecionada(String alternativaSelecionada) {
		this.alternativaSelecionada = alternativaSelecionada;
	}
		
}
