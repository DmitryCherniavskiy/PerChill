package info.stepanoff.trsis.samples.service;


import info.stepanoff.trsis.samples.db.dao.ClientRepository;
import info.stepanoff.trsis.samples.db.dao.RoleDao;
import info.stepanoff.trsis.samples.db.dao.UserRepository;
import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Role;
import info.stepanoff.trsis.samples.db.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user, Integer roleId) {
        user.setPasshash(bCryptPasswordEncoder.encode(user.getPasshash()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(roleId));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String telephone) {
        return userRepository.findByTelephone(telephone);
    }

    @Override
    public Iterable<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            //for the reason of idempotency leave this blank
        }
    }


    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).get();
    }

}
