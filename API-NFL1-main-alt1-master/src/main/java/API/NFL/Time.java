package API.NFL;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Time extends PanacheEntity {

    @NotBlank(message = "O nome do time é obrigatório")
    @Size(min = 3, max = 100)
    @Column(nullable = false, unique = true, length = 100)
    public String nome;

    @NotBlank(message = "A cidade do time é obrigatória")
    @Column(nullable = false)
    public String cidade;

    @NotNull(message = "A conferência é obrigatória")
    @Enumerated(EnumType.STRING)
    public API.NFL.Conferencia conferencia;

    // Um time tem um estádio principal (One-to-One)
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "estadio_id", referencedColumnName = "id")
    public Estadio estadio;

    //Um time tem vários jogadores (One-to-Many)
    @OneToMany(mappedBy = "time", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Jogador> jogadores = new ArrayList<>();
}