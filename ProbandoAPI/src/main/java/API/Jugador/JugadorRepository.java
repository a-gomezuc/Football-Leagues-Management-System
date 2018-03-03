package API.Jugador;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface JugadorRepository extends MongoRepository<Jugador, String> {
	
	List<Jugador> findByNombreIgnoreCase(String nombre);
	
	List<Jugador>findByEquipoIgnoreCase(String equipo);
	
	List<Jugador>findByEstadoIgnoreCase(String estado);
	
	List<Jugador>findByEstadoAndEquipoAllIgnoreCase(String estado, String equipo);
	
	Jugador findByNombreAndApellidosAllIgnoreCase(String nombre, String apellido);
	
	Jugador findById(String id);
	
	Jugador findByEmailIgnoreCase(String email);
	
	Jugador findByDniIgnoreCase(String dni);
	
	Jugador findByCapitanAndEquipo(boolean capitan, String idEquipo);
	
	Jugador findByDorsalAndEquipo(int dorsal,String equipo);
	
	Jugador findByNombreUsuarioIgnoreCase(String nombreUsuario);
	
}
