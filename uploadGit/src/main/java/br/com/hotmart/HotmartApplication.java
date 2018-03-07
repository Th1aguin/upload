package br.com.hotmart;

import io.swagger.jaxrs.config.BeanConfig;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.hotmart.service.ArquivoService;

public class HotmartApplication extends Application {
	HashSet<Object> singletons = new HashSet<Object>();
    
	public HotmartApplication() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Gerenciador de Arquivos");
        beanConfig.setDescription("API desenvolvida para o processo seletivo da hotmart");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/upload/rest");
        beanConfig.setResourcePackage("br.com.hotmart.service");
        beanConfig.setScan(true);
    }
	
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<Class<?>>();

        resources.add(ArquivoService.class);
        
        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return resources;
    }
	
	@Override
    public Set<Object> getSingletons() {
        return singletons;
    }
    
    
}
