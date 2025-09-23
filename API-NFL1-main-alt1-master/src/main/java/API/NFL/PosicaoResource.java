package API.NFL;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/posicoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Tag(name = "Posições", description = "Operações para gerenciamento de posições de jogadores")
public class PosicaoResource {

    @Context
    UriInfo uriInfo;

    // 1. CREATE (POST)
    @POST
    @Transactional
    @Operation(summary = "Criar uma nova posição")
    @APIResponse(responseCode = "201", description = "Posição criada")
    @APIResponse(responseCode = "400", description = "Dados inválidos (ex: sigla já existe)")
    public Response criarPosicao(@Valid Posicao posicao) {
        posicao.persist();
        URI location = uriInfo.getAbsolutePathBuilder().path(posicao.id.toString()).build();
        return Response.created(location).entity(posicao).build();
    }

    // 2. READ (GET All)
    @GET
    @Operation(summary = "Listar todas as posições")
    public Response listarPosicoes() {
        List<Posicao> posicoes = Posicao.listAll();
        List<PosicaoDTO> dtos = posicoes.stream().map(this::toDTO).collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    // 3. READ (GET by ID)
    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar posição por ID")
    @APIResponse(responseCode = "200", description = "Posição encontrada")
    @APIResponse(responseCode = "404", description = "Posição não encontrada")
    public Response buscarPosicaoPorId(@PathParam("id") Long id) {
        return Posicao.<Posicao>findByIdOptional(id)
                .map(posicao -> Response.ok(toDTO(posicao)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // 4. UPDATE (PUT)
    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar uma posição existente")
    public Response atualizarPosicao(@PathParam("id") Long id, @Valid Posicao posicaoAtualizada) {
        return Posicao.<Posicao>findByIdOptional(id)
                .map(posicao -> {
                    posicao.sigla = posicaoAtualizada.sigla;
                    posicao.nome = posicaoAtualizada.nome;
                    posicao.persist();
                    return Response.ok(posicao).build();
                }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // 5. DELETE
    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Deletar uma posição")
    @APIResponse(responseCode = "204", description = "Posição deletada")
    @APIResponse(responseCode = "404", description = "Posição não encontrada")
    public Response deletarPosicao(@PathParam("id") Long id) {
        if (Posicao.deleteById(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6. ENDPOINT COM CONSULTA PERSONALIZADA
    @GET
    @Path("/buscar")
    @Operation(summary = "Buscar posição pela sigla")
    public Response buscarPorSigla(@Parameter(description = "Sigla da posição (ex: QB, WR)", required = true) @QueryParam("sigla") String sigla) {
        if (sigla == null || sigla.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("O parâmetro 'sigla' é obrigatório.").build();
        }
        return Posicao.find("sigla", sigla).firstResultOptional()
                .map(posicao -> Response.ok(posicao).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // Método auxiliar para HATEOAS
    private PosicaoDTO toDTO(Posicao posicao) {
        Link selfLink = Link.fromUri(uriInfo.getAbsolutePathBuilder().path(posicao.id.toString()).build())
                .rel("self")
                .type(MediaType.APPLICATION_JSON)
                .build();
        return new PosicaoDTO(posicao.id, posicao.sigla, posicao.nome, List.of(selfLink));
    }
}