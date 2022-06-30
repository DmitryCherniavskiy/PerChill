package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.dao.CityRepository;
import info.stepanoff.trsis.samples.db.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CityServiceImpl implements CityService{
    @Autowired
    private CityRepository cityRepository;

    @Override
    public Iterable<City> listAll() {
        return cityRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        try {
            cityRepository.deleteById(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            //for the reason of idempotency leave this blank
        }
    }

    @Override
    public City add(Integer number, String name) {
        return cityRepository.save(new City(number, name));
    }

    @Override
    public City getById(Integer id) {
        return cityRepository.findById(id).orElse(null);
    }

}
