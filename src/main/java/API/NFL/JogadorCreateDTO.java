package API.NFL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Set;

// DTO para criar um novo jogador, especificando os IDs das relações
public class JogadorCreateDTO {
    @NotBlank(message = "O nome do jogador é obrigatório")
    public String nome;

    @NotNull(message = "O número da camisa é obrigatório")
    @Positive
    public Integer numeroCamisa;

    @NotNull(message = "O ID do time é obrigatório")
    public Long timeId;

    @NotEmpty(message = "O jogador deve ter pelo menos uma posição")
    public Set<Long> posicoesIds;
}