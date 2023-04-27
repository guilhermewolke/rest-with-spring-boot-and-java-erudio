package unittests;

import java.util.ArrayList;
import java.util.List;

import br.com.zaek.data.vo.v1.PersonVO;
import br.com.zaek.model.Person;

public class MockPerson {
	
	public List<Person> mockEntityList() {
		List<Person> people = new ArrayList<Person>();
		for (int i = 0; i < 14; i++) {
			people.add(mockEntity(i));
		}
		return people;
	}
	
	public List<PersonVO> mockVOList() {
		List<PersonVO> persons = new ArrayList<PersonVO>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockVO(i));
		}
		return persons;
	}
	
	public Person mockEntity(Integer number) {
		Person p = new Person();
		p.setAddress("Endereço #" + number);
		p.setFirstName("Pessoinha #" + number);
		p.setGender((number %2 == 0 ? "M":"F"));
		p.setId(number.longValue());
		p.setLastName("Sobrenome #" + number);
		return p;
	}
	
	public PersonVO mockVO(Integer number) {
		PersonVO p = new PersonVO();
		p.setAddress("Endereço #" + number);
		p.setFirstName("Pessoinha #" + number);
		p.setGender((number %2 == 0 ? "M":"F"));
		p.setKey(number.longValue());
		p.setLastName("Sobrenome #" + number);
		return p;
	}

}
