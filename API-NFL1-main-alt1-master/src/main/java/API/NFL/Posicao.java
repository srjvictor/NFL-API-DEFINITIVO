package API.NFL;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Posicao extends PanacheEntity {

    @NotBlank(message = "A sigla da posição é obrigatória")
    @Pattern(regexp = "[A-Z]{1,3}", message = "A sigla deve ter de 1 a 3 letras maiúsculas")
    @Column(unique = true, nullable = false)
    public String sigla; // Ex: QB, WR, CB

    @NotBlank(message = "O nome da posição é obrigatório")
    public String nome; // Ex: Quarterback
}