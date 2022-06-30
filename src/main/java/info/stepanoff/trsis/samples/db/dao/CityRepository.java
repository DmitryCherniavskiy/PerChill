package info.stepanoff.trsis.samples.db.dao;

import info.stepanoff.trsis.samples.db.model.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Integer> {
}
