package API.NFL;


import jakarta.ws.rs.core.Link;
import java.util.List;
import java.util.Set;

// DTO para exibir um jogador com seus links
public class JogadorDTO {
    public Long id;
    public String nome;
    public Integer numeroCamisa;
    public Set<Posicao> posicoes;
    public List<Link> links;

    public JogadorDTO(Long id, String nome, Integer numeroCamisa, Set<Posicao> posicoes, List<Link> links) {
        this.id = id;
        this.nome = nome;
        this.numeroCamisa = numeroCamisa;
        this.posicoes = posicoes;
        this.links = links;
    }
}