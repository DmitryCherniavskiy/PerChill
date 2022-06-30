package info.stepanoff.trsis.samples.db.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="message")
public class Message implements Comparable<Message>{

    public Message(){}

    public Message(String text,TransportOperator to, Client client, String from){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.date = formatter.format(Calendar.getInstance().getTime());
        this.textMessage = text;
        this.clientMessage = client;
        this.toMessage = to;
        this.fromMessage = from;
    }


    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message_date")
    private String date;

    @Column(name = "message_text")
    private String textMessage;

    @Column(name = "message_from")
    private String fromMessage;

    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn (name="client_id")
    private Client clientMessage;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "to_id")
    private TransportOperator toMessage;

    @Override
    public int compareTo(Message message) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Integer result = 0;
        try {
            try {
                Date date1 = formatter.parse(this.date);
            }catch (Exception ex1){
                return 1;
            }
            try {
                Date date2 = formatter.parse(message.date);
            }catch (Exception ex1){
                return -1;
            }
            Date date1 = formatter.parse(this.date);
            Date date2 = formatter.parse(message.date);
            result = date1.compareTo(date2);
        } catch (Exception e) {
            e.printStackTrace();
            result=0;
        }
        return (-result);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getFromMessage() {
        return fromMessage;
    }

    public void setFromMessage(String fromMessage) {
        this.fromMessage = fromMessage;
    }

    public Client getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(Client clientMessage) {
        this.clientMessage = clientMessage;
    }

    public TransportOperator getToMessage() {
        return toMessage;
    }

    public void setToMessage(TransportOperator toMessage) {
        this.toMessage = toMessage;
    }
}
