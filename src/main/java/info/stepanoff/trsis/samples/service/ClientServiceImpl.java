package info.stepanoff.trsis.samples.service;


import info.stepanoff.trsis.samples.db.dao.ClientRepository;
import info.stepanoff.trsis.samples.db.dao.RoleDao;
import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.db.model.Role;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client findByUsername(String telephone) {
        return clientRepository.findByTelephone(telephone);
    }

    @Override
    public Iterable<Client> listAll() {
        return clientRepository.findAll();
    }

    @Override
    public void delete(String id) {
        try {
            clientRepository.deleteById(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            //for the reason of idempotency leave this blank
        }
    }


    @Override
    public Client getById(String id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public Client findByEmail(String email){
        return clientRepository.findByEmail(email);
    }

}
