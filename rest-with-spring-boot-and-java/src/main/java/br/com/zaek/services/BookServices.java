package br.com.zaek.services;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.zaek.BookController;
import br.com.zaek.data.vo.v1.BookVO;
import br.com.zaek.exceptions.RequiredObjectIsNullException;
import br.com.zaek.exceptions.ResourceNotFoundException;
import br.com.zaek.mapper.DozerMapper;
import br.com.zaek.mapper.custom.BookMapper;
import br.com.zaek.model.Book;
import br.com.zaek.repositories.BookRepository;

@Service
public class BookServices {
	
	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository rep;
	
	@Autowired
	BookMapper mapper;

	public BookServices() {
		// TODO Auto-generated constructor stub
	}
	
	public BookVO findByID(Integer id) {
		logger.info("Finding one book with id '" + id + "'");
		
		var entity = this.rep.findById(id).orElseThrow(() -> new ResourceNotFoundException("No book found with ID '" + id + "'"));
		
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findByID(id)).withSelfRel());
		vo.add(linkTo(methodOn(BookController.class).update(id, vo)).withSelfRel());
		vo.add(linkTo(methodOn(BookController.class).delete(id)).withSelfRel());
		
		return vo;
	}
	
	public List<BookVO> findAll() {
		logger.info("List all of the books");
		var books = DozerMapper.parseListObjects(this.rep.findAll(), BookVO.class);
		books.stream()
			.forEach(p -> {
				p.add(linkTo(methodOn(BookController.class).findByID(p.getKey())).withSelfRel());
				p.add(linkTo(methodOn(BookController.class).update(p.getKey(), p)).withSelfRel());
				p.add(linkTo(methodOn(BookController.class).delete(p.getKey())).withSelfRel());
			});
		return books;
	}
	
	public BookVO create(BookVO b) {
		if (b == null) {
			throw new RequiredObjectIsNullException();
		}
		
		logger.info("Creating a brand new book");
		
		var entity = DozerMapper.parseObject(b, Book.class);
		
		BookVO vo = DozerMapper.parseObject(this.rep.save(entity), BookVO.class);
		
		vo.add(linkTo(methodOn(BookController.class).findByID(vo.getKey())).withSelfRel());
		vo.add(linkTo(methodOn(BookController.class).update(vo.getKey(), vo)).withSelfRel());
		vo.add(linkTo(methodOn(BookController.class).delete(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public BookVO update(BookVO b) {
		if (b == null) {
			throw new RequiredObjectIsNullException();
		}
		
		logger.info("Updating a book with id '" + b.getKey() + "'");
		Book book = this.rep.findById(b.getKey()).orElseThrow(() -> new ResourceNotFoundException("No book found with ID '" + b.getKey() + "'"));
		book.setAuthor(b.getAuthor());
		book.setId(b.getKey());
		book.setLaunchDate(b.getLaunchDate());
		book.setPrice(b.getPrice());
		book.setTitle(b.getTitle());
		
		BookVO vo = DozerMapper.parseObject(this.rep.save(book), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findByID(vo.getKey())).withSelfRel());
		vo.add(linkTo(methodOn(BookController.class).update(vo.getKey(), vo)).withSelfRel());
		vo.add(linkTo(methodOn(BookController.class).delete(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public void delete(Integer id) {
		logger.info("Removing a book with id '" + id + "'");
		Book b = this.rep.findById(id).orElseThrow(() -> new ResourceNotFoundException("No book found with ID '" + id + "'"));
		this.rep.delete(b);
	}

}
