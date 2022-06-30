package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.model.Transport;

public interface TransportService {
    Iterable<Transport> listAll();

    Iterable<Transport> listAllByTo(Integer toId);

    void delete(Integer id);

    Transport add(Integer id, String model, String transport_class, String description, String image, Integer year, Integer fk_to_id);

    Transport add(Transport transport);

    Transport getById(Integer id);
}
