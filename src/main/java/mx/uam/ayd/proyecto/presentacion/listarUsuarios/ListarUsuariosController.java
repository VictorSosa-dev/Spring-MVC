package mx.uam.ayd.proyecto.presentacion.listarUsuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Controller
@Slf4j
public class ListarUsuariosController {
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	/**
	 * Método invocado cuando se hace una petición GET a la ruta
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listarUsuarios",method = RequestMethod.GET)
	public String getListarUsuarios(Model model) {
		log.info("Iniciando historia de usuario Listar Usuarios");
		try {
			
			// Invocamos al servicio
			List <UsuarioDto> usuarios = servicioUsuario.recuperaUsuarios();
			
			// Agregamos la lista de usuarios al modelo
			model.addAttribute("usuarios", usuarios);
			
			// Redirigimos  a la ventana de listar usuarios
			return "vistaListarUsuarios/ListaUsuarios";
		} catch (Exception e) {
			// TODO: handle exception
			// Agregamos el mensaje de error al modelo
			model.addAttribute("error",e.getMessage());
			return "vistaListarUsuarios/ListaUsuariosError";
		}
		// Redirige a esta vista
		
	
	}
}
