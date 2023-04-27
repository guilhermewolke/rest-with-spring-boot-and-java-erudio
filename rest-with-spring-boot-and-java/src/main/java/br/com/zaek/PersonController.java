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

import br.com.zaek.data.vo.v1.PersonVO;
import br.com.zaek.data.vo.v2.PersonVOV2;
import br.com.zaek.mapper.DozerMapper;
import br.com.zaek.model.Person;
import br.com.zaek.services.PersonServices;
import br.com.zaek.utils.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name="Pessoas", description="Endpoints para gestão de pessoas")
public class PersonController {
	
	@Autowired
	private PersonServices ps;

	public PersonController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping(value="/{id}", produces={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
	@Operation(summary="Recuperar uma pessoa",
	description="Recuperar uma pessoa através de seu ID",
	tags={"People"},
	responses = {
		@ApiResponse(description = "Success", responseCode = "200", content =	@Content(schema = @Schema(implementation = PersonVO.class))),
		@ApiResponse(description = "No content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
				
	})
	public ResponseEntity<PersonVO> findByID(@PathVariable(value="id") Long id) {
		return ResponseEntity.ok(ps.findById(id));
	}
	
	@GetMapping(produces={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
	@Operation(summary="Listar",
				description="Retorna todas as pessoas cadastradas no sistema",
				tags={"People"},
				responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(
									mediaType="application/json",
									array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
									)
							}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
							
				})
	public List<PersonVO> findAll() {
		return ps.findAll();
	}
	
	@PostMapping(consumes={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML},
			produces={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
	@Operation(summary="Inserir pessoa",
	description="Inserir um novo cadastro de pessoa, enviando os dados em formato JSON, XML ou representação YAML da entidade",
	tags={"People"},
	responses = {
		@ApiResponse(description = "Success", responseCode = "200", content =	@Content(schema = @Schema(implementation = PersonVO.class))),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public PersonVO create(@RequestBody Person p) {
		var entity = DozerMapper.parseObject(p, PersonVO.class);
		return ps.create(entity);
	}
	
	@PostMapping(value="/v2",
			consumes={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML},
			produces={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
	public PersonVOV2 createV2(@RequestBody PersonVOV2 p) {
		var entity = DozerMapper.parseObject(p, PersonVOV2.class);
		return ps.createV2(entity);
	}
	
	@PutMapping(value="/{id}",
			consumes={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML},
			produces={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
	@Operation(summary="Editar pessoa",
	description="Editar o cadastro de uma pessoa, enviando os dados em formato JSON, XML ou representação YAML da entidade",
	tags={"People"},
	responses = {
		@ApiResponse(description = "Updated", responseCode = "200", content =	@Content(schema = @Schema(implementation = PersonVO.class))),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public PersonVO update(@PathVariable(name="id") String id, @RequestBody PersonVO p) {
		Long newId = Long.parseLong(id);
		p.setKey(newId);

		return ps.update(p);
	}
	
	@DeleteMapping(value="/{id}")
	@Operation(summary="Excluir pessoa",
	description="Excluir o cadastro de uma pessoa, à partir de seu ID",
	tags={"People"},
	responses = {
		@ApiResponse(description = "No content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable(name="id") String id) {
		Long theID = Long.parseLong(id);
		ps.delete(theID);
		return ResponseEntity.noContent().build();
	}
	

}
