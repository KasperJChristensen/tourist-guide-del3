package com.example.touristguidedel3.service;

import com.example.touristguidedel3.exception.AttractionNotFound;
import com.example.touristguidedel3.exception.DatabaseOperationException;
import com.example.touristguidedel3.exception.DuplicateAttractionException;
import com.example.touristguidedel3.exception.InvalidAttractionException;
import com.example.touristguidedel3.model.TouristAttraction;
import com.example.touristguidedel3.repository.TouristRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return repository.getAttractions();
        } catch (DataAccessException attractions) {
            throw new DatabaseOperationException("Failed to retrieve attractions.", attractions);
        }
    }

    public TouristAttraction findAttractionById(int id) {
        validateId(id);
        TouristAttraction attraction;
        try {
            attraction = repository.findAttractionById(id);
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("Failed to retrieve attration.", exception);
        }
        if (attraction == null) {
            throw new AttractionNotFound(id);
        }
        return attraction;
    }

    @Transactional
    public void saveAttraction(TouristAttraction attraction) {
        validateAttraction(attraction);
        try {
            int locationId = repository.findLocationId(attraction.getLocation());
            int attractionId = repository.saveAttraction(attraction, locationId);
            repository.saveAttraction_tags(attractionId, attraction.getTags());

        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateAttractionException("Attraction already exists.");

        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("Failed to create attraction.", exception);
        }
    }

    @Transactional
    public void updateAttraction(TouristAttraction attraction) {
        validateAttraction(attraction);
        int locationId = repository.findLocationId(attraction.getLocation());
        try {
            repository.updateAttraction(attraction, locationId);
            repository.deleteTagsForAttraction(attraction.getId());
            repository.saveAttraction_tags(attraction.getId(), attraction.getTags());

        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateAttractionException("Attraction already exists.");

        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("Failed to create attraction.", exception);
        }
    }

    @Transactional
    public void deleteAttraction(int idOfAttraction) {
        repository.deleteAttraction(idOfAttraction);
    }

    public List<String> getCities() {
        return repository.getCities();
    }

    public List<String> getTags() {
        return repository.getTags();
    }
    private void validateId(int id) {
        if (id <= 0) {
            throw new InvalidAttractionException("ID must be a positive integer.");
        }
    }
    private void validateAttraction(TouristAttraction attraction) {
        if (attraction == null) {
            throw new InvalidAttractionException("Attraction is required.");
        }
        String name = attraction.getName();
        if (name == null || name.isBlank()) {
            throw new InvalidAttractionException("name is required.");
        }
        if (name.length() > 100) {
            throw new InvalidAttractionException("Name must be at most character.");
        }
        validateLocation(attraction.getLocation());
    }

    private void validateLocation(String location) {
        if (location == null || location.isBlank()) {
            throw new InvalidAttractionException("Location is required.");
        }
    }
}