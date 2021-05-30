package mx.uam.ayd.proyecto.servicios;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;

import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
public class UsuarioRestController {

	@Autowired
	private ServicioUsuario servicioUsuarios;
	
	
	/**
	 * Permite recuperar todos los usuarios
	 * 
	 * @return
	 */
	@GetMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<UsuarioDto>> retrieveAll() {
		
		List <UsuarioDto> usuarios =  servicioUsuarios.recuperaUsuarios();
		
		return ResponseEntity.status(HttpStatus.OK).body(usuarios);
		
	}

	/**
	 * Método que permite agregar un usuario
	 * 
	 * @param nuevoUsuario
	 * @return
	 */
	@PostMapping(path = "/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDto> create(@RequestBody @Valid UsuarioDto nuevoUsuario) {
		
		try {
		
			UsuarioDto usuarioDto = servicioUsuarios.agregaUsuario(nuevoUsuario);

			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);

		} catch(Exception ex) {
			
			HttpStatus status;
			
			if(ex instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
			throw new ResponseStatusException(status, ex.getMessage());
		}
		
	}
	
	/**
	 * Permite recuperar un usuario a partir de su ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/usuarios/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <UsuarioDto> retrieve(@PathVariable("id") Long id) {
	
		log.info("Recuperando el usuario con id"+id);
		
		try {
			Usuario usuario = servicioUsuarios.recuperaId(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(UsuarioDto.creaDto(usuario));
		} catch (Exception ex) {
			HttpStatus status;
			
			if(ex instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
			throw new ResponseStatusException(status, ex.getMessage());
		}
		
	}
	
	/**
	 * Metodo para eliminar un usuario
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "/usuarios/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDto> delete(@PathVariable("id") Long id) {
		
		log.info("Se va eliminar el usuario con id "+id);
		try {
			Usuario usuario = servicioUsuarios.recuperaId(id);
			servicioUsuarios.eliminarUsuario(id);
			return ResponseEntity.status(HttpStatus.OK).body(UsuarioDto.creaDto(usuario));
		} catch (Exception ex) {
			HttpStatus status;
			
			if(ex instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
			throw new ResponseStatusException(status, ex.getMessage());
		}
		
	}
	
	
	/**
	 * Metodo para actualizar un usuario
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	@PutMapping(path = "/usuarios/{id}",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody UsuarioDto usuario) {
		
		log.info("Recuperando el usuario con id "+id);
		
		try{
			
			UsuarioDto usuarioDto = servicioUsuarios.actualiza(id,usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
		} catch(Exception ex) {
			
			HttpStatus status;
			
			if(ex instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
			throw new ResponseStatusException(status, ex.getMessage());
		}
					
	}
	
}
