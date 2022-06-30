package info.stepanoff.trsis.samples.db.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    public User() {
    }

    public User(String telephone, String passhash) {
        this.telephone = telephone;
        this.passhash= passhash;
    }

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_telephone", unique = true, nullable = false)
    private String telephone;

    @Column(name = "user_passhash", nullable = false)
    private String passhash;

    @Transient
    private String confirmPasshash;

    //private boolean isEnabled;

    ////// role connect //////

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    /////////////////////////////////////////////////////////////////////////////////////////////////


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public String getConfirmPasshash() {
        return confirmPasshash;
    }

    public void setConfirmPasshash(String confirmPasshash) {
        this.confirmPasshash = confirmPasshash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /*public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }*/
}
