package info.stepanoff.trsis.samples.db.dao;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAllBy();

    Iterable<Order> findAllByClient(Client client);

    Iterable<Order> findAllByTo(TransportOperator to);

    Integer countAllByTo(TransportOperator to);

}
