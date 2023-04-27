package br.com.zaek.mapper.custom;

import org.springframework.stereotype.Service;

import br.com.zaek.data.vo.v1.BookVO;
import br.com.zaek.model.Book;

@Service
public class BookMapper {

	public BookVO entityToVO(Book b) {
		BookVO vo = new BookVO();
		vo.setKey(b.getId());
		vo.setAuthor(b.getAuthor());
		vo.setLaunchDate(b.getLaunchDate());
		vo.setPrice(b.getPrice());
		vo.setTitle(b.getTitle());
		return vo;
	}
	
	public Book VOtoEntity(BookVO vo) {
		Book b = new Book();
		b.setAuthor(vo.getAuthor());
		b.setId(vo.getKey());
		b.setLaunchDate(vo.getLaunchDate());
		b.setPrice(vo.getPrice());
		b.setTitle(vo.getTitle());
		return b;
	}

}
