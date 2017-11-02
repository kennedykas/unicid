package br.edu.unicid.bean;

public class Palestra {

	private int id_palestra;
	private String titulo;
	private String descricao;
	private int vagas;
	private int contador;
	private String horario;

	public Palestra() {}

	public Palestra(int id_palestra, String titulo, String descricao,
			int vagas, int contador, String horario) {

		this.id_palestra = id_palestra;
		this.titulo = titulo;
		this.descricao = descricao;
		this.vagas = vagas;
		this.contador = contador;
		this.horario = horario;

	}

	/**
	 * @return the id_palestra
	 */
	public int getId_palestra() {
		return id_palestra;
	}

	/**
	 * @param id_palestra
	 *            the id_palestra to set
	 */
	public void setId_palestra(int id_palestra) {
		this.id_palestra = id_palestra;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the vagas
	 */
	public int getVagas() {
		return vagas;
	}

	/**
	 * @param vagas
	 *            the vagas to set
	 */
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}

	/**
	 * @return the contador
	 */
	public int getContador() {
		return contador;
	}

	/**
	 * @param contador
	 *            the contador to set
	 */
	public void setContador(int contador) {
		this.contador = contador;
	}

	/**
	 * @return the horario
	 */
	public String getHorario() {
		return horario;
	}

	/**
	 * @param horario
	 *            the horario to set
	 */
	public void setHorario(String horario) {
		this.horario = horario;
	}

}
