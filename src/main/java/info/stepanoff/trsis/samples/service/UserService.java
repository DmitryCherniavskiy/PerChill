package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.User;

public interface UserService {
    Iterable<User> listAll();

    void delete(Integer id);

    User getById(Integer id);

    User findByUsername(String telephone);

    void save(User user, Integer role_id);

}
