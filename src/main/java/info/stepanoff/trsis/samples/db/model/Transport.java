package info.stepanoff.trsis.samples.db.model;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.text.ParseException;
import java.util.List;

@Entity
@Table(name = "Transport")
public class Transport {

    public Transport() {
    }

    public Transport(String model, String transportClass, Integer year, Integer number, String description) {
        this.model = model;
        this.transportClass = transportClass;
        this.description = description;
        this.year = year;
        this.number = number;
    }

    public Transport(Integer id, String model, String transportClass, String description, String image, Integer year) {
        this.id = id;
        this.model = model;
        this.transportClass = transportClass;
        this.description = description;
        this.image = image;
        this.year = year;
    }


    @Id
    @Column(name = "transport_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transport_model")
    private String model;

    @Column(name = "transport_class")
    private String transportClass;

    @Column(name = "transport_number")
    private Integer number;

    @Column(name = "transport_description")
    private String description;

    @Column(name = "transport_image")
    private String image;

    @Column(name = "transport_year")
    private Integer year;

    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "fk_to_id")
    private TransportOperator toTransport;

    public static Transport parseFromJson(JSONObject oJson) throws JSONException, ParseException {
        try {
            var model = oJson.get("model").toString();
            var tclass = oJson.get("tclass").toString();
            Integer year = Integer.parseInt(oJson.get("year").toString());
            Integer number = Integer.parseInt(oJson.get("number").toString());
            var desc = oJson.get("desc").toString();
            return new Transport(model, tclass, year, number, desc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer getId() {
        return id;
    }

    public TransportOperator getToTransport() {
        return toTransport;
    }

    public void setToTransport(TransportOperator toTransport) {
        this.toTransport = toTransport;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTransportClass() {
        return transportClass;
    }

    public void setTransportClass(String transportClass) {
        this.transportClass = transportClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}