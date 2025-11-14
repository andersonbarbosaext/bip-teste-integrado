package com.example.backend.config;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ejb.BeneficioEjbServiceRemote;


@Configuration
public class EjbClientConfig {
	@Bean
	Context jndiContext() throws Exception {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		p.put(Context.PROVIDER_URL, "remote+http://localhost:8080");	
		p.put("jboss.naming.client.ejb.context", true);
		return new InitialContext(p);
	}

	@Bean
	BeneficioEjbServiceRemote accountService(Context ctx) throws Exception {		
		String jndi = "ejb:/ejb-module/BeneficioEjbServiceBean!com.example.ejb.BeneficioEjbServiceRemote";
		return (BeneficioEjbServiceRemote) ctx.lookup(jndi);
	}
}
