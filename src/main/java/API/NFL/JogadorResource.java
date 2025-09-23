package API.NFL;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/jogadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Tag(name = "Jogadores", description = "Operações para gerenciamento de jogadores")
public class JogadorResource {

    @Context
    UriInfo uriInfo;

    // 1. CREATE (POST)
    @POST
    @Transactional
    @Operation(summary = "Criar um novo jogador e associá-lo a um time e posições")
    @APIResponse(responseCode = "201", description = "Jogador criado")
    @APIResponse(responseCode = "404", description = "Time ou Posição não encontrados")
    public Response criarJogador(@Valid JogadorCreateDTO jogadorDTO) {
        Time time = Time.findById(jogadorDTO.timeId);
        if (time == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Time não encontrado.").build();
        }

        List<Posicao> posicoes = Posicao.list("id in ?1", jogadorDTO.posicoesIds);
        if (posicoes.size() != jogadorDTO.posicoesIds.size()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Uma ou mais posições não foram encontradas.").build();
        }

        Jogador jogador = new Jogador();
        jogador.nome = jogadorDTO.nome;
        jogador.numeroCamisa = jogadorDTO.numeroCamisa;
        jogador.time = time;
        jogador.posicoes = new HashSet<>(posicoes);

        jogador.persist();

        URI location = uriInfo.getAbsolutePathBuilder().path(jogador.id.toString()).build();
        return Response.created(location).entity(jogador).build();
    }

    // 2. READ (GET All)
    @GET
    @Operation(summary = "Listar todos os jogadores")
    public Response listarJogadores() {
        List<Jogador> jogadores = Jogador.listAll();
        List<JogadorDTO> dtos = jogadores.stream().map(this::toDTO).collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    // 3. READ (GET by ID)
    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar jogador por ID")
    @APIResponse(responseCode = "200", description = "Jogador encontrado")
    @APIResponse(responseCode = "404", description = "Jogador não encontrado")
    public Response buscarJogadorPorId(@PathParam("id") Long id) {
        return Jogador.<Jogador>findByIdOptional(id)
                .map(jogador -> Response.ok(toDTO(jogador)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // 4. UPDATE (PUT) - Simplificado para nome e número. A troca de time seria mais complexa.
    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar nome e número de um jogador")
    public Response atualizarJogador(@PathParam("id") Long id, Jogador jogadorAtualizado) {
        return Jogador.<Jogador>findByIdOptional(id)
                .map(jogador -> {
                    jogador.nome = jogadorAtualizado.nome;
                    jogador.numeroCamisa = jogadorAtualizado.numeroCamisa;
                    jogador.persist();
                    return Response.ok(jogador).build();
                }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // 5. DELETE
    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Deletar um jogador")
    @APIResponse(responseCode = "204", description = "Jogador deletado")
    public Response deletarJogador(@PathParam("id") Long id) {
        if (Jogador.deleteById(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6. ENDPOINT COM CONSULTA PERSONALIZADA
    @GET
    @Path("/portime/{timeId}")
    @Operation(summary = "Listar todos os jogadores de um time específico")
    @APIResponse(responseCode = "404", description = "Time não encontrado")
    public Response buscarJogadoresPorTime(@PathParam("timeId") Long timeId) {
        if (Time.findById(timeId) == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Time não encontrado.").build();
        }
        List<Jogador> jogadores = Jogador.list("time.id", timeId);
        return Response.ok(jogadores).build();
    }

    // Método auxiliar para HATEOAS
    private JogadorDTO toDTO(Jogador jogador) {
        // Self link
        Link selfLink = Link.fromUri(uriInfo.getAbsolutePathBuilder().path(jogador.id.toString()).build())
                .rel("self").type(MediaType.APPLICATION_JSON).build();

        // Link para o time do jogador
        Link timeLink = Link.fromUri(uriInfo.getBaseUriBuilder().path(TimeResource.class).path(jogador.time.id.toString()).build())
                .rel("time").type(MediaType.APPLICATION_JSON).build();

        return new JogadorDTO(jogador.id, jogador.nome, jogador.numeroCamisa, jogador.posicoes, List.of(selfLink, timeLink));
    }
}