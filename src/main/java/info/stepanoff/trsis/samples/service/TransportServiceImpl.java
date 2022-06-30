package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.dao.TransportOperatorRepository;
import info.stepanoff.trsis.samples.db.dao.TransportRepository;
import info.stepanoff.trsis.samples.db.model.Transport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransportServiceImpl implements TransportService{
    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private TransportOperatorRepository toRepository;

    @Override
    public Iterable<Transport> listAll() {
        return transportRepository.findAll();
    }

    @Override
    public Iterable<Transport> listAllByTo(Integer toId) {
        return transportRepository.findAllByToTransport(toRepository.getByNumber(toId));
    }

    @Override
    public void delete(Integer id) {
        try {
            transportRepository.deleteById(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) { }
    }

    @Override
    public Transport add(Integer id, String model, String transport_class, String description, String image, Integer year, Integer fk_to_id) {
        return transportRepository.save(new Transport(id, model, transport_class, description, image, year));
    }

    @Override
    public Transport add(Transport transport) {
        return transportRepository.save(transport);
    }

    @Override
    public Transport getById(Integer id) {
        return transportRepository.findById(id).orElse(null);
    }

}