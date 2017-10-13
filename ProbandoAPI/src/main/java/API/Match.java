package API;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Partidos")
public class Match {
	@Id
	private String id;
	private String liga;
	private String equipoLocal;
	private String equipoVisitante;
	private String arbitro;
	private String fechaPartido;
	private String horaPartido;
	// private Estadio estadio;
	private String estado;
	private String jornada;
	private String equipacionLocal;
	private String equipacionVisitante;
	private String resultado;
	// private Incidencia[] incidencias;

	public Match() {
	};

	public Match(String id, String liga, String equipoLocal, String equipoVisitante, String arbitro,
			String fechaPartido, String horaPartido, String estado, String jornada, String equipacionLocal,
			String equipacionVisitante, String resultado) {
		super();
		this.id = id;
		this.liga = liga;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.arbitro = arbitro;
		this.fechaPartido = fechaPartido;
		this.horaPartido = horaPartido;
		this.estado = estado;
		this.jornada = jornada;
		this.equipacionLocal = equipacionLocal;
		this.equipacionVisitante = equipacionVisitante;
		this.resultado = resultado;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	public String getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(String equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public String getEquipoVisitante() {
		return equipoVisitante;
	}

	public void setEquipoVisitante(String equipoVisitante) {
		this.equipoVisitante = equipoVisitante;
	}

	public String getArbitro() {
		return arbitro;
	}

	public void setArbitro(String arbitro) {
		this.arbitro = arbitro;
	}

	public String getFechaPartido() {
		return fechaPartido;
	}

	public void setFechaPartido(String fechaPartido) {
		this.fechaPartido = fechaPartido;
	}

	public String getHoraPartido() {
		return horaPartido;
	}

	public void setHoraPartido(String horaPartido) {
		this.horaPartido = horaPartido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getJornada() {
		return jornada;
	}

	public void setJornada(String jornada) {
		this.jornada = jornada;
	}

	public String getEquipacionLocal() {
		return equipacionLocal;
	}

	public void setEquipacionLocal(String equipacionLocal) {
		this.equipacionLocal = equipacionLocal;
	}

	public String getEquipacionVisitante() {
		return equipacionVisitante;
	}

	public void setEquipacionVisitante(String equipacionVisitante) {
		this.equipacionVisitante = equipacionVisitante;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}