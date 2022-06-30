package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.model.City;

public interface CityService {
    Iterable<City> listAll();

    void delete(Integer id);

    City add(Integer number, String name);

    City getById(Integer id);
}
