package ar.com.mavha.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import ar.com.mavha.mapper.ConstraintViolationMapper;
import ar.com.mavha.mapper.EmptyBodyMapper;
import ar.com.mavha.mapper.ExistingPersonCollitionMapper;
import ar.com.mavha.mapper.PersonNotFoundMapper;
import ar.com.mavha.restendpoint.impl.PersonEndpointImpl;


@Configuration
public class RestConfig extends ResourceConfig{

	public RestConfig() {
		register(PersonEndpointImpl.class).
		register(PersonNotFoundMapper.class).
		register(EmptyBodyMapper.class).
		register(ExistingPersonCollitionMapper.class).
		register(ConstraintViolationMapper.class);
	}
}
