package nl.bd.sdbackendopdracht.security.validation;

import org.springframework.stereotype.Service;

@Service
public class LongValidation {
    public Boolean checkForLong(Long numToBeChecked){
        //check if empty
        if(numToBeChecked != null | numToBeChecked != 0){
            //check if long
            return true;
        }
        return false;
    }
}
