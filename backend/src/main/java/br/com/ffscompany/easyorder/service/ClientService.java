package br.com.ffscompany.easyorder.service;

import br.com.ffscompany.easyorder.dto.ClientDTO;
import br.com.ffscompany.easyorder.model.ClientModel;
import br.com.ffscompany.easyorder.model.OrderModel;
import br.com.ffscompany.easyorder.repository.ClientRepository;
import br.com.ffscompany.easyorder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements br.com.ffscompany.easyorder.service.Service<ClientModel, ClientDTO> {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Registers a new client.
     *
     * @param clientToRegister the client data to register.
     * @return the registered client model.
     */
    public ClientModel register(ClientDTO clientToRegister) {
        ClientModel clientModel = new ClientModel(
                clientToRegister.id(),
                clientToRegister.name(),
                clientToRegister.cpf(),
                clientToRegister.telephone(),
                clientToRegister.email()
        );
        clientRepository.save(clientModel);
        return clientModel;
    }

    /**
     * Edits an existing client.
     *
     * @param clientToEdit the client data to edit.
     * @return an optional containing the edited client model if it exists, otherwise an empty optional.
     */
    @Override
    public Optional<ClientModel> edit(ClientDTO clientToEdit) {
        Optional<ClientModel> clientModel = clientRepository.findById(clientToEdit.id());
        clientModel.ifPresent(client -> {
                    client.setName(clientToEdit.name());
                    client.setCpf(clientToEdit.cpf());
                    client.setTelephone(clientToEdit.telephone());
                    client.setEmail(clientToEdit.email());
                    clientRepository.save(client);
                }
        );
        return clientModel;
    }

    /**
     * Removes a client and all their orders.
     *
     * @param id the ID of the client to remove.
     */
    @Override
    public void remove(Long id) {
        Optional<ClientModel> clientModel = clientRepository.findById(id);
        clientModel.ifPresent(model -> {
            List<OrderModel> orders = orderRepository.findAllByClient(model);
            orderRepository.deleteAll(orders);
            clientRepository.delete(model);
        });
    }

    /**
     * Returns a list of all clients.
     *
     * @return a list of client models.
     */
    @Override
    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    /**
     * Finds a client by ID.
     *
     * @param id the ID of the client to find.
     * @return an optional containing the client model if it exists, otherwise an empty optional.
     */
    @Override
    public Optional<ClientModel> findById(Long id) {
        return clientRepository.findById(id);
    }
}
