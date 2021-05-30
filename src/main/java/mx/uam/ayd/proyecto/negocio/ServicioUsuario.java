package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

/**
 * Clase  que maneja la logica de negocio del los usuarios
 * @author Victor
 *
 */
@Slf4j
@Service
public class ServicioUsuario {
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	

	
	/**
	 * Permite agregar un usuario
	 *  
	 * @param usuarioDto
	 * @return dto con el id del usuario
	 */
	public UsuarioDto agregaUsuario(UsuarioDto usuarioDto) {
		
		Usuario usuario = usuarioRepository.findByNombreAndApellido(usuarioDto.getNombre(), usuarioDto.getApellido());
		
		if(usuario != null) {
			throw new IllegalArgumentException("Ese usuario ya existe");
		}
		
		Optional <Grupo> optGrupo = grupoRepository.findById(usuarioDto.getGrupo());
		
		if(optGrupo.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el grupo");
		}
				
		Grupo grupo = optGrupo.get();
		
		log.info("Agregando usuario nombre: "+usuarioDto.getNombre()+" apellido:"+usuarioDto.getApellido());
		
		usuario = new Usuario();
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellido(usuarioDto.getApellido());
		usuario.setEdad(usuarioDto.getEdad());
		usuario.setGrupo(grupo);
		
		usuario = usuarioRepository.save(usuario);
		
		grupo.addUsuario(usuario);
		
		grupoRepository.save(grupo);
		
		
		
		return UsuarioDto.creaDto(usuario);

	}
	
	/**
	 * Recupera todos los usuarios existentes
	 * 
	 * @return Una lista con los usuarios (o lista vacía)
	 */
	public List <UsuarioDto> recuperaUsuarios() {
        
		List <UsuarioDto> usuarios = new ArrayList<>();
		
		for (Usuario usuario : usuarioRepository.findAll()) {
			  usuarios.add(UsuarioDto.creaDto(usuario));
		}
		
		return usuarios;
	}
	
	/**
	 * Recuperar un usuario por su id
	 * 
	 * @param id
	 * @return
	 */
	public Usuario recuperaId(Long id) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		if(usuario != null) {
			return usuario.get();
		}else {
			throw new IllegalArgumentException("Ese usuario con "+ id +" no existe");
		}
		
	}


	/**
	 * Se actualiza un usuario con el id 
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	public UsuarioDto actualiza(Long id, UsuarioDto usuario) {
		
		Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
		
		if(optionalUsuario == null) {
			throw new IllegalArgumentException("No se encontró el usuario");
		}
		
		Usuario usuarioAux = optionalUsuario.get();
		
		Optional <Grupo> optGrupo = grupoRepository.findById(usuario.getGrupo());
		
		if(optGrupo.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el grupo");
		}
				
		Grupo grupo = optGrupo.get();
		
		usuarioAux.setNombre(usuario.getNombre());
		usuarioAux.setApellido(usuario.getApellido());
		usuarioAux.setEdad(usuario.getEdad());
		usuarioAux.setGrupo(grupo);
		
		usuarioAux = usuarioRepository.save(usuarioAux);
		
		grupo.addUsuario(usuarioAux);
		
		grupoRepository.save(grupo);
		
		
		return UsuarioDto.creaDto(usuarioAux);
	}
	
	
	/**
	 * Metodo para eliminar un usuario
	 * 
	 * @param id
	 */
	public void eliminarUsuario(Long id) {
		
		usuarioRepository.deleteById(id);
	}

	
	
	
	

}
