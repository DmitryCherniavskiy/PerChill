package info.stepanoff.trsis.samples.db.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import info.stepanoff.trsis.samples.service.OrderService;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "Transport_operator")
public class TransportOperator {

    public TransportOperator() {
    }

    public TransportOperator(String name, String sname, String pname, String email, String telephone, String description, Integer price_into, Integer price_between, Integer fk_city_id, String children, String abroad) {
        this.name = name;
        this.sname = sname;
        this.pname = pname;
        this.email = email;
        this.telephone = telephone;
        this.description = description;
        this.price_into = price_into;
        this.price_between = price_between;
        this.fk_city_id = fk_city_id;
        this.children = children;
        this.abroad = abroad;
    }

    public TransportOperator(String name, String sname, String pname, String email, String description, Integer price_into, Integer price_between, Integer fk_city_id, String children, String abroad) {
        this.name = name;
        this.sname = sname;
        this.pname = pname;
        this.email = email;
        this.description = description;
        this.price_into = price_into;
        this.price_between = price_between;
        this.fk_city_id = fk_city_id;
        this.children = children;
        this.abroad = abroad;
    }

    public TransportOperator(String name, String sname, String pname, String email, String telephone, Integer fk_city_id) {
        this.name = name;
        this.sname = sname;
        this.pname = pname;
        this.email = email;
        this.telephone = telephone;
        this.fk_city_id = fk_city_id;
        this.grade = 0.;
        this.recomendation = 5.;
    }

    public TransportOperator(String name, String sname, String pname, String email, String description) {
        this.name = name;
        this.sname = sname;
        this.pname = pname;
        this.email = email;
        this.description = description;
    }

    @Column(name = "to_id")
    private Integer number;

    @Id
    @Column(name = "to_telephone", nullable = false)
    private String telephone;

    @Column(name = "to_name", nullable = false)
    private String name;

    @Column(name = "to_sname", nullable = false)
    private String sname;

    @Column(name = "to_pname")
    private String pname;

    @Column(name = "to_email", nullable = false)
    private String email;

    @Column(name = "to_image")
    private String image;

    @Column(name = "to_description")
    private String description;

    @Column(name = "to_grade")
    private Double grade;

    @Column(name = "to_recomandation")
    private Double recomendation;

    @Column(name = "to_price_into")
    private Integer price_into;

    @Column(name = "to_price_between")
    private Integer price_between;

    @Column(name = "fk_city_id", nullable = false)
    private Integer fk_city_id;

    @Column(name = "to_children")
    private String children;

    @Column(name = "to_abroad")
    private String abroad;

    //////  transport connect  //////

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toTransport", cascade = CascadeType.ALL)
    private List<Transport> transport;

    /////   order connect    //////

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "to", cascade = CascadeType.ALL)
    private List<Order> orderto;

    /////    message connect   //////

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toMessage", cascade = CascadeType.ALL)
    private List<Message> message;

    //////////////////////////////////////////////////////////////////////////////////////////

    public static TransportOperator parseFromJson(JSONObject oJson) throws JSONException, ParseException {
        try {
            var name = oJson.get("name").toString();
            var sname = oJson.get("sname").toString();
            var pname = oJson.get("pname").toString();
            var email = oJson.get("email").toString();
            var desc = oJson.get("desc").toString();
            return new TransportOperator(name, sname, pname, email, desc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Double getRecomendation() {
        return recomendation;
    }

    public void setRecomendation(Double recomendation) {
        this.recomendation = recomendation;
    }

    public Integer getId() {
        return number;
    }

    public void setId(Integer id) {
        this.number = id;
    }

    public List<Order> getOrderto() {
        return orderto;
    }

    public void setOrderto(List<Order> order) {
        this.orderto = order;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public List<Transport> getTransport() {
        return transport;
    }

    public void setTransport(List<Transport> transport) {
        this.transport = transport;
    }

    public Integer getPrice_into() {
        return price_into;
    }

    public void setPrice_into(Integer price_into) {
        this.price_into = price_into;
    }

    public Integer getPrice_between() {
        return price_between;
    }

    public void setPrice_between(Integer price_between) {
        this.price_between = price_between;
    }

    public Integer getFk_city_id() {
        return fk_city_id;
    }

    public void setFk_city_id(Integer fk_city_id) {
        this.fk_city_id = fk_city_id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getAbroad() {
        return abroad;
    }

    public void setAbroad(String abroad) {
        this.abroad = abroad;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }
}






