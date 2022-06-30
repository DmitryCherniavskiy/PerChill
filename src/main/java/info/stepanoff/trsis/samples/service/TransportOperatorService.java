package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.TransportOperator;

public interface TransportOperatorService {

    Iterable<TransportOperator> listAll(Integer p);

    Iterable<TransportOperator> listAbroad();

    Iterable<TransportOperator> listChildren();

    void setGrade(String telephone);

    void setRecomendation(String telephone);

    void delete(Integer id);

    TransportOperator getByTelephone(String telephone);

    TransportOperator getById(Integer id);

    Integer count();

    void save(TransportOperator user);

    TransportOperator findByEmail(String email);
}
