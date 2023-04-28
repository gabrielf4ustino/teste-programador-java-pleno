package br.com.ffscompany.marketplacemanager.service;

import br.com.ffscompany.marketplacemanager.dto.ClientDto;
import br.com.ffscompany.marketplacemanager.model.ClientModel;
import br.com.ffscompany.marketplacemanager.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public void register(ClientDto client) {
        ClientModel clientModel = new ClientModel(
                null,
                client.name(),
                client.cpf(),
                client.telephone(),
                client.email()
        );
    }
}
