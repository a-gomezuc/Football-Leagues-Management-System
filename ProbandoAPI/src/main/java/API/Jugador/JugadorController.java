package API.Jugador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import API.Arbitro.Arbitro;
import API.Arbitro.ArbitroRepository;
import API.Equipo.Equipo;
import API.Equipo.EquipoRepository;
import API.Liga.Liga;
import API.Liga.LigaRepository;
import API.Partido.Partido;
import API.Usuario.Usuario;
import API.Usuario.UsuarioComponent;
import API.Usuario.UsuarioRepository;

@RestController
@CrossOrigin
@RequestMapping("/jugadores")
public class JugadorController {

	public interface ProfileView extends Jugador.PerfilAtt, Jugador.EquipoAtt {
	}

	@Autowired
	JugadorRepository jugadorRepository;
	@Autowired
	EquipoRepository equipoRepository;
	@Autowired
	LigaRepository ligaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	ArbitroRepository arbitroRepository;
	@Autowired
	UsuarioComponent usuarioComponent;

	@JsonView(ProfileView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Jugador> crearJugador(@RequestBody Jugador jugador) {
		if (jugadorRepository.findByDniIgnoreCase(jugador.getDni()) != null
				|| usuarioRepository.findByNombreUsuarioIgnoreCase(jugador.getNombreUsuario()) != null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_ACCEPTABLE);
		}

		jugador.setFotoJugador("defaultImage.png");
		jugador.setEquipo("");
		jugador.setTarjetasAmarillas(0);
		jugador.setTarjetasRojas(0);

		Usuario usuario = new Usuario(jugador.getNombreUsuario(), jugador.getClave(), "ROLE_JUGADOR");

		usuarioRepository.save(usuario);
		jugadorRepository.save(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.CREATED);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadores() {
		return new ResponseEntity<List<Jugador>>(jugadorRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugador(@PathVariable(value = "nombre") String nombre) {
		List<Jugador> jugadores = jugadorRepository.findByNombreIgnoreCase(nombre);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{nombre}/{apellidos}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verJugadorApellidos(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "apellidos") String apellidos) {
		Jugador jugador = jugadorRepository.findByNombreAndApellidosAllIgnoreCase(nombre, apellidos);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/equipo/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadoresEquipo(@PathVariable String equipo) {
		List<Jugador> jugadores = jugadorRepository.findByEquipoIgnoreCase(equipo);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/estado/{estado}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadorEstado(@PathVariable String estado) {
		List<Jugador> jugadores = jugadorRepository.findByEstadoIgnoreCase(estado);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/estado/{estado}/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verJugadorEstadoEquipo(@PathVariable(value = "estado") String estado,
			@PathVariable(value = "equipo") String equipo) {
		List<Jugador> jugadores = jugadorRepository.findByEstadoAndEquipoAllIgnoreCase(estado, equipo);
		if (jugadores.isEmpty()) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(jugadores, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/capitan/{equipo}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verCapitanEquipo(@PathVariable String equipo) {
		Jugador capitan = jugadorRepository.findByCapitanAndEquipoIgnoreCase(true, equipo);
		if (capitan == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Jugador>(capitan, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/dni/{DNI}", method = RequestMethod.GET)
	public ResponseEntity<Jugador> verJugadorDNI(@PathVariable(value = "DNI") String DNI) {
		Jugador jugador = jugadorRepository.findByDniIgnoreCase(DNI);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Jugador> actualizaJugadorId(@PathVariable(value = "id") String id,
			@RequestBody Jugador entrada) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(jugador.getNombreUsuario());
		switch (usuarioComponent.getLoggedUser().getRol()) {
		case "ROLE_JUGADOR":

			if (usuarioComponent.getLoggedUser().getNombreUsuario().equals(jugador.getNombreUsuario())) {

				jugador.setNombre(entrada.getNombre());
				jugador.setFotoJugador(entrada.getFotoJugador());
				jugador.setApellidos(entrada.getApellidos());
				jugador.setClaveSinEncriptar(entrada.getClave());
				jugador.setEmail(entrada.getEmail());

				usuario.setClave(jugador.getClave());

				usuarioRepository.save(usuario);
				break;
			} else {
				return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED);
			}
		case "ROLE_ARBITRO":
			boolean jugadorEnPartido = false ;
			Arbitro arbitro = arbitroRepository.findByNombreUsuario(usuarioComponent.getLoggedUser().getNombreUsuario());
			for (Partido partidoArbitro: arbitro.getPartidosArbitrados()) {
				if (((partidoArbitro.getEquipoLocal().getPlantillaEquipo().contains(jugador)) || (partidoArbitro.getEquipoVisitante().getPlantillaEquipo().contains(jugador))) && (jugadorEnPartido==false)){
				jugadorEnPartido = true;
			}
			}
				if(jugadorEnPartido) {
			jugador.setGoles(entrada.getGoles());
			jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
			jugador.setTarjetasRojas(entrada.getTarjetasRojas());
				}
				else {
					return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED);
				}
			break;
		case "ROLE_ADMIN":
		case "ROLE_MIEMBROCOMITE":
			jugador.setNombre(entrada.getNombre());
			jugador.setApellidos(entrada.getApellidos());
			jugador.setCapitan(entrada.isCapitan());
			jugador.setEmail(entrada.getEmail());
			jugador.setDni(entrada.getDni());
			jugador.setNombreUsuario(entrada.getNombreUsuario());
			jugador.setClaveSinEncriptar(entrada.getClave());
			jugador.setDorsal(entrada.getDorsal());
			jugador.setGoles(entrada.getGoles());
			jugador.setFechaSancion(entrada.getFechaSancion());
			jugador.setNacionalidad(entrada.getNacionalidad());
			jugador.setEstado(entrada.getEstado());
			jugador.setPosicion(entrada.getPosicion());
			jugador.setGoles(entrada.getGoles());
			jugador.setTarjetasAmarillas(entrada.getTarjetasAmarillas());
			jugador.setTarjetasRojas(entrada.getTarjetasRojas());
			
			usuario.setNombreUsuario(jugador.getNombreUsuario());
			usuario.setClave(jugador.getClave());
			usuarioRepository.save(usuario);
			break;
		default:
			return new ResponseEntity<Jugador>(HttpStatus.UNAUTHORIZED);
		}

		jugadorRepository.save(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

	@JsonView(ProfileView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Jugador> eliminarJugador(@PathVariable String id) {
		Jugador jugador = jugadorRepository.findById(id);
		if (jugador == null) {
			return new ResponseEntity<Jugador>(HttpStatus.NO_CONTENT);
		}

		Equipo equipo = equipoRepository.findById(jugador.getEquipo());
		if (equipo != null) {
			equipo.getPlantillaEquipo().remove(jugador);
			Liga liga = ligaRepository.findByNombreIgnoreCase(equipo.getLiga());
			if (liga != null) {
				liga.getGoleadores().remove(jugador);
				equipo.getPlantillaEquipo().remove(jugador);
				ligaRepository.save(liga);
				equipoRepository.save(equipo);
			}

		}
		Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(jugador.getNombreUsuario());

		if (usuario != null) {
			usuarioRepository.delete(usuario);
			;
		}

		jugadorRepository.delete(jugador);
		return new ResponseEntity<Jugador>(jugador, HttpStatus.OK);
	}

}
