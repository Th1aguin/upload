package br.com.hotmart.mb;

import br.com.hotmart.model.Arquivo;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

@ManagedBean
public class ArquivoMb {
	
	private static final String SERVIDOR = "http://localhost:8080/upload";
	
	public List<Arquivo> getArquivos() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(SERVIDOR+"/rest/arquivo/lista");
		Response response = target.request().get();
		List<Arquivo> lista = response.readEntity(new GenericType<List<Arquivo>>() {});
		return lista;
  }
}
