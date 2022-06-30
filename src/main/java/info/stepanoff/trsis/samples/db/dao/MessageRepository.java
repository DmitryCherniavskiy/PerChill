package info.stepanoff.trsis.samples.db.dao;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Message;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findAllBy();

    Iterable<Message> findAllByClientMessageAndToMessage(Client client, TransportOperator to);

    Iterable<Message> findAllByToMessage(TransportOperator to);

    Iterable<Message> findAllByClientMessage(Client to);

}
