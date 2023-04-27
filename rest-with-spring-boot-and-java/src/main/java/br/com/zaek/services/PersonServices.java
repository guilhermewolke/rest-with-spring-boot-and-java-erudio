package br.com.zaek.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.zaek.PersonController;
import br.com.zaek.data.vo.v1.PersonVO;
import br.com.zaek.data.vo.v2.PersonVOV2;
import br.com.zaek.exceptions.RequiredObjectIsNullException;
import br.com.zaek.exceptions.ResourceNotFoundException;
import br.com.zaek.mapper.DozerMapper;
import br.com.zaek.mapper.custom.PersonMapper;
import br.com.zaek.model.Person;
import br.com.zaek.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository pr;
	
	@Autowired
	PersonMapper pm;

	public PersonServices() {
		// TODO Auto-generated constructor stub
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person");
		var entity = this.pr.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found with this ID"));
		
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findByID(id)).withSelfRel());

		return vo;
	}

	public List<PersonVO> findAll() {
		var persons = DozerMapper.parseListObjects(pr.findAll(), PersonVO.class);
		persons.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findByID(p.getKey())).withSelfRel()));
		return persons;
	}

	public PersonVO create(PersonVO p) {
		if (p == null) {
			throw new RequiredObjectIsNullException();
		}
		logger.info("Creating a brand new lil' brazilian");
		
		var entity = DozerMapper.parseObject(p, Person.class);
		
		PersonVO vo = DozerMapper.parseObject(this.pr.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findByID(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 p) {
		logger.info("Creating a brand new lil' brazilian 2.0");
		
		var entity = this.pm.convertVOToEntity(p);
		
		return this.pm.convertEntityToVO(this.pr.save(entity));
	}

	public PersonVO update(PersonVO p) {
		if (p == null) {
			throw new RequiredObjectIsNullException();
		}
		logger.info("Updating a lil' brazilian");
		Person person = this.pr.findById(p.getKey()).orElseThrow(() -> new ResourceNotFoundException("No record found with this ID"));
		person.setFirstName(p.getFirstName());
		person.setLastName(p.getLastName());
		person.setAddress(p.getAddress());
		person.setGender(p.getGender());
		
		PersonVO vo = DozerMapper.parseObject(this.pr.save(person), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findByID(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(Long id) {
		logger.info("Removing a lil' brazilian, because the pix was not sent");
		Person person = this.pr.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found with this ID"));
		this.pr.delete(person);

	}

}
