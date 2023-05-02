package br.com.ffscompany.easyorder.controller;

import br.com.ffscompany.easyorder.dto.ClientDTO;
import br.com.ffscompany.easyorder.model.ClientModel;
import br.com.ffscompany.easyorder.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cliente")
@Transactional // Defines that all operations within this class must be executed within a transaction
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class ClientController {

    @Autowired // Injects the ClientService dependency
    private ClientService clientService;

    /**
     * Endpoint to register a new client.
     *
     * @param client     the DTO representing the client to be registered
     * @param uriBuilder the UriComponentsBuilder used to create the URI for the created resource
     * @return a ResponseEntity representing the created client, with the Location header set to the URI of the created resource
     */
    @PostMapping("cadastro")
    public ResponseEntity<ClientDTO> clientRegister(@RequestBody @Valid ClientDTO client, UriComponentsBuilder uriBuilder) {
        ClientModel registeredClient = clientService.register(client);

        return getResponseEntity(uriBuilder, registeredClient);
    }

    /**
     * Endpoint to edit an existing client.
     *
     * @param client     the DTO representing the client to be edited
     * @param uriBuilder the UriComponentsBuilder used to create the URI for the edited resource
     * @return a ResponseEntity representing the edited client, with the Location header set to the URI of the edited resource
     * or a 404 Not Found response if the client does not exist.
     */
    @PutMapping("editar")
    public ResponseEntity<ClientDTO> editClient(@RequestBody @Valid ClientDTO client, UriComponentsBuilder uriBuilder) {
        Optional<ClientModel> editedClient = clientService.edit(client);
        if (editedClient.isPresent()) {
            return getResponseEntity(uriBuilder, editedClient.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Helper method to create a ResponseEntity for a ClientDTO object with the Location header set to the URI of the client.
     *
     * @param uriBuilder  the UriComponentsBuilder used to create the URI for the client
     * @param clientModel the ClientModel object to be converted to a ClientDTO
     * @return a ResponseEntity representing the client, with the Location header set to the URI of the client
     */
    private static ResponseEntity<ClientDTO> getResponseEntity(UriComponentsBuilder uriBuilder, ClientModel clientModel) {
        return ResponseEntity
                .created(uriBuilder.path("/cliente/{id}").buildAndExpand(clientModel.getId()).toUri())
                .body(new ClientDTO(
                        clientModel.getId(),
                        clientModel.getName(),
                        clientModel.getCpf(),
                        clientModel.getTelephone(),
                        clientModel.getEmail()));
    }

    /**
     * Endpoint to delete an existing client.
     *
     * @param id the ID of the client to be deleted
     * @return a ResponseEntity with a 200 OK status if the client was successfully deleted, or an error response otherwise
     */
    @DeleteMapping("remover/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.remove(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to retrieve all clients.
     *
     * @return a ResponseEntity containing a list of all clients, represented as ClientDTOs
     */
    @GetMapping("todos")
    public ResponseEntity<List<ClientDTO>> findAllClients() {
        List<ClientDTO> clientDTOS = clientService.findAll().stream()
                .map(clientModel -> new ClientDTO(
                        clientModel.getId(),
                        clientModel.getName(),
                        clientModel.getCpf(),
                        clientModel.getTelephone(),
                        clientModel.getEmail()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientDTOS);
    }

    /**
     * Endpoint to retrieve a specific client by their ID.
     *
     * @param id the ID of the client to retrieve
     * @return a ResponseEntity representing the retrieved client, or a 404 Not Found response if the client does not exist
     */
    @GetMapping("{id}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id) {
        Optional<ClientModel> clientModel = clientService.findById(id);
        if (clientModel.isPresent()) {
            ClientDTO clientDTO = new ClientDTO(
                    clientModel.get().getId(),
                    clientModel.get().getName(),
                    clientModel.get().getCpf(),
                    clientModel.get().getTelephone(),
                    clientModel.get().getEmail());
            return ResponseEntity.ok(clientDTO);
        }
        return ResponseEntity.notFound().build();
    }
}