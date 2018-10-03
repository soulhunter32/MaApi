package ar.com.maapi.restendpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.maapi.PersonServiceTestConfiguration;
//import ar.com.maapi.PersonServiceTestConfiguration;
import ar.com.mavha.MaapiApplication;
import ar.com.mavha.dto.PersonDTO;
import ar.com.mavha.exception.ExistingPersonCollitionException;
import ar.com.mavha.exception.PersonNotFoundException;
import ar.com.mavha.model.Person;
import ar.com.mavha.repository.PersonRepository;
import ar.com.mavha.restendpoint.impl.PersonEndpointImpl;
import ar.com.mavha.restendpoint.request.PersonRequest;
import ar.com.mavha.restendpoint.response.PersonResponse;
import ar.com.mavha.service.impl.PersonServiceImpl;
import ar.com.mavha.wrapper.PersonRestWrapper;
import ar.com.mavha.wrapper.PersonWrapper;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes=MaapiApplication.class)
@ContextConfiguration(classes = { PersonServiceTestConfiguration.class })
public class PersonEndpointTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@InjectMocks
	@Autowired
	private PersonServiceImpl personService;

	@InjectMocks
	private PersonEndpointImpl endpoint;

	@Mock
	private PersonRepository personRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getRequest_shouldReturnNoResults() {
		PersonServiceImpl personServiceMock = Mockito.mock(PersonServiceImpl.class);
		Mockito.when(personServiceMock.getAllPerson()).thenReturn(new ArrayList<PersonDTO>());

		ResponseEntity<PersonDTO[]> response = testRestTemplate.getForEntity("/personas", PersonDTO[].class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().length == 0);
	}

	@Test
	public void getRequest_shouldReturnTwoResults() {
		PersonDTO personOne = new PersonDTO();
		personOne.setDni(111112L);
		PersonDTO personTwo = new PersonDTO();
		personTwo.setDni(22223L);

		ArrayList<PersonDTO> personList = new ArrayList<PersonDTO>();
		personList.add(personOne);
		personList.add(personTwo);

		Mockito.when(personService.getAllPerson()).thenReturn(personList);

		ResponseEntity<PersonDTO[]> response = testRestTemplate.getForEntity("/personas", PersonDTO[].class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(Long.parseLong("111112"), ((PersonDTO)response.getBody()[0]).getDni().longValue());
		assertEquals(Long.parseLong("22223"), ((PersonDTO)response.getBody()[1]).getDni().longValue());
	}

	@Test
	public void getRequestForId_shouldReturnOnePersonWithSameId() throws PersonNotFoundException {
		Long dni = 30303030L;

		PersonRequest personRequest = new PersonRequest();
		personRequest.setDni(dni);
		personRequest.setName("Juan");
		personRequest.setLastname("Sosa");
		personRequest.setAge(25);

		PersonDTO personDTO = new PersonDTO();
		personDTO.setDni(dni);
		personDTO.setName("Juan");
		personDTO.setLastname("Sosa");
		personDTO.setAge(25);

		Mockito.when(personService.getPersonById(dni)).thenReturn(personDTO);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PersonRequest> requestEntity = new HttpEntity<PersonRequest>(personRequest, headers);

		testRestTemplate.postForObject("/personas", requestEntity, PersonResponse.class);

		ResponseEntity<PersonResponse> response = testRestTemplate.getForEntity("/personas" + "/" + dni, PersonResponse.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(dni, response.getBody().getDni());
	}

	@Test
	public void getRequestForId_shouldReturnNoPersonFoundVia404StatusCode() {
		Long dni = 1L;
		ResponseEntity<String> response = testRestTemplate.getForEntity("/personas" + "/" + dni, String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void postRequestWithAllData_shouldReturnNewCreatedResourceThatMatchesRequestData() throws ExistingPersonCollitionException {
		PersonRequest personRequest = new PersonRequest();
		personRequest.setDni(10366544L);
		personRequest.setName("Juan");
		personRequest.setLastname("Sosa");
		personRequest.setAge(25);

		PersonDTO personDTO = new PersonDTO();
		personDTO.setDni(10366544L);
		personDTO.setName("Juan");
		personDTO.setLastname("Sosa");
		personDTO.setAge(25);

		Mockito.when(personService.createPerson(Mockito.any(PersonDTO.class))).thenReturn(personDTO);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PersonRequest> requestEntity = new HttpEntity<PersonRequest>(personRequest, headers);

		PersonResponse response = testRestTemplate.postForObject("/personas", requestEntity, PersonResponse.class);

		assertEquals(personRequest.getDni(), response.getDni());
		assertEquals(personRequest.getName(), response.getName());
		assertEquals(personRequest.getLastname(), response.getLastname());
		assertEquals(personRequest.getAge(), response.getAge());
	}

	@Test
	public void postRequestWithInvalidData_shouldReturnErrorResponse() {
		PersonRequest personRequest = new PersonRequest();
		personRequest.setDni(10366544L);
		personRequest.setName("Juan");
		personRequest.setAge(25);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PersonRequest> requestEntity = new HttpEntity<PersonRequest>(personRequest, headers);

		ResponseEntity<String> response = testRestTemplate.postForEntity("/personas", requestEntity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void postRequestWithNoData_shouldReturnErrorResponse() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PersonRequest> requestEntity = new HttpEntity<PersonRequest>(null, headers);

		ResponseEntity<String> response = testRestTemplate.postForEntity("/personas", requestEntity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
}
