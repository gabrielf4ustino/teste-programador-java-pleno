package br.com.ffscompany.marketplacemanager.controller;

import br.com.ffscompany.marketplacemanager.dto.ClientDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cliente")
public class ClientController {

    @PostMapping("cadastro")
    public void clientRegister(@PathVariable ClientDto request) {

    }

    @GetMapping("todos")
    public ClientDto findAllClients(){
        return new ClientDto();
    }
}
