package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.dao.OrderRepository;
import info.stepanoff.trsis.samples.db.dao.RoleDao;
import info.stepanoff.trsis.samples.db.dao.TransportOperatorRepository;
import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.db.model.Role;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class TransportOperatorServiceImpl implements TransportOperatorService{

    @Autowired
    private TransportOperatorRepository transportOperatorRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Iterable<TransportOperator> listAll(Integer p) {
        Pageable page = PageRequest.of(p, 2, Sort.by(Sort.Direction.DESC,"recomendation"));
        return transportOperatorRepository.findAll(page);
    }

    @Override
    public Iterable<TransportOperator> listAbroad(){
        return transportOperatorRepository.findAllByAbroad("Да");
    }

    @Override
    public void setGrade(String telephone){
        TransportOperator to = transportOperatorRepository.findByTelephone(telephone);
        Iterable<Order> orderList = orderRepository.findAllByTo(to);
        Double sum = 0.;
        Integer count = 0;
        for (Order order: orderList ){
            if (order.getGrade() != null) {
                sum = sum + order.getGrade();
                count = count + 1;
            }
        }
        to.setGrade(sum/count);
        transportOperatorRepository.save(to);
    }

    @Override
    public void setRecomendation(String telephone){
        TransportOperator to = transportOperatorRepository.findByTelephone(telephone);
        Iterable<Order> orderList = orderRepository.findAllByTo(to);
        Double sum = 0.;
        Integer count = 0;
        for (Order order: orderList ){
            if (order.getGrade() != null) {
                sum = sum + order.getGrade();
                count = count + 1;
            }
        }
        to.setRecomendation(sum/count);
        transportOperatorRepository.save(to);
    }

    @Override
    public Iterable<TransportOperator> listChildren(){
        return transportOperatorRepository.findAllByChildren("Да");
    }

    @Override
    public void delete(Integer id) {
        try {
            transportOperatorRepository.deleteByNumber(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            //for the reason of idempotency leave this blank
        }
    }

    @Override
    public TransportOperator getByTelephone(String id) {
        return transportOperatorRepository.findByTelephone(id);
    }

    @Override
    public TransportOperator getById(Integer id){
        return  transportOperatorRepository.getByNumber(id);
    }

    @Override
    public Integer count(){
        return transportOperatorRepository.countAllBy();
    }

    @Override
    public void save(TransportOperator client) {
        transportOperatorRepository.save(client);
    }

    @Override
    public TransportOperator findByEmail(String email){
        return transportOperatorRepository.findByEmail(email);
    }

}
