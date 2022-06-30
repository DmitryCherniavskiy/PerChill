package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.dao.ClientRepository;
import info.stepanoff.trsis.samples.db.dao.TransportOperatorRepository;
import info.stepanoff.trsis.samples.db.dao.UserRepository;
import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Role;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import info.stepanoff.trsis.samples.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String telephone) throws UsernameNotFoundException {
        User user = userRepository.findByTelephone(telephone);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getTelephone(), user.getPasshash(), grantedAuthorities);
    }

}