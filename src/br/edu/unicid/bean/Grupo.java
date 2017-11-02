package br.edu.unicid.bean;


import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.Part;

public class Grupo {

	private int      codigo; 
	private String   nomeGrupo; 
	private String   nomeArquivo; // nome arquivo com extensao para poder recontrui-lo (tipo byte nao armazena isso)
	private Part     arquivo;     // arquivo submetido pelo usuario
	private byte[]   bytes; 	  // array com os bytes do arquivo (isso que sera salvo no banco) 
	private String   observacao;  
	private Date     data;        // data em que o arquivo foi submetido
	private int      representante; 
	private String[] integrantesArray = new String[14]; // 14 = num max de integrantes (pode ser nulo)
	private String   integrantes;
	private String   senhaGrupo;

	//contrutores
	public Grupo(){}
	
	public Grupo(int codigo, String nomeGrupo, String nomeArquivo, byte[] bytes, String observacao,
			Date data, int representante, String integrantes, String senhaGrupo) {
		super();
		this.codigo        = codigo;
		this.nomeGrupo     = nomeGrupo;
		this.nomeArquivo   = nomeArquivo;
		this.bytes         = bytes;
		this.observacao    = observacao;
		this.data          = data;
		this.representante = representante; 
		this.integrantes   = integrantes;
		this.senhaGrupo    = senhaGrupo;
	}
	
	//getters and setters
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public Part getArquivo() {
		return arquivo;
	}
	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getRepresentante() {
		return representante;
	}
	public void setRepresentante(int representante) {
		this.representante = representante;
	}
	public String[] getIntegrantesArray() {
		return integrantesArray;
	}
	public void setIntegrantesArray(String[] integrantesArray) {
		this.integrantesArray = integrantesArray;
	}
	public String getIntegrantes() {
		setIntegrantes();
		return integrantes;
	}
	public void setIntegrantes() {
		// Caso o array de integrantes esteja vazio entao eu nao estou tentando criar um novo grupo.
		// Se esse trecho nao fosse interrompido, em uma listagem ele limparia os integrantes
		if(integrantesArray[0] == null)
			return;
		// Alguns ajustes na String para que ela seja devidamente formatada
		this.integrantes = Arrays.toString(integrantesArray).replace(", , ", "").replace(" ", "").replace("[", "").replace("]", "");
//				
//		if(this.integrantes.endsWith(",")) {
//			this.integrantes.substring(0, this.integrantes.length() - 1);
//		}
		if(this.integrantes.length() == 1)
			this.integrantes = "";
	}
	public void setIntegrantes(String integrantes) {
		this.integrantes = integrantes;
	}
	public String getSenhaGrupo() {
		return senhaGrupo;
	}
	public void setSenhaGrupo(String senhaGrupo) {
		this.senhaGrupo = senhaGrupo;
	}
}

