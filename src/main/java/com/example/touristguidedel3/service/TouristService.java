package com.example.touristguidedel3.service;

import com.example.touristguidedel3.model.TouristAttraction;
import com.example.touristguidedel3.repository.TouristRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;


    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public List<TouristAttraction> getAttractions() {
        return repository.getAttractions();
    }

    public TouristAttraction findAttractionByName(String name) {
        return repository.findAttractionByName(name);
    }

    @Transactional
    public void saveAttraction(TouristAttraction attraction) {
        int locationId = repository.findLocationId(attraction.getLocation());
        int attractionId = repository.saveAttraction(attraction, locationId);
        repository.saveAttraction_tags(attractionId, attraction.getTags());
    }


    public void updateAttraction(TouristAttraction attraction){
        repository.updateAttraction(attraction);
    }

    @Transactional
    public void deleteAttraction(String nameOfAttraction){
        repository.deleteAttraction(nameOfAttraction);
    }
    public List<String> getCities() {
        return repository.getCities();
    }
    public List<String>getTags(){
        return repository.getTags();
    }

}

