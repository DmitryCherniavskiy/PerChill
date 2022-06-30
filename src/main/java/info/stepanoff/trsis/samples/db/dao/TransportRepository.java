package info.stepanoff.trsis.samples.db.dao;


import info.stepanoff.trsis.samples.db.model.*;
import org.springframework.data.repository.CrudRepository;

public interface TransportRepository extends CrudRepository<Transport, Integer> {
    Iterable<Transport> findAllByToTransport(TransportOperator To);
}
