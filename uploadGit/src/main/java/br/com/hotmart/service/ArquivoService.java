package br.com.hotmart.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONObject;

import br.com.hotmart.exception.ApiException;
import br.com.hotmart.model.Arquivo;
import br.com.hotmart.model.StatusEnum;


@Api(value = "/arquivo", description = "servicos de upload")
@Path("/arquivo")  
public class ArquivoService {

	private final String UPLOADED_FILE_PATH = "C:\\";
	public static  Map<String, Arquivo> map = new HashMap<String,Arquivo>();
	
	
	@POST
	@Path("/upload")
	@ApiOperation(value = "Adiciona um novo arquivo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Erro ao processar o download") })
	public Response upload(
			@ApiParam(value = "Formulario com arquivo e usuario", required = true) MultipartFormDataInput input) throws ApiException{

		long inicio = System.currentTimeMillis();
		String nomeArquivo ="";
		String nome = "";
		JSONObject json = new JSONObject();
		Arquivo arquivo = null;
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		 try {
			
			nome = uploadForm.get("name").get(0).getBody(String.class,null);
			String usuario = uploadForm.get("usuario").get(0).getBody(String.class,null);
			Integer indice = uploadForm.get("chunk").get(0).getBody(Integer.class,null);
			Integer qtdPedaco = uploadForm.get("chunks").get(0).getBody(Integer.class,null);
			InputStream inputStream = uploadForm.get("file").get(0).getBody(InputStream.class,null);
			
			
		    arquivo  = map.get(nome);
			if(arquivo == null){
				arquivo = new Arquivo();
				arquivo.setBlocos(qtdPedaco);
				arquivo.setNome(nome);
				arquivo.setStatus(StatusEnum.ANDAMENTO);
				arquivo.setMiliSegundos(0l);
				arquivo.setLink("rest/arquivo/download/"+nome);
				arquivo.setUsuario(usuario);
			}
			
		 
			byte [] bytes = IOUtils.toByteArray(inputStream);
			
			boolean inteiro = false;
			nomeArquivo = UPLOADED_FILE_PATH+nome;
			File theFile = new File(nomeArquivo);
			if (qtdPedaco == 1){
				inteiro = true;
			}
			if (!inteiro){
				FileUtils.writeByteArrayToFile(theFile, bytes, true);
			}else{
				FileUtils.writeByteArrayToFile(theFile, bytes);
			}
			
			json.put("mensagem", "Parte ("+(indice+1)+"/"+qtdPedaco+") do arquivo "+nome+" foi processada com sucesso");
			
			if(indice == (qtdPedaco-1)){
				arquivo.setStatus(StatusEnum.CONCLUIDO);	
			}
			long fim = System.currentTimeMillis();
			long dif = (fim - inicio);
			arquivo.setMiliSegundos(arquivo.getMiliSegundos()+dif);
				
		 } catch (IOException e1) {
			 arquivo.setStatus(StatusEnum.FALHA);
			 throw new ApiException(500, "Erro ao processar o download");
		 }
		 map.put(nome, arquivo);
		 
		return Response.status(200).entity(json.toString()).build();
	}
	
	@GET
	@Produces("application/json")
	@Path("/lista")
	@ApiOperation(value = "Lista com os arquivos cadastrados", 
	    notes = "Lista os arquivos",
	    response = Arquivo.class, 
	    responseContainer = "List")
	public Response getDownloads() {
		List<Arquivo> list = new ArrayList<Arquivo>(ArquivoService.map.values());
		return Response.status(200).entity(list).build();
	} 
	
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/download/{nome}")
	@ApiOperation(value = "Download dos Arquivos pelo nome fornecido", 
    notes = "Faz o download dos arquivos pelo nome fornecido")
	public Response download(@ApiParam(value = "Nome do arquivo", required = true) @PathParam("nome") String nome) {
        File file = new File(UPLOADED_FILE_PATH+nome);  
		return Response.status(200).entity((Object) file).header("Content-Disposition", "attachment; filename=\"" + nome + "\"" )
                .build();

	} 
	


}
