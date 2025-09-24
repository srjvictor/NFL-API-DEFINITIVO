package API.NFL;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Estadio extends PanacheEntity {

    @NotBlank(message = "O nome do estádio é obrigatório")
    public String nome; // Ex: Lambeau Field

    @NotNull(message = "A capacidade é obrigatória")
    @Positive(message = "A capacidade deve ser um número positivo")
    public Integer capacidade;
}
