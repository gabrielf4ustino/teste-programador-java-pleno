package br.com.ffscompany.marketplacemanager.service;

import br.com.ffscompany.marketplacemanager.dto.ClientDTO;
import br.com.ffscompany.marketplacemanager.model.ClientModel;
import br.com.ffscompany.marketplacemanager.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public void register(ClientDTO clientToRegister) {
        try {
            ClientModel clientModel = new ClientModel(
                    null,
                    clientToRegister.name(),
                    clientToRegister.cpf(),
                    clientToRegister.telephone(),
                    clientToRegister.email()
            );
            clientRepository.save(clientModel);
        }catch (Exception e){
            throw new HttpClientErrorException(HttpStatusCode.valueOf(500));
        }
    }

    public List<ClientModel> findAllClients(){
        return clientRepository.findAll();
    }
}
