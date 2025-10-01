package API.NFL;

import jakarta.ws.rs.core.Link;
import java.util.List;

public class TimeDTO {
    public Long id;
    public String nome;
    public String cidade;
    public Conferencia conferencia;
    public Estadio estadio;
    public List<Link> links;

    // Construtor completo
    public TimeDTO(Long id, String nome, String cidade, Conferencia conferencia, Estadio estadio, List<Link> links) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.conferencia = conferencia;
        this.estadio = estadio;
        this.links = links;
    }
}