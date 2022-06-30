package info.stepanoff.trsis.samples.db.model;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.text.ParseException;
import java.util.List;

@Entity
@Table(name = "Client")
public class Client {

    public Client() {
    }

    public Client(String name, String sname, String pname, String email, String telephone, Integer fk_city_id) {
        this.name = name;
        this.sname = sname;
        this.pname = pname;
        this.telephone = telephone;
        this.email = email;
        this.fk_city_id = fk_city_id;
    }

    public Client(String name, String sname, String pname, String email, String image) {
        this.name = name;
        this.sname = sname;
        this.pname = pname;
        this.email = email;
        this.image = image;
    }

    @Id
    @Column(name = "client_telephone", unique = true, nullable = false)
    private String telephone;

    @Column(name = "client_id")
    private Integer number;

    @Column(name = "client_image")
    private String image;

    @Column(name = "client_name", nullable = false)
    private String name;

    @Column(name = "client_sname", nullable = false)
    private String sname;

    @Column(name = "client_pname", nullable = false)
    private String pname;

    @Column(name = "client_email", nullable = false)
    private String email;

    @Column(name = "fk_city_id", nullable = false)
    private Integer fk_city_id;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> order;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientMessage", cascade = CascadeType.ALL)
    private List<Message> message;

    public static Client parseFromJson(JSONObject oJson) throws JSONException, ParseException {
        try {
            var name = oJson.get("name").toString();
            var sname = oJson.get("sname").toString();
            var pname = oJson.get("pname").toString();
            var email = oJson.get("email").toString();
            var image = oJson.get("image").toString();
            return new Client(name, sname, pname, email, image);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getFk_city_id() {
        return fk_city_id;
    }

    public void setFk_city_id(Integer fk_city_id) {
        this.fk_city_id = fk_city_id;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public Integer getId() {
        return number;
    }

    public void setId(Integer number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}