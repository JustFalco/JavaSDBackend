package nl.bd.sdbackendopdracht.security.validation;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AllArgsConstructor
class NumValidationTest {

    @Test
    void testValidateId() {
        assertTrue((new NumValidation()).validateId(123L));
        assertFalse((new NumValidation()).validateId(-1L));
        assertFalse((new NumValidation()).validateId(Long.MAX_VALUE));
    }


    @Test
    void testValidateNumber() {
        assertFalse((new NumValidation()).validateNumber(10, 1, 3));
        assertFalse((new NumValidation()).validateNumber(Integer.MAX_VALUE, 1, 1));
        assertFalse((new NumValidation()).validateNumber(0, 1, 1));
        assertTrue((new NumValidation()).validateNumber(3, 1, 10));
        assertFalse((new NumValidation()).validateNumber(10, Integer.MAX_VALUE, 3));
    }


    @Test
    void testValidateFloat() {
        assertTrue((new NumValidation()).validateFloat(6.8f, 1.0f, 20.0f));
        assertFalse((new NumValidation()).validateFloat(Float.MAX_VALUE, 10.0f, 10.0f));
        assertFalse((new NumValidation()).validateFloat(Float.MIN_VALUE, 10.0f, 10.0f));
        assertFalse((new NumValidation()).validateFloat(0.5f, 10.0f, 10.0f));
        assertFalse((new NumValidation()).validateFloat(10.0f, 10.0f, Float.MIN_VALUE));
    }

}