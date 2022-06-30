package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Order;

import java.util.List;

public interface ClientService {
    Iterable<Client> listAll();

    void delete(String id);

    Client getById(String id);

    void save(Client user);

    Client findByUsername(String username);

    Client findByEmail(String email);

}
