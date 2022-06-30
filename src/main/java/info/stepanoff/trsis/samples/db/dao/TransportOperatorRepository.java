package info.stepanoff.trsis.samples.db.dao;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TransportOperatorRepository extends CrudRepository<TransportOperator, String> {

    List<TransportOperator> findAll(Pageable page);

    List<TransportOperator> findAllByAbroad(String abroad);

    List<TransportOperator> findAllByChildren(String abroad);

    Integer countAllBy();

    TransportOperator findByTelephone(String login);

    TransportOperator getByNumber(Integer id);

    TransportOperator deleteByNumber(Integer id);

    TransportOperator findByEmail(String email);


}
