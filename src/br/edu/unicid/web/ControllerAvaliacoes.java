package br.edu.unicid.web;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.edu.unicid.bean.Avaliacao;
import br.edu.unicid.dao.AvaliacaoDAO;

@ManagedBean(name="controllerAvaliacoes")
@SessionScoped
public class ControllerAvaliacoes {
	
	private AvaliacaoDAO dao;
	private Avaliacao avaliacao;
	private DataModel<Avaliacao> listaAvaliacao;

	public ControllerAvaliacoes() {}

	@PostConstruct
	public void init() {
		this.avaliacao = new Avaliacao();
	}
	
	/** SALVA A AVALIACAO
	 * @param 
	 * @return Retorna uma String com o nome da página a ser renderizada
	 */
	public String responder(String nomeProfessor, int codGrupo) throws Exception {
		// SETA NOME DO PROFESSOR QUE FEZ A AVALIACAO
		this.avaliacao.setNomeProfessor(nomeProfessor);
		this.avaliacao.setCodGrupo(codGrupo);
		
		// SALVA A AVALIACAO
		this.dao = new AvaliacaoDAO();
		
		// MEIO QUE DESNECESSARIA ESSA LINHA PRECISA SER AVALIADA
		return dao.salvar(avaliacao) ? "detalhesAvaliacao" : "detalhesAvaliacao";
	}
	
	/** SALVA A AVALIACAO
	 * @param 
	 * @return retorna uma lista com todas as avaliacoes feitas por professores
	 */
	public DataModel<Avaliacao> getListaAvaliacoes(int codigoGrupo) throws Exception {
		this.dao = new AvaliacaoDAO();
		
		// LISTA DE AVALIACOES
		List<Avaliacao> lista = this.dao.todasAvaliacoes(codigoGrupo);
		// DATAMODEL COM AS AVALIACOES
		this.listaAvaliacao = new ListDataModel<Avaliacao>(lista);
		
		return this.listaAvaliacao;
	}
	
	public void setListaAvaliacao(DataModel<Avaliacao> listaAvaliacao) {
		this.listaAvaliacao = listaAvaliacao;
	}
	
	// GETTERS AND SETTERS
	public Avaliacao getAvaliacao() {
		return avaliacao;
	}
	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

}
