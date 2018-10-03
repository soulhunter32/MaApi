package ar.com.mavha.restendpoint.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PersonRequest {

	@NotNull(message = "DNI is mandatory")
	@Min(value = 1000000, message = "DNI should be greater than 1000000")
	@Max(value = 100000000, message = "DNI should be lower than 100000000")
	private Long dni;
	
	@NotNull(message = "Name is mandatory")
	@Pattern(regexp = "^[A-Za-z ]*$")
	@Size(min = 3, max = 30, message = "The name should me between 3 and 20 characters long")
	private String name;
	
	@NotNull(message = "Lastname is mandatory")
	@Pattern(regexp = "^[A-Za-z ]*$")
	@Size(min = 3, max = 30, message = "The lastname should me between 3 and 20 characters long")
	private String lastname;
	
	@Min(value = 1, message = "Age should not be less than 1")
    @Max(value = 110, message = "Age should not be greater than 110")
	private Integer age;

	public Long getDni() {
		return dni;
	}
	public void setDni(Long dni) {
		this.dni = dni;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
