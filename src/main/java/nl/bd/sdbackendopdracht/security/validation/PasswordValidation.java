package nl.bd.sdbackendopdracht.security.validation;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.security.exeptions.IllegalEmailExeption;
import nl.bd.sdbackendopdracht.security.exeptions.IllegalPasswordExeption;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PasswordValidation {

    public Boolean validate(String password) {
        /**
         * https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
         *     Password must contain at least one digit [0-9].
         *     Password must contain at least one lowercase Latin character [a-z].
         *     Password must contain at least one uppercase Latin character [A-Z].
         *     Password must contain at least one special character like ! @ # & ( ).
         *     Password must contain a length of at least 8 characters and a maximum of 20 characters.
         */
        String emailRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$" ;
        if (!password.matches(emailRegex)) {
            throw new IllegalPasswordExeption("Password must be between 8 and 20 characters, and must contain at least: one lowercase letter, one uppercase letter, one special character and one digit");
        }
        return true;
    }
}
