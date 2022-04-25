/*
 * Copyright (c) 2022. Falco Wolkorte
 */

package nl.bd.sdbackendopdracht.security.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidationTest {

    @Test
    void validate() {
        assertEquals(true, new EmailValidation().validate("falco23@wolkorte.com"));
        assertEquals(true, new EmailValidation().validate("falco@wolkorte.nl"));
        assertEquals(false, new EmailValidation().validate("falco@wolkorte."));
        assertEquals(true, new EmailValidation().validate("falco@wolkorte23.be"));
        assertEquals(true, new EmailValidation().validate("falco@wolkorte.com"));

    }
}