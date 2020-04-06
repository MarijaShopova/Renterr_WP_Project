package com.finki.renterr.api.validation;

import com.finki.renterr.model.domain.Apartment;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class LocationCheckValidator implements ConstraintValidator<LocationCheck, Apartment> {

    private final String[] skopjeMunicipalities = {"Aerodrom", "Aracinovo", "Butel", "Cair", "Centar", "Cucer-Sandevo",
            "Gazi Baba", "Gjorce Petrov", "Ilinden", "Karpos", "Kisela Voda", "Petrovec", "Saraj", "Sopiste",
            "Studenicani", "Suto Orizari", "Zelenikovo"};
    private final String[] ohridMunicipalities = {"Ohrid"};
    private final String[] stipMunicipalities = {"Stip"};

    private HashMap<String, List<String>> citiesMap;

    @Override
    public void initialize(LocationCheck constraintAnnotation) {
        this.citiesMap = new HashMap<>();
        this.citiesMap.put("Skopje", Arrays.asList(skopjeMunicipalities));
        this.citiesMap.put("Ohrid", Arrays.asList(ohridMunicipalities));
        this.citiesMap.put("Stip", Arrays.asList(stipMunicipalities));
    }

    @Override
    public boolean isValid(Apartment value, ConstraintValidatorContext context) {
        String city = value.getCity();
        String municipality = value.getMunicipality();
        return this.citiesMap.containsKey(city) && this.citiesMap.get(city).contains(municipality);
    }
}
