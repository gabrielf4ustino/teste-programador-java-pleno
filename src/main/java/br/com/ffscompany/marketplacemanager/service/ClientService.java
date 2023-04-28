package br.com.ffscompany.marketplacemanager.service;

import br.com.ffscompany.marketplacemanager.dto.ClientDTO;
import br.com.ffscompany.marketplacemanager.model.ClientModel;
import br.com.ffscompany.marketplacemanager.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientModel register(ClientDTO clientToRegister) {
        ClientModel clientModel = new ClientModel(
                null,
                clientToRegister.name(),
                clientToRegister.cpf(),
                clientToRegister.telephone(),
                clientToRegister.email()
        );
        clientRepository.save(clientModel);
        return clientModel;
    }

    public List<ClientModel> findAllClients() {
        return clientRepository.findAll();
    }

    public Optional<ClientModel> findClientById(Long id){
        return clientRepository.findById(id);
    }
}
