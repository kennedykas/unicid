package br.edu.unicid.bean;

public class Disciplina {

	private int    codigo;         // CODIGO DISCIPLINA
	private int    codProfessor;   // CODIGO DO PROFESSOR
//	private int    codCursoCodDisciplina; // COD DA TABELA DE CODIGOS DE DISCIPLINA E CURSOS
	private String nomeDisciplina; // NOME DISCIPLINA
	private String observacoes;    // OBSERVACOES (OPCIONAL)
	
	public Disciplina() {}
	
	public Disciplina(int codigo, int codProfessor, String nomeDisciplina, String observacoes) {
		super();
		this.codigo         = codigo;
		this.codProfessor   = codProfessor;
//		this.codCursoCodDisciplina = codCursoCodDisciplina;
		this.nomeDisciplina = nomeDisciplina;
		this.observacoes    = observacoes;
	}
	
	public Disciplina(int codigo, String nomeDisciplina) {
		super();
		this.codigo = codigo;
		this.nomeDisciplina = nomeDisciplina;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
//	public int getCodCursoCodDisciplina() {
//		return codCursoCodDisciplina;
//	}
//	public void setCodCursoCodDisciplina(int codCursoCodDisciplina) {
//		this.codCursoCodDisciplina = codCursoCodDisciplina;
//	}
	public int getCodProfessor() {
		return codProfessor;
	}
	public void setCodProfessor(int codProfessor) {
		this.codProfessor = codProfessor;
	}
	public String getNomeDisciplina() {
		return nomeDisciplina;
	}
	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
}
