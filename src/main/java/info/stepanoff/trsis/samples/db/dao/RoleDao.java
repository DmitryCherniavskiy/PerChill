package info.stepanoff.trsis.samples.db.dao;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {
    Role findByRoleName(String name);
}
