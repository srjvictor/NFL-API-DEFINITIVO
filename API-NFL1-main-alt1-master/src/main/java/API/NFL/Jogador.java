package API.NFL;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Jogador extends PanacheEntity {

    @NotBlank(message = "O nome do jogador é obrigatório")
    public String nome;

    @NotNull(message = "O número da camisa é obrigatório")
    @Positive
    public Integer numeroCamisa;

    @NotNull(message = "O time é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id", nullable = false)
    public Time time;

    // RELACIONAMENTO: Um jogador pode ter várias posições (Many-to-Many)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "jogador_posicao",
            joinColumns = @JoinColumn(name = "jogador_id"),
            inverseJoinColumns = @JoinColumn(name = "posicao_id"))
    public Set<Posicao> posicoes = new HashSet<>();
}