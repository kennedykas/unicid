package br.edu.unicid.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Deposit {
	
	private int        code;
	private int        professor;
	private int        student;
	private LocalDate  date;
	private int        type;
	private BigDecimal value;
	private String     comments;
	
	public Deposit() {}

	public Deposit(int code, int professor, int student, LocalDate date, int type, BigDecimal value, String comments) {
		super();
		this.code      = code;
		this.professor = professor;
		this.student   = student;
		this.date      = date;
		this.type      = type;
		this.value     = value;
		this.comments  = comments;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getProfessor() {
		return professor;
	}
	public void setProfessor(int professor) {
		this.professor = professor;
	}
	public int getStudent() {
		return student;
	}
	public void setStudent(int student) {
		this.student = student;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
