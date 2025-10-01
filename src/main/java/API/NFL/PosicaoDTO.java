package API.NFL;

import jakarta.ws.rs.core.Link;
import java.util.List;

public class PosicaoDTO {
    public Long id;
    public String sigla;
    public String nome;
    public List<Link> links;

    public PosicaoDTO(Long id, String sigla, String nome, List<Link> links) {
        this.id = id;
        this.sigla = sigla;
        this.nome = nome;
        this.links = links;
    }
}