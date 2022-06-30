package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Message;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.db.model.TransportOperator;

import java.util.List;


public interface MessageService {
    List<Message> listAll(TransportOperator to, Client client);

    Message add(Message message);

    Message add(String text, TransportOperator to, Client client, String from);
}
