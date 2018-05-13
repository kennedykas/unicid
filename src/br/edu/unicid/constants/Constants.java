package br.edu.unicid.constants;

public class Constants {

	public static final String WRONG_EMAIL              = "EMAIL incorreto.";
	public static final String WRONG_PASS               = "SENHA incorreta.";
	public static final String WRONG_RGM                = "RGM incorreto.";
	public static final String FORGET_CHECK_RECAPTCHA   = "QUASE só falta você dizer que não é um robô!";
	public static final String RECAPTCHA_FAILED         = "CLIQUE EM: 'não sou um robô', para confirmar que você é uma pessoa!";
	public static final String EMAIL_WAS_SENT_WITH_PASS = "ENVIAMOS um email com a sua senha, para o endereço cadastrado. dúvidas entre em contato conosco: jadircmj@hotmail.com";
	public static final String NAME_NOT_FOUND           = "NOME não encontrado (maiúsculas e minúsculas não interferem), dúvidas entre em contato conosco: jadircmj@hotmail.com";
	public static final String SOMETHING_WENT_WRONG     = "Algo não aconteceu como o esperado!";
	public static final String FACE_MESSAGES_ID         = "messages";
	public static final String SUCCESS                  = "ok";
	public static final String READY                    = "Pronto!";
	public static final String EMAIL                    = "email";
	public static final String PASSWORD                 = "senha";
	public static final String NAME                     = "nome";
	public static final String RGM                      = "rgm";
	public static final String UNVERIFIED               = "unverified";
	public static final String UNKNOWN_RGM              = "RGM não coincide com o nome, dúvidas entre em contato conosco: jadircmj@hotmail.com";
	public static final String URL_PARAMETER            = ".xhtml?faces-redirect=true&toast=";
	public static final String SAVED_WITH_SUCCESS       = URL_PARAMETER + "Salvo com sucesso";
	public static final String CHANGES_HAVE_BEEN_SAVED  = URL_PARAMETER + "Suas alterações foram salvas";
	public static final String TOAST_DELETED_WITH_SUCCESS = URL_PARAMETER + "Excluído com sucesso!";
	
	public static final String TOAST_SOMETHING_WENT_WRONG = URL_PARAMETER + "Algo não aconteceu como o esperado!";
	public static final String TOAST_GENERIC_ERROR        = URL_PARAMETER + "Erro! Tente novamente mais tarde.";
	
	public static final String PAGE_EMAIL_SENT          = "/user-tools/emailEnviado";
	
	public static final String PAGE_TEST_DETAILS        = "detalhesAvaliacao";
	
	public static final String PAGE_SUCCESS_INTERVIEW        = "/success/sucessoEntrevista";
	public static final String PAGE_NEW_INTERVIEW            = "/create/novaEntrevista";
	public static final String PAGE_LIST_INTERVIEWS          = "/list/listaEntrevistas";
	public static final String PAGE_SUCCESS_COURSE_EXTENSION = "/success/sucessoCursoExtensao";
	public static final String PAGE_NEW_COURSE_EXTENSIVE     = "/create/novoCursoExtensao";

	public static final String PAGE_NEW_PROFSSOR         = "/create/novoProfessor";
	public static final String PAGE_LIST_QUESTIONS       = "/list/listaQuestoes";
	public static final String PAGE_LOGIN_PROFSSOR       = "/login/loginProfessor";
	public static final String PAGE_UPDATE_PROFESSOR     = "/update/alterarProfessor";
	public static final String PAGE_RECOVER_PROF_PASS    = "/user-tools/recuperarSenhaProfessor";
	
	public static final String PAGE_NEW_STUDENT          = "/create/novoAluno";
	public static final String PAGE_LOGIN_STUDENT        = "/login/loginAluno";
	public static final String PAGE_RECOVER_STUDENT_PASS = "/user-tools/recuperarSenhaAluno";
	public static final String PAGE_UPDATE_STUDENT_INFO  = "/update/alterarAluno";
	
	public static final String PAGE_LIST_DISCIPLINES     = "/list/listaDisciplinas";
	public static final String PAGE_NEW_DISCIPLINE       = "/list/novaDisciplina";
	public static final String PAGE_UPDATE_DISCIPLINE    = "/list/alterarDisciplina";
		
	public static final String PAGE_LIST_TESTS_PROF     = "/list/listaProvasProfessor";
	public static final String PAGE_LIST_TESTS_STUDENT  = "/list/listaProvasAluno";
	public static final String PAGE_SHOW_TEST           = "/see/verProvaAluno";
	public static final String PAGE_UPDATE_TEST         = "/update/alterarProva";

	public static final String PAGE_LIST_MADE_TESTS     = "/list/listaProvasRealizadasAluno";
	public static final String PAGE_SEE_CORRECT_ANSWERS = "/see/gabaritoAluno";
	
	public static final String PAGE_NEW_QUESTION        = "/create/novaQuestao";
	public static final String PAGE_UPDATE_QUESTION     = "/update/alterarQuestao";
	
	public static final String PAGE_LIST_DEPOSITS_PROFESSOR = "/list/listDepositsProfessor";
	public static final String PAGE_NEW_DEPOSIT             = "/create/newDeposit";
	
}
