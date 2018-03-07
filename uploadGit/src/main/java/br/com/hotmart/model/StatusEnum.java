package br.com.hotmart.model;

public enum StatusEnum {
	

	ANDAMENTO("Em Andamento"),
	FALHA("Falha"),
	CONCLUIDO("Concluido");
	
	private String descricao;
	
	StatusEnum(String descricao){
		this.descricao=descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
