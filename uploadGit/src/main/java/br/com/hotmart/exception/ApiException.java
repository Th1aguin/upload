package br.com.hotmart.exception;

public class ApiException extends Exception{

	private static final long serialVersionUID = 1L;
	private int codigo;
	
	public ApiException (int codigo, String msg) {
		super(msg);
		this.codigo = codigo;
	}
}
