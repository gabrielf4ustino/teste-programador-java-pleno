package br.com.ffscompany.marketplacemanager.controller;

import br.com.ffscompany.marketplacemanager.dto.ClientDTO;
import br.com.ffscompany.marketplacemanager.model.ClientModel;
import br.com.ffscompany.marketplacemanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cliente")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("cadastro")
    public void clientRegister(@PathVariable ClientDTO request) {
        clientService.register(request);
    }

    @GetMapping("todos")
    public List<ClientModel> findAllClients() {
        return clientService.findAllClients();
    }
}
