package br.com.ffscompany.marketplacemanager.controller;

import br.com.ffscompany.marketplacemanager.dto.ClientDTO;
import br.com.ffscompany.marketplacemanager.model.ClientModel;
import br.com.ffscompany.marketplacemanager.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cliente")
@Transactional
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("cadastro")
    public ResponseEntity clientRegister(@RequestBody @Valid ClientDTO client, UriComponentsBuilder uriBuilder) {
        ClientModel registeredClient = clientService.register(client);

        return ResponseEntity
                .created(uriBuilder.path("/cliente/{id}").buildAndExpand(registeredClient.getId()).toUri())
                .body(new ClientDTO(
                        registeredClient.getId(),
                        registeredClient.getName(),
                        registeredClient.getCpf(),
                        registeredClient.getTelephone(),
                        registeredClient.getEmail()));
    }

    @GetMapping("todos")
    public ResponseEntity<List<ClientModel>> findAllClients() {
        return ResponseEntity.ok(clientService.findAllClients());
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<ClientModel>> findClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findClientById(id));
    }
}
