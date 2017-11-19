package br.edu.unicid.bean;

public class Curso {

	private int    codigo;          // COD CURSO
	private int    codUniversidade; // COD DA UNIVERSIDADE QUE O CURSO PERTENCE 
	private String nome;            // NOME DO CURSO
	private String data;            // DATA EM QUE O CURSO FOI CADASTRADO
	private int    icon;            // ICONE DO CURSO
	private String modulos;         // MODULOS
	private String ementa;			// EMENTA
	private String certificacoes;	// CERTIFICACOES
	private String objetivos;		// OBJETIVOS
	private String investimento;	// INVESTIMENTO
	private String cargaHoraria;	// CARGA HORARIA
	private String horario;			// HORARIO
	private String duracao;			// DURACAO
	private String taxaInscricao;	// TAXA DE INSCRICAO
	private int[]  codigos;			// CODS DOS CURSOS SELECIONADOS NO SELECT MANY CURSOS
	private String conteudoProgramatico; // CONTEUDO PROGRAMATICO
	
	public Curso() {}
	
	public Curso(int codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Curso(int codigo, int codUniversidade, String nome, String data) {
		super();
		this.codigo = codigo;
		this.codUniversidade = codUniversidade;
		this.nome = nome;
		this.data = data;
	}
	
	public Curso(
			int codigo, 
			int codUniversidade, 
			String nome, 
			String data, 
			String modulos, 
			String ementa, 
			String certificacoes, 
			String conteudoProgramatico,
			String horario,
			String duracao,
			String cargaHoraria,
			String investimento,
			String taxaInscricao) {
		super();
		this.codigo = codigo;
		this.codUniversidade = codUniversidade;
		this.nome = nome;
		this.data = data;
		this.modulos = modulos;
		this.ementa = ementa;
		this.certificacoes = certificacoes; 
		this.conteudoProgramatico = conteudoProgramatico;
		this.horario = horario;
		this.duracao = duracao;
		this.cargaHoraria = cargaHoraria;
		this.investimento = investimento;
		this.taxaInscricao = taxaInscricao;
	}
	
	public Curso(
			int codigo, 
			int codUniversidade, 
			String nome, 
			String data, 
			String objetivos, 
			String conteudoProgramatico,
			String investimento,
			String cargaHoraria) {
		super();
		this.codigo = codigo;
		this.codUniversidade = codUniversidade;
		this.nome = nome;
		this.data = data;
		this.objetivos = objetivos;
		this.conteudoProgramatico = conteudoProgramatico;
		this.cargaHoraria = cargaHoraria;
		this.investimento = investimento;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodUniversidade() {
		return codUniversidade;
	}
	public void setCodUniversidade(int codUniversidade) {
		this.codUniversidade = codUniversidade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int[] getCodigos() {
		return codigos;
	}
	public void setCodigos(int[] codigos) {
		this.codigos = codigos;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getModulos() {
		return modulos;
	}
	public void setModulos(String modulos) {
		this.modulos = modulos;
	}
	public String getEmenta() {
		return ementa;
	}
	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}
	public String getCertificacoes() {
		return certificacoes;
	}
	public void setCertificacoes(String certificacoes) {
		this.certificacoes = certificacoes;
	}
	public String getObjetivos() {
		return objetivos;
	}
	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
	}
	public String getInvestimento() {
		return investimento;
	}
	public void setInvestimento(String investimento) {
		this.investimento = investimento;
	}
	public String getCargaHoraria() {
		return cargaHoraria;
	}
	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public String getTaxaInscricao() {
		return taxaInscricao;
	}
	public void setTaxaInscricao(String taxaInscricao) {
		this.taxaInscricao = taxaInscricao;
	}
	public String getConteudoProgramatico() {
		return conteudoProgramatico;
	}
	public void setConteudoProgramatico(String conteudoProgramatico) {
		this.conteudoProgramatico = conteudoProgramatico;
	}
}
