package br.com.zaek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaek.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
