package br.edu.unicid.bean;

public class DisciplinaCurso {

	private int codigo;
	private int codCurso;
	private int codDisciplina;
//	private ArrayList<Integer> cursos;
	private int[] cursos;
	
	public DisciplinaCurso() {}
	
	public DisciplinaCurso(int codigo, int codCurso, int codDisciplina) {
		this.codigo        = codigo;
		this.codCurso      = codCurso;
		this.codDisciplina = codDisciplina;
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
//	public ArrayList<Integer> getCursos() {
//		return cursos;
//	}
//	public void setCursos(ArrayList<Integer> cursos) {
//		this.cursos = cursos;
//	}
	public int[] getCursos() {
		return cursos;
	}
	public void setCursos(int[] cursos) {
		this.cursos = cursos;
	}
	public int getCodDisciplina() {
		return codDisciplina;
	}
	public void setCodDisciplina(int codDisciplina) {
		this.codDisciplina = codDisciplina;
	}
}
