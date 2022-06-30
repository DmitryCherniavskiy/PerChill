package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.dao.ClientRepository;
import info.stepanoff.trsis.samples.db.dao.OrderRepository;
import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Iterable<Order> listAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> listAllByClient(Client client) {
        List result = new ArrayList();
        orderRepository.findAllByClient(client).forEach(result::add);
        return result;
    }

    @Override
    public List<Order> listAllByTo(TransportOperator to_id) {
        List result = new ArrayList();
        orderRepository.findAllByTo(to_id).forEach(result::add);
        return result;
    }

    @Override
    public void delete(Integer id) {
        try {
            orderRepository.deleteById(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            //for the reason of idempotency leave this blank
        }
    }

    @Override
    public Order add(Double grade, Double price, String start_place, String end_place, String date, String date_start, String date_end, String comment, Integer transport, Integer fk_to_id, Integer fk_client_id) {
        return orderRepository.save(new Order(grade, price, start_place, end_place, date, date_start, date_end, comment, transport, fk_to_id, fk_client_id));
    }

    @Override
    public Order add(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getById(Integer id) {
        return orderRepository.findById(id).get();
    }



}
