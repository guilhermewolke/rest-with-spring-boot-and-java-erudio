package unittests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.zaek.data.vo.v1.BookVO;
import br.com.zaek.model.Book;

public class MockBook {
	
	private Map<Integer,String> romanNumbers;
	
	public MockBook() {
		this.romanNumbers = new HashMap<Integer,String>();
		this.romanNumbers.put(1, "I");
		this.romanNumbers.put(2, "II");
		this.romanNumbers.put(3, "III");
		this.romanNumbers.put(4, "IV");
		this.romanNumbers.put(5, "V");
	}
	
	public List<Book> mockEntityList(){
		List<Book> books = new ArrayList<Book>();
		for (int i = 1; i <= 5; i++) {
			books.add(mockEntity(i));
		}
		return books;
	}
	
	public List<BookVO> mockVOList() {
		List<BookVO> books = new ArrayList<BookVO>();
		for (int i = 1; i <= 5; i++) {
			books.add(mockVO(i));
		}
		return books;
	}
	
	public Book mockEntity(Integer number) {
		Book b = new Book();
		b.setAuthor("Santo Tomás de Aquino");
		b.setId(number);
		b.setLaunchDate(new Date());
		b.setPrice(150.00);
		b.setTitle("Suma Teológica - Tomo " + this.romanNumbers.get(number));
		
		return b;
	}
	
	public BookVO mockVO(Integer number) {
		BookVO b = new BookVO();
		b.setAuthor("Santo Tomás de Aquino");
		b.setKey(number);
		b.setLaunchDate(new Date());
		b.setPrice(150.00);
		b.setTitle("Suma Teológica - Tomo " + this.romanNumbers.get(number));
		
		return b;
	}

}
