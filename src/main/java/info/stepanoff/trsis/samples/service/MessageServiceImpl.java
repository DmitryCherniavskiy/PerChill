package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.dao.ClientRepository;
import info.stepanoff.trsis.samples.db.dao.MessageRepository;
import info.stepanoff.trsis.samples.db.dao.TransportOperatorRepository;
import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Message;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> listAll(TransportOperator to, Client client) {
        List result = new ArrayList();
        messageRepository.findAllByClientMessageAndToMessage(client, to).forEach(result::add);
        return result;
    }

    @Override
    public Message add(String text, TransportOperator to, Client client, String from) {
        return messageRepository.save(new Message(text,to,client,from));
    }

    @Override
    public Message add(Message message) {
        return messageRepository.save(message);
    }

}
