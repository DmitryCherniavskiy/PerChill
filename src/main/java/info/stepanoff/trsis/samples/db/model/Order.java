package info.stepanoff.trsis.samples.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Order")
public class Order implements Comparable<Order>{

    public Order() {
    }

    public Order(Client client, TransportOperator to, Integer transport){
        this.client=client;
        this.to=to;
        this.transport=transport;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.date = formatter.format(Calendar.getInstance().getTime());
    }

    public Order(Double grade, Double price, String start_place, String end_place, String date, String date_start, String date_end,String comment, Integer transport,  Integer fk_to_id, Integer fk_client_id) {
        this.grade = grade;
        this.price = price;
        this.start_place = start_place;
        this.end_place = end_place;
        this.date = date;
        this.date_start = date_start;
        this.date_end = date_end;
        this.comment = comment;
        this.transport = transport;
    }


    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_grade")
    private Double grade;

    @Column(name = "order_price")
    private Double price;

    @Column(name = "start_place")
    private String start_place;

    @Column(name = "end_place")
    private String end_place;

    @Column(name = "order_date")
    private String date;

    @Column(name = "date_start")
    private String date_start;

    @Column(name = "date_end")
    private String date_end;

    @Column(name = "order_status")
    private String status;

    @Column(name = "transport_id")
    private Integer transport;

    @Column(name = "order_comment")
    private String comment;

    @ManyToOne (fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn (name="fk_client_id")
    private Client client;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_to_id")
    private TransportOperator to;

    @Override
    public int compareTo(Order order) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Integer result = 0;
        try {
            try {
                Date date1 = formatter.parse(this.date);
            }catch (Exception ex1){
                return 1;
            }
            try {
                Date date2 = formatter.parse(order.date);
            }catch (Exception ex1){
                return -1;
            }
            Date date1 = formatter.parse(this.date);
            Date date2 = formatter.parse(order.date);
            result = date1.compareTo(date2);
        } catch (Exception e) {
            e.printStackTrace();
            result=0;
        }
        return (-result);
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStart_place() {
        return start_place;
    }

    public void setStart_place(String start_place) {
        this.start_place = start_place;
    }

    public String getEnd_place() {
        return end_place;
    }

    public void setEnd_place(String end_place) {
        this.end_place = end_place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TransportOperator getTo() {
        return to;
    }

    public void setTo(TransportOperator to) {
        this.to = to;
    }

    public Integer getTransport() {
        return transport;
    }

    public void setTransport(Integer transport) {
        this.transport = transport;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}