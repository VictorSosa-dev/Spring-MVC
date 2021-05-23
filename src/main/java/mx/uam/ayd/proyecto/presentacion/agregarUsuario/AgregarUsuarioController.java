package mx.uam.ayd.proyecto.presentacion.agregarUsuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.negocio.ServicioGrupo;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;


@Controller
@Slf4j
public class AgregarUsuarioController {
	
	@Autowired
	private ServicioGrupo servicioGrupo;
	
	@Autowired
	private ServicioUsuario servicioUsuario;

	/**
	 * 
	 * Método invocado cuando se hace una petición GET a la ruta
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/agregarUsuario", method = RequestMethod.GET)
    public String getAgregarUsuario(Model model) {
    	
    		log.info("Iniciando Historia de usuario: Agrega usuario");
    		
    		// Invocando el servicio
    		List <Grupo> grupos = servicioGrupo.recuperaGrupos();
        
    		// Agregamos los grupos al modelo que se le pasa a la vista
    		model.addAttribute("grupos", grupos);
    		
    		// Redirige a esta vista
    		return "vistaAgregarUsuario/FormaAgregarUsuario";
    	
    }

	/**
	 * 
	 * Método invocado cuando se hace una petición POST a la ruta
	 * 
	 * @param nombre
	 * @param apellido
	 * @param grupo
	 * @param edad
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/agregarUsuario", method = RequestMethod.POST)
    public String postAgregarUsuario(
    		@RequestParam(value="nombre", required=true) String nombre, 
    		@RequestParam(value="apellido", required=true) String apellido,
    		@RequestParam(value="grupo", required=true) String grupo, 
    		@RequestParam(value="edad", required=true) int edad, Model model) {
    	
		
    		log.info("Agregando usuario "+nombre+" "+apellido+" "+edad+" "+grupo);
    		
    		try {
    		
    			// Invocación al servicio
    			Usuario usuario = servicioUsuario.agregaUsuario(nombre, apellido, edad, grupo);
    			
    			// Agregamos el usuario al modelo que se le pasa a la vista
    			model.addAttribute("usuario", usuario);
    			
    			// Redirigimos a la vista de éxito
    			return "vistaAgregarUsuario/AgregarUsuarioExito";
    			
    		} catch(Exception ex) {
    			
    			// Agregamos el mensaje de error al modelo
    			model.addAttribute("error",ex.getMessage());
    			
    			// Redirigimos a la vista de error
    			return "vistaAgregarUsuario/AgregarUsuarioError";
    			
    		}

       
    	
    }

}
