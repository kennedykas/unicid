package br.edu.unicid.bean;

public class DisciplinaCurso {

	private int codigo;
	private int codCurso;
	private int codDisciplina;
	private int[] coursesThatDisciplineBelongTo;
	
	public DisciplinaCurso() {}
	
	public DisciplinaCurso(int codigo, int codCurso, int codDisciplina) {
		this.codigo        = codigo;
		this.codCurso      = codCurso;
		this.codDisciplina = codDisciplina;
	}
	public DisciplinaCurso(int codDisciplina, int[] coursesThatDisciplineBelongTo) {
		this.codDisciplina = codDisciplina;
		this.coursesThatDisciplineBelongTo = coursesThatDisciplineBelongTo;
	}
	
	// GETTERS AND SETTERS
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodCurso() {
		return codCurso;
	}
	public void setCodCurso(int codCurso) {
		this.codCurso = codCurso;
	}
	public int[] getCoursesThatDisciplineBelongTo() {
		return coursesThatDisciplineBelongTo;
	}
	public void setCoursesThatDisciplineBelongTo(int[] coursesThatDisciplineBelongTo) {
		this.coursesThatDisciplineBelongTo = coursesThatDisciplineBelongTo;
	}
	public int getCodDisciplina() {
		return codDisciplina;
	}
	public void setCodDisciplina(int codDisciplina) {
		this.codDisciplina = codDisciplina;
	}
}
