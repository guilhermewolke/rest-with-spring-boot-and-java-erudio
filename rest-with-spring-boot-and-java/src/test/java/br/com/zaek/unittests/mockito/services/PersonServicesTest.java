package br.com.zaek.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.zaek.data.vo.v1.PersonVO;
import br.com.zaek.exceptions.RequiredObjectIsNullException;
import br.com.zaek.model.Person;
import br.com.zaek.repositories.PersonRepository;
import br.com.zaek.services.PersonServices;
import unittests.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {
	
	MockPerson input;
	
	@InjectMocks
	private PersonServices ps;
	
	@Mock
	private PersonRepository pr;

	@BeforeEach
	void setUpMocks() throws Exception {
		input =new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Person p = input.mockEntity(1);
		p.setId(1L);
		when(pr.findById(1L)).thenReturn(Optional.of(p));
		
		var result = ps.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("[</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Endereço #1", result.getAddress());
		assertEquals("Pessoinha #1", result.getFirstName());
		assertEquals("Sobrenome #1", result.getLastName());
		assertEquals("F", result.getGender());
		
	}

	@Test
	void testFindAll() {
		List<Person> list = input.mockEntityList();
		when(pr.findAll()).thenReturn(list);
		
		var people = ps.findAll();
		assertNotNull(people);
		assertEquals(14, people.size());
		
		PersonVO firstPerson = people.get(1);
		
		assertNotNull(firstPerson.getKey());
		assertNotNull(firstPerson.getLinks());
		assertNotNull(firstPerson.toString().contains("[</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Endereço #1", firstPerson.getAddress());
		assertEquals("Pessoinha #1", firstPerson.getFirstName());
		assertEquals("Sobrenome #1", firstPerson.getLastName());
		assertEquals("F", firstPerson.getGender());
		
		PersonVO fourthPerson = people.get(4);
		
		assertNotNull(fourthPerson.getKey());
		assertNotNull(fourthPerson.getLinks());
		assertNotNull(fourthPerson.toString().contains("[</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Endereço #4", fourthPerson.getAddress());
		assertEquals("Pessoinha #4", fourthPerson.getFirstName());
		assertEquals("Sobrenome #4", fourthPerson.getLastName());
		assertEquals("M", fourthPerson.getGender());
		
		PersonVO seventhPerson = people.get(7);
		
		assertNotNull(seventhPerson.getKey());
		assertNotNull(seventhPerson.getLinks());
		assertNotNull(seventhPerson.toString().contains("[</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Endereço #7", seventhPerson.getAddress());
		assertEquals("Pessoinha #7", seventhPerson.getFirstName());
		assertEquals("Sobrenome #7", seventhPerson.getLastName());
		assertEquals("F", seventhPerson.getGender());
	}

	@Test
	void testCreate() {
		Person p = input.mockEntity(1);
		Person persisted = p;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);
		when(this.pr.save(p)).thenReturn(persisted);
		
		var result = this.ps.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("[</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Endereço #1", result.getAddress());
		assertEquals("Pessoinha #1", result.getFirstName());
		assertEquals("Sobrenome #1", result.getLastName());
		assertEquals("F", result.getGender());
	}
	
	@Test
	void testCreateWithNullPerson() {
		Exception ex = assertThrows(RequiredObjectIsNullException.class, () -> {
			ps.create(null);
		});
		
		String expectedMsg = "It is not allowed to persist a null object!";
		String actualMsg = ex.getMessage();
		
		assertTrue(actualMsg.contains(expectedMsg));
	}

	@Test
	void testUpdate() {
		Person p = input.mockEntity(1);
		p.setId(1L);
		Person persisted = p;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);
		when(this.pr.findById(1L)).thenReturn(Optional.of(p));
		when(this.pr.save(p)).thenReturn(persisted);
		
		var result = this.ps.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("[</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Endereço #1", result.getAddress());
		assertEquals("Pessoinha #1", result.getFirstName());
		assertEquals("Sobrenome #1", result.getLastName());
		assertEquals("F", result.getGender());
	}
	
	@Test
	void testUpdateWithNullPerson() {
		Exception ex = assertThrows(RequiredObjectIsNullException.class, () -> {
			ps.update(null);
		});
		
		String expectedMsg = "It is not allowed to persist a null object!";
		String actualMsg = ex.getMessage();
		
		assertTrue(actualMsg.contains(expectedMsg));
	}

	@Test
	void testDelete() {
		Person p = input.mockEntity(1);
		p.setId(1L);
		when(pr.findById(1L)).thenReturn(Optional.of(p));
		
		ps.delete(1L);
	}

}
