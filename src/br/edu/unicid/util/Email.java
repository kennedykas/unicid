package br.edu.unicid.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.edu.unicid.bean.Aluno;
import br.edu.unicid.bean.CursoExtensao;
import br.edu.unicid.bean.Entrevista;
import br.edu.unicid.bean.Professor;

public class Email extends Thread {
   	
	// LOCALHOST ACTVATION EMAIL
	//private String ACTIVATION = "<html><head><meta charset='utf-8'/></head><body style='font-family:Gadget,sans-serif;color:#686868;text-align:center'><br/><img src='http://www.unicid.edu.br/wp-content/uploads/2013/10/logo.png' alt='UNICID'/><h2 style='margin-bottom:30px'>Ative seu cadastro</h2><p style='margin-bottom:60px'>Nï¿½s sï¿½ precisamos confirmar o seu endereï¿½o de email para ativar sua conta. Simplesmente clique no botï¿½o abaixo:</p><a style='padding:20px 40px 20px 40px;background-color:#3BB598;color:#fff;font-size:18px;text-decoration:none;border-radius:5px' href='http://localhost:8080/wecti/faces/autenticaUser.xhtml?";
	private static final String GENERIC_HEADER          = "<html><head><meta charset='utf-8'/></head><body style='font-family:Gadget,sans-serif;color:#686868;text-align:center'><br/><img src='http://www.unicid.edu.br/wp-content/uploads/2013/10/logo.png' alt='UNICID'/>";
	private static final String ACTIVATION              = "<h2 style='margin-bottom:30px'>Ative seu cadastro</h2><p style='margin-bottom:60px'>N&oacute;s s&oacute; precisamos confirmar o seu endere&ccedil;o de email para ativar sua conta. Simplesmente clique no bot&atilde;o abaixo:</p><a style='padding:20px 40px 20px 40px;background-color:#3BB598;color:#fff;font-size:18px;text-decoration:none;border-radius:5px' href='http://www.posgraduacaounicid.com.br/faces/user-tools/autenticaUser.xhtml?";
	private static final String PASSWORD                = "<h2 style='margin-bottom:30px'>Recupera&ccedil;&atilde;o de senha</h2><p style='margin-bottom:60px'>Est&aacute; &eacute; a sua senha:</p><a style='padding:20px 40px 20px 40px;background-color:#3BB598;color:#fff;font-size:18px;text-decoration:none;border-radius:5px'>";
	private static final String INTERVIEW               = "<h2 style='margin-bottom:30px'>Nova entrevista solicitada</h2><p style='margin-bottom:60px'>";
	private static final String COURSE_EXTENSION        = "<h2 style='margin-bottom:30px'>Um novo aluno se matriculou em um curso de extensÃ£o</h2><p style='margin-bottom:60px'>";
	private static final String ADRESS                  = "<p style='margin-top:60px;font-size:13px'><a href='http://unicid.com.br' style='color:#3BB598;text-decoration:none'>UNICID</a> R. Ces&aacute;rio Galero, 448/475 - Tatuap&eacute;, S&atilde;o Paulo - SP, 03071-000</p>";
	private static final String REASON_CHECK_EMAIL      = "<p style='font-size:13px'>Este email foi enviado para voc&ecirc; porque voc&etilde; criou uma conta em ";
	private static final String REASON_PASS_REQUEST     = "<p style='font-size:13px'>Este email foi enviado para voc&etilde; porque voc&etilde; solicitou sua senha em ";
	private static final String REASON_NEW_INTERVIEW    = "<p style='font-size:13px'>Este email foi enviado para voc&ecirc; porque um novo aluno solicitou uma entrevista em ";
	private static final String REASON_COURSE_EXTENSION = "<p style='font-size:13px'>Este email foi enviado para voc&etilde; porque um novo aluno solicitou se matriculou em ";
	private static final String GENERIC_FOOTER          = "<a href='http://www.posgraduacaounicid.com.br' style='color:#3BB598;text-decoration:none'>unicid-pos</a></p></body></html>";
	private static final String DEFAULT_SUBJECT         = "PÓS-GRADUAÇÃO E EXTENSÂO UNICID";
	private static final String DEFAULT_TEACHER_MAIL    = "jadircmj@gmail.com";
	private String message;
	private String receiver;
	private String subject;
	
	public Email() {}

	public void sendEmailVerificacao(Aluno aluno) { 
		message =				 
				GENERIC_HEADER.concat(
				ACTIVATION.concat("i=" + aluno.getData() + "+" + aluno.getEmail() + "&t=a" + "'>Ativar meu cadastro</a>").concat(
				ADRESS).concat(
				REASON_CHECK_EMAIL).concat(
				GENERIC_FOOTER));
		receiver = aluno.getEmail();
		subject = aluno.getNome();
		
		start();
	}
	
	public void sendEmailVerificacao(Professor professor) {
		message =
				GENERIC_HEADER.concat(
				ACTIVATION.concat("i=" + professor.getData() + "+" + professor.getEmailProfessor() + "&t=p" + "'>Ativar meu cadastro</a>").concat(
				ADRESS).concat(
				REASON_CHECK_EMAIL).concat(
				GENERIC_FOOTER));
		receiver = professor.getEmailProfessor();
		subject = professor.getNomeProfessor();
		
		start();
	}
	
	public void sendEmailSenha(Aluno aluno) {
		message =				 
				GENERIC_HEADER.concat(
				PASSWORD.concat(aluno.getSenha() +"</a>").concat(
				ADRESS).concat(
				REASON_PASS_REQUEST).concat(
				GENERIC_FOOTER));
		receiver = aluno.getEmail();
		subject = aluno.getNome();
		
		start();
	}
	
	public void sendEmailSenha(Professor professor) {
		message = 
				GENERIC_HEADER.concat(
				PASSWORD.concat(professor.getSenhaProfessor() +"</a>").concat(
				ADRESS).concat(
				REASON_PASS_REQUEST).concat(
				GENERIC_FOOTER));
		receiver = professor.getEmailProfessor();
		subject = professor.getNomeProfessor();
		
		start();
	}
	
	public void sendEmailEntrevista(Entrevista entrevista) {
		
		message = 
				GENERIC_HEADER.concat(
				INTERVIEW.concat(entrevista.getNomeCurso() +"<br/>"+ entrevista.getNome() +"<br/>"+ entrevista.getEmail() +"<br/>"+ entrevista.getCpf() +"<br/>"+ entrevista.getCelular() +"<br/>"+ "</p>").concat(
				ADRESS.concat(
				REASON_NEW_INTERVIEW.concat(
				GENERIC_FOOTER))));
		
		start();
	}
	
	public void sendEmailCursoExtensao(CursoExtensao cursoExtensao) {
		
		message = 
				GENERIC_HEADER.concat(
				COURSE_EXTENSION.concat(cursoExtensao.getNomeCurso() +"<br/>"+ cursoExtensao.getNome() +"<br/>"+ cursoExtensao.getEmail() +"<br/>"+ cursoExtensao.getCpf() +"<br/>"+ cursoExtensao.getCelular() +"<br/>"+ "</p>").concat(
				ADRESS.concat(
				REASON_COURSE_EXTENSION.concat(
				GENERIC_FOOTER))));
		start();
	}

	@Override
	public void run() {
		
		try {		
			SimpleEmail email = new SimpleEmail();   
		    email.setHostName("smtp.gmail.com");
		    email.setSmtpPort(587);
		    // DESTINATARIOS
		    email.addTo(
		    	(receiver != null) ? receiver : DEFAULT_TEACHER_MAIL,
		    	(subject != null) ? subject : DEFAULT_SUBJECT);
		    email.setFrom("contatoposgraduacaounicid@gmail.com", DEFAULT_SUBJECT);
		    email.setSubject("PÓS-GRADUAÇÃO E EXTENSÂO");
		    // CONTENT TYPE
		    email.addHeader("html", "text/html; charset=ISO-8859-1");
		    // MESSAGE
		    email.setContent(message, "text/html; charset=utf-8");
		    // AUTENTICANDO NO SERVIDOR
		    email.setStartTLSEnabled(true);
		    email.setAuthentication("EMAIL", "PASS");
		    email.send();
		} catch (EmailException e) {} 
		finally {
			interrupt();
		}
	}
}
