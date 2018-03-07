package br.com.hotmart.model;

public class Arquivo {

	private String nome;
	private String usuario;
	private StatusEnum status;
	private Long milisegundos;
	private Integer blocos;
	private String link;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public Long getMiliSegundos() {
		return milisegundos;
	}
	public void setMiliSegundos(Long milisegundos) {
		this.milisegundos = milisegundos;
	}
	public Integer getBlocos() {
		return blocos;
	}
	public void setBlocos(Integer blocos) {
		this.blocos = blocos;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arquivo other = (Arquivo) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
