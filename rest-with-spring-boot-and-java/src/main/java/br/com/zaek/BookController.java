package br.com.zaek;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zaek.data.vo.v1.BookVO;
import br.com.zaek.data.vo.v1.PersonVO;
import br.com.zaek.mapper.DozerMapper;
import br.com.zaek.model.Book;
import br.com.zaek.services.BookServices;
import br.com.zaek.utils.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("api/book/v1")
@Tag(name="Livros", description="Endpoints para gestão de livros")
public class BookController {
	
	@Autowired
	private BookServices service;

	public BookController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping(value="/{id}",
			produces= {
					MediaType.APPLICATION_JSON,
					MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YAML
					})
	@Operation(
			summary="Recuperar um livro",
			description="Recuperar um livro à partir de seu ID",
			tags = {"Livros"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class))),
				@ApiResponse(description = "No content", responseCode = "204", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
			})
	public ResponseEntity<BookVO> findByID(@PathVariable(value="id") Integer id) {
		return ResponseEntity.ok(service.findByID(id));
	}
	
	@GetMapping(produces={
					MediaType.APPLICATION_JSON,
					MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YAML
					})
	@Operation(
		summary="Listar",
		description="Retorna todos os livros cadastrados no sistema",
		tags={"Livros"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
			@Content(
					mediaType="application/json",
					array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
				)
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public List<BookVO> findAll() {
		return service.findAll();
	}
	
	@PostMapping(
		consumes={
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML
		},
		produces={
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML
		})
	@Operation(
		summary="Inserir Livro",
		description="Inserir um novo livro, enviando os dados em formato JSON, XML ou representação YAML da entidade",
		tags={"Livros"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public BookVO create(@RequestBody Book b) {
		var entity = DozerMapper.parseObject(b, BookVO.class);
		return service.create(entity);
	}
	
	@PutMapping(value="/{id}",
			consumes={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML},
			produces={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
	@Operation(
		summary="Editar Livro",
		description="Editar o livro, enviando os dados em formato JSON, XML ou representação YAML da entidade",
		tags={"Livros"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public BookVO update(@PathVariable(name="id") Integer id, @RequestBody BookVO b) {
		b.setKey(id);
		
		return service.update(b);
	}

	@DeleteMapping(value="/{id}")
	@Operation(
		summary="Excluir Livro",
		description="Excluir um livro, à partir de seu ID",
		tags={"Livros"},
		responses = {
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	public ResponseEntity<?> delete(@PathVariable(name="id") Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
