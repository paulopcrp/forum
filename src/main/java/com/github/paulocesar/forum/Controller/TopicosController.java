package com.github.paulocesar.forum.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.paulocesar.forum.Topico;
import com.github.paulocesar.forum.Controller.DTO.AtualizacaoTopicoForm;
import com.github.paulocesar.forum.Controller.DTO.DetalhesDoTopicoDTO;
import com.github.paulocesar.forum.Controller.DTO.TopicoDTO;
import com.github.paulocesar.forum.Controller.DTO.TopicoForm;
import com.github.paulocesar.forum.Controller.repository.CursoRepository;
import com.github.paulocesar.forum.repository.TopicoRepository;




//@Controller
@RestController //se usar esta anotation não é necessario usar o @ResponseBody
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	//@ResponseBody // sem essa anotation o spring considera q faremos uma navegação.
	public List<TopicoDTO> lista(String nomeCurso) {
		if (nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDTO.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso); //sintaxe findBy+NOME-DO-ATRIBUTO e dentro do parentese o nome q representa o atributo
			return TopicoDTO.converter(topicos);	
			
		}
		
	}
	
	@PostMapping
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Validated TopicoForm topicoform, UriComponentsBuilder uriBuilder) {
		Topico topico = topicoform.converter(cursoRepository);
		topicoRepository.save(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDTO> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
		
			return ResponseEntity.ok(new DetalhesDoTopicoDTO(topico.get()));
		} 
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Validated AtualizacaoTopicoForm form  ){
		
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topico));
		} 
	
		return ResponseEntity.notFound().build();
				
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id ){
		
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} 
	
		return ResponseEntity.notFound().build();
		
	}
}
