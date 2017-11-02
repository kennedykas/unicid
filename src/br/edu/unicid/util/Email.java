package br.edu.unicid.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.edu.unicid.bean.Aluno;
import br.edu.unicid.bean.Professor;

public class Email {
   	
	//LOCALHOST
	//private String ACTIVATION = "<html><head><meta charset='utf-8'/></head><body style='font-family:Gadget,sans-serif;color:#686868;text-align:center'><br/><img src='http://www.unicid.edu.br/wp-content/uploads/2013/10/logo.png' alt='UNICID'/><h2 style='margin-bottom:30px'>Ative seu cadastro</h2><p style='margin-bottom:60px'>Nós só precisamos confirmar o seu endereço de email para ativar sua conta. Simplesmente clique no botão abaixo:</p><a style='padding:20px 40px 20px 40px;background-color:#3BB598;color:#fff;font-size:18px;text-decoration:none;border-radius:5px' href='http://localhost:8080/wecti/faces/autenticaUser.xhtml?";
	private final String GENERIC_HEADER = "<html><head><meta charset='utf-8'/></head><body style='font-family:Gadget,sans-serif;color:#686868;text-align:center'><br/><img src='http://www.unicid.edu.br/wp-content/uploads/2013/10/logo.png' alt='UNICID'/>";
	private final String ACTIVATION = "<h2 style='margin-bottom:30px'>Ative seu cadastro</h2><p style='margin-bottom:60px'>Nós só precisamos confirmar o seu endereço de email para ativar sua conta. Simplesmente clique no botão abaixo:</p><a style='padding:20px 40px 20px 40px;background-color:#3BB598;color:#fff;font-size:18px;text-decoration:none;border-radius:5px' href='http://www.posgraduacaounicid.com.br/unicid-19-08-17/user-tools/autenticaUser.xhtml?";
	private final String PASSWORD = "<h2 style='margin-bottom:30px'>Recuperação de senha</h2><p style='margin-bottom:60px'>Está é a sua senha:</p><a style='padding:20px 40px 20px 40px;background-color:#3BB598;color:#fff;font-size:18px;text-decoration:none;border-radius:5px'>";
	private final String ADRESS = "<p style='margin-top:60px;font-size:13px'><a href='http://unicid.com.br' style='color:#3BB598;text-decoration:none'>UNICID</a> R. Cesário Galero, 448/475 - Tatuapé, São Paulo - SP, 03071-000</p>";
	private final String REASON_CHECK_EMAIL = "<p style='font-size:13px'>Este email foi enviado para você porque você criou uma conta em ";
	private final String REASON_PASS_REQUEST = "<p style='font-size:13px'>Este email foi enviado para você porque você solicitou sua senha em ";
	private final String GENERIC_FOOTER = "<a href='http://www.posgraduacaounicid.com.br/wecti' style='color:#3BB598;text-decoration:none'>wecti</a></p></body></html>";
	
	public Email() {}
	
	public boolean sendEmailVerificacao(Aluno aluno) { // EMAIL DE VERIFICACAO PARA ALUNO 
		return send(
				aluno.getEmail(), aluno.getNome(), 
				this.GENERIC_HEADER.concat(
				this.ACTIVATION.concat("i=" + aluno.getData() + "+" + aluno.getEmail() + "&t=a" + "'>Ativar meu cadastro</a>").concat(
				this.ADRESS).concat(
				this.REASON_CHECK_EMAIL).concat(
				this.GENERIC_FOOTER)));
	}
	public boolean sendEmailVerificacao(Professor professor) { // EMAIL DE VERIFICACAO PARA PROFESSOR
		return send(
				professor.getEmailProfessor(), professor.getNomeProfessor(), 
				this.GENERIC_HEADER.concat(
				this.ACTIVATION.concat("i=" + professor.getData() + "+" + professor.getEmailProfessor() + "&t=p" + "'>Ativar meu cadastro</a>").concat(
				this.ADRESS).concat(
				this.REASON_CHECK_EMAIL).concat(
				this.GENERIC_FOOTER)));
	}
	public boolean sendEmailSenha(Aluno aluno) { // EMAIL DE SENHA PARA ALUNO
		return send(
				aluno.getEmail(), aluno.getNome(), 
				this.GENERIC_HEADER.concat(
				this.PASSWORD.concat(aluno.getSenha() +"</a>").concat(
				this.ADRESS).concat(
				this.REASON_PASS_REQUEST).concat(
				this.GENERIC_FOOTER)));
	}
	public boolean sendEmailSenha(Professor professor) { // EMAIL DE SENHA PARA PROFESSOR
		return send(
				professor.getEmailProfessor(), professor.getNomeProfessor(), 
				this.GENERIC_HEADER.concat(
				this.PASSWORD.concat(professor.getSenhaProfessor() +"</a>").concat(
				this.ADRESS).concat(
				this.REASON_PASS_REQUEST).concat(
				this.GENERIC_FOOTER)));
	}
	
	public boolean send(String emailAddress, String nome, String message) { 
		int i = 0; 
		// TENTA ENVIAR O EMAIL POR 4 VEZES PODE SER UM POUCO DEMORADO PARA O USER
		while(true) { 
			try {		
				SimpleEmail email = new SimpleEmail();   
			    email.setHostName("smtp.gmail.com");
			    email.setSmtpPort(587);
			    // DESTINATARIOS
			    email.addTo(emailAddress, nome);
			    // EMAIL Q ENVIA OS EMAILS 
			    email.setFrom("kennedy0064@gmail.com", "PÓS-GRADUÇÃO UNICID");
			    // SUBJECT
			    email.setSubject("PÓS-GRADUAÇÃO UNICID");
			    // CONTENT TYPE
			    email.addHeader("html", "text/html; charset=ISO-8859-1");
			    // MESSAGE
			    email.setContent(message, "text/html; charset=utf-8");
			    // AUTENTICANDO NO SERVIDOR
			    email.setStartTLSEnabled(true);
			    email.setAuthentication("kennedy0064@gmail.com", "kaskennedy");
			    // SENDING
			    email.send();
			    // AVISA QUE O(S) EMAIL(S) FORAM ENVIADOS COM SUCESSO
			    return true;
			} catch (EmailException e) {
				// VERIFICA SE O NUMERO MAXIMO DE TENTATIVAS (4) FOI ATINGIDO, DO CONTRARIO TENTA NOVAMENTE
				if(++i == 4) return false;
			}
		}
	}
}
