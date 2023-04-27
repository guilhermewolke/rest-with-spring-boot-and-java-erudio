package br.com.zaek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaek.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
