package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface OrderService {
    Iterable<Order> listAll();

    void delete(Integer id);

    Order add(Order order);

    Order add(Double grade, Double price, String start_place, String end_place, String date, String date_start, String date_end, String comment, Integer transport, Integer fk_to_id, Integer fk_client_id);

    Order getById(Integer id);

    List<Order> listAllByClient(Client client_id);

    List<Order> listAllByTo(TransportOperator to_id);
}
