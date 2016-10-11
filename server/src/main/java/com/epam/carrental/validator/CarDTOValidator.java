package com.epam.carrental.validator;

import com.epam.carrental.dto.CarDTO;
import org.springframework.stereotype.Component;

@Component
public class CarDTOValidator implements Validator<CarDTO>{

    @Override
    public boolean validate(CarDTO carDTO) {
        if(carDTO==null){
            return false;
        }
        String model=carDTO.getModel();
        String registrationNumber=carDTO.getRegistrationNumber();

        if(model==null||model.equals("")||registrationNumber==null||registrationNumber.equals("")){
           return false;
        }
        return true;
    }
}
