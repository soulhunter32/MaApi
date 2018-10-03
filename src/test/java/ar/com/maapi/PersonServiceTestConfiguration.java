package ar.com.maapi;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import ar.com.mavha.service.impl.PersonServiceImpl;

@Profile("test")
@Configuration
public class PersonServiceTestConfiguration {
    @Bean
    @Primary
    public PersonServiceImpl personService() {
        return Mockito.mock(PersonServiceImpl.class);
    }
}
