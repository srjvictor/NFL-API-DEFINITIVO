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

@Path("/api/times")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Tag(name = "Times", description = "Operações para gerenciamento de times da NFL")
public class TimeResource {

    @Context
    UriInfo uriInfo;

    // 1. CREATE (POST)
    @POST
    @Transactional
    @Operation(summary = "Criar um novo time")
    @APIResponse(responseCode = "201", description = "Time criado com sucesso")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    public Response criarTime(@Valid Time time) {
        time.persist();
        URI location = uriInfo.getAbsolutePathBuilder().path(time.id.toString()).build();
        return Response.created(location).entity(time).build();
    }

    // 2. READ (GET All)
    @GET
    @Operation(summary = "Listar todos os times")
    public Response listarTimes() {
        List<Time> times = Time.listAll();
        List<TimeDTO> dtos = times.stream().map(this::toDTO).collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    // 3. READ (GET by ID)
    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar time por ID")
    @APIResponse(responseCode = "200", description = "Time encontrado")
    @APIResponse(responseCode = "404", description = "Time não encontrado")
    public Response buscarTimePorId(@PathParam("id") Long id) {
        return Time.<Time>findByIdOptional(id)
                .map(time -> Response.ok(toDTO(time)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // 4. UPDATE (PUT)
    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar um time existente")
    @APIResponse(responseCode = "200", description = "Time atualizado")
    @APIResponse(responseCode = "404", description = "Time não encontrado")
    public Response atualizarTime(@PathParam("id") Long id, @Valid Time timeAtualizado) {
        return Time.<Time>findByIdOptional(id)
                .map(time -> {
                    time.nome = timeAtualizado.nome;
                    time.cidade = timeAtualizado.cidade;
                    time.conferencia = timeAtualizado.conferencia;
                    time.estadio.nome = timeAtualizado.estadio.nome;
                    time.estadio.capacidade = timeAtualizado.estadio.capacidade;
                    time.persist();
                    return Response.ok(time).build();
                }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // 5. DELETE
    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Deletar um time")
    @APIResponse(responseCode = "204", description = "Time deletado")
    @APIResponse(responseCode = "404", description = "Time não encontrado")
    public Response deletarTime(@PathParam("id") Long id) {
        if (Time.deleteById(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6. ENDPOINT COM CONSULTA PERSONALIZADA
    @GET
    @Path("/buscar")
    @Operation(summary = "Buscar times por conferência (AFC ou NFC)")
    public Response buscarPorConferencia(@Parameter(description = "Conferência (AFC ou NFC)", required = true) @QueryParam("conferencia") Conferencia conferencia) {
        if (conferencia == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("O parâmetro 'conferencia' é obrigatório.").build();
        }
        List<Time> times = Time.list("conferencia", conferencia);
        return Response.ok(times).build();
    }

    // Método auxiliar para criar DTO com links HATEOAS
    private TimeDTO toDTO(Time time) {
        Link selfLink = Link.fromUri(uriInfo.getAbsolutePathBuilder().path(time.id.toString()).build())
                .rel("self")
                .type(MediaType.APPLICATION_JSON)
                .build();

        // Link para a lista de jogadores do time (exemplo)
        // Assumindo que você terá um JogadorResource em /api/jogadores
        UriBuilder jogadorUriBuilder = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("jogadores")
                .queryParam("timeId", time.id);

        Link jogadoresLink = Link.fromUri(jogadorUriBuilder.build())
                .rel("jogadores")
                .type(MediaType.APPLICATION_JSON)
                .build();

        return new TimeDTO(time.id, time.nome, time.cidade, time.conferencia, time.estadio, List.of(selfLink, jogadoresLink));
    }
}