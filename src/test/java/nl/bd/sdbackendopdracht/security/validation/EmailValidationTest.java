/*
 * Copyright (c) 2022. Falco Wolkorte
 */

package nl.bd.sdbackendopdracht.security.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.bd.sdbackendopdracht.security.exeptions.IllegalEmailExeption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmailValidation.class})
@ExtendWith(SpringExtension.class)
class EmailValidationTest {
    @Autowired
    private EmailValidation emailValidation;

    /**
     * Method under test: {@link EmailValidation#validate(String, String)}
     */
    @Test
    void testValidate() {
        assertTrue(this.emailValidation.validate("jane.doe@example.org"));
        assertTrue(this.emailValidation.validate("l.l.l@lll.lll.lll.lll"));
        assertThrows(IllegalEmailExeption.class, () -> this.emailValidation.validate(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21"
                        + "\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0"
                        + "-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]"
                        + "|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53"
                        + "-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"));
    }
}

