# Configuração do EJB  

## JNDI 
	ejb:/ejb-module/BeneficioEjbServiceBean!com.example.ejb.BeneficioEjbServiceRemote

## Configuração do server WILDFLY:

	$WILDFLY_HOME/standalone/configuration/standalone.xml

	<datasource jndi-name="java:/PostgresDS" pool-name="PostgresDS" enabled="true" use-java-context="true">
	    <connection-url>jdbc:postgresql://localhost:5432/beneficiodb</connection-url>
	    <driver>postgresql</driver>
	    <pool>
	        <min-pool-size>5</min-pool-size>
	        <max-pool-size>20</max-pool-size>
	    </pool>
	    <security>
	        <user-name>user</user-name>
	        <password>password</password>
	    </security>
	</datasource>
	<drivers>
	    <driver name="postgresql" module="org.postgresql"/>
	</drivers>

##  Configuração do modulo
	WILDFLY_HOME/modules/org/postgresql/main/module.xml


	<?xml version="1.0" encoding="UTF-8"?>
		<module name="org.postgresql" xmlns="urn:jboss:module:1.9">

	    <resources>
	        <resource-root path="postgresql-42.7.7.jar"/>
	    </resources>
	
	    <dependencies>
	        <module name="javax.api" optional="true"/>
	        <module name="javax.transaction.api" optional="true"/>
	        <module name="java.logging"/>
	        <module name="java.sql"/>
	
	        <!-- DEPENDÊNCIAS NECESSÁRIAS PARA HIBERNATE 6 -->
	        <module name="org.jboss.threads"/>
	        <module name="org.slf4j"/>
	    </dependencies>
	</module>

## Baixar o Jar e incluir na pasta main como abaixo:
	WILDFLY_HOME/modules/org/postgresql/main/postgresql-42.x.x.jar
