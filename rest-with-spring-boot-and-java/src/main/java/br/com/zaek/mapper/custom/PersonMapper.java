package br.com.zaek.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.zaek.data.vo.v2.PersonVOV2;
import br.com.zaek.model.Person;

@Service
public class PersonMapper {

	public PersonVOV2 convertEntityToVO(Person p) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(p.getId());
		vo.setFirstName(p.getFirstName());
		vo.setLastName(p.getLastName());
		vo.setAddress(p.getAddress());
		vo.setGender(p.getGender());
		vo.setBirthDate(new Date());
		
		return vo;
	}
	
	public Person convertVOToEntity(PersonVOV2 vo) {
		Person p = new Person();
		p.setId(vo.getId());
		p.setFirstName(vo.getFirstName());
		p.setLastName(vo.getLastName());
		p.setAddress(vo.getAddress());
		p.setGender(vo.getGender());
		//p.setBirthDate(new Date());
		
		return p;
	}

}
