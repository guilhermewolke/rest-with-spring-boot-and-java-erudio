package br.com.zaek.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import br.com.zaek.data.vo.v1.BookVO;
import br.com.zaek.exceptions.RequiredObjectIsNullException;
import br.com.zaek.model.Book;
import br.com.zaek.repositories.BookRepository;
import br.com.zaek.services.BookServices;
import unittests.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServicesTest {
	
	MockBook input;
	
	@InjectMocks
	private BookServices service;
	
	@Mock
	private BookRepository rep;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindByID() throws Exception {
		Book b = input.mockEntity(1);
		b.setId(1);
		when(rep.findById(1)).thenReturn(Optional.of(b));
		
		var book = service.findByID(1);
		assertNotNull(book);
		assertNotNull(book.getKey());
		assertNotNull(book.getLinks());
		assertNotNull(book.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Suma Teológica - Tomo I", book.getTitle());
		assertEquals("Santo Tomás de Aquino", book.getAuthor());
		assertEquals(1, book.getKey());
		assertEquals(150.00, book.getPrice());
		
	}
	
	@Test
	void testFindAll() throws Exception {
		List<Book> list = input.mockEntityList();
		when(rep.findAll()).thenReturn(list);
		
		var books = service.findAll();
		assertNotNull(books);
		assertEquals(5, books.size());
		
		BookVO firstTome = books.get(0);
		assertNotNull(firstTome.getKey());
		assertNotNull(firstTome.getLinks());
		assertNotNull(firstTome.getAuthor());
		assertNotNull(firstTome.getLaunchDate());
		assertNotNull(firstTome.getPrice());
		assertNotNull(firstTome.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Santo Tomás de Aquino", firstTome.getAuthor());
		assertEquals(1, firstTome.getKey());
		assertEquals(150, firstTome.getPrice());
		assertEquals("Suma Teológica - Tomo I", firstTome.getTitle());
		
		BookVO thirdTome = books.get(2);
		assertNotNull(thirdTome.getKey());
		assertNotNull(thirdTome.getLinks());
		assertNotNull(thirdTome.getAuthor());
		assertNotNull(thirdTome.getLaunchDate());
		assertNotNull(thirdTome.getPrice());
		assertNotNull(thirdTome.toString().contains("[</api/book/v1/3>;rel=\"self\"]"));
		assertEquals("Santo Tomás de Aquino", thirdTome.getAuthor());
		assertEquals(3, thirdTome.getKey());
		assertEquals(150, thirdTome.getPrice());
		assertEquals("Suma Teológica - Tomo III", thirdTome.getTitle());
		
		BookVO fifthTome = books.get(4);
		assertNotNull(fifthTome.getKey());
		assertNotNull(fifthTome.getLinks());
		assertNotNull(fifthTome.getAuthor());
		assertNotNull(fifthTome.getLaunchDate());
		assertNotNull(fifthTome.getPrice());
		assertNotNull(fifthTome.toString().contains("[</api/book/v1/5>;rel=\"self\"]"));
		assertEquals("Santo Tomás de Aquino", fifthTome.getAuthor());
		assertEquals(5, fifthTome.getKey());
		assertEquals(150, fifthTome.getPrice());
		assertEquals("Suma Teológica - Tomo V", fifthTome.getTitle());
	}
	
	@Test
	void testCreate() throws Exception {
		Book b = input.mockEntity(1);
		Book book = b;
		book.setId(1);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1);
		when(rep.save(b)).thenReturn(book);
		
		var result = service.create(vo);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.getAuthor());
		assertNotNull(result.getLaunchDate());
		assertNotNull(result.getPrice());
		assertNotNull(result.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Santo Tomás de Aquino", result.getAuthor());
		assertEquals(1, result.getKey());
		assertEquals(150, result.getPrice());
		assertEquals("Suma Teológica - Tomo I", result.getTitle());
	}
	
	@Test
	void testCreateWithNullBook() throws Exception {
		Exception ex = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMsg = "It is not allowed to persist a null object";
		String msg = ex.getMessage();
		
		assertTrue(msg.contains(expectedMsg));
	}
	
	@Test
	void testUpdate() throws Exception {
		Book b = input.mockEntity(1);
		b.setId(1);
		Book persisted = b;
		persisted.setId(1);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1);
		when(rep.findById(1)).thenReturn(Optional.of(b));
		when(rep.save(b)).thenReturn(persisted);
		
		var result = service.update(vo);
		
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.getAuthor());
		assertNotNull(result.getLaunchDate());
		assertNotNull(result.getPrice());
		assertNotNull(result.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Santo Tomás de Aquino", result.getAuthor());
		assertEquals(1, result.getKey());
		assertEquals(150, result.getPrice());
		assertEquals("Suma Teológica - Tomo I", result.getTitle());
	}
	
	@Test
	void testUpdateWithNullBook() throws Exception {
		Exception ex = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMsg = "It is not allowed to persist a null object";
		String msg = ex.getMessage();
		
		assertTrue(msg.contains(expectedMsg));
	}
	
	@Test
	void testDelete() throws Exception {
		Book b = input.mockEntity(1);
		b.setId(1);
		when(rep.findById(1)).thenReturn(Optional.of(b));
		
		service.delete(1);
	}

}
