package com.example.a310finalproj;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailFieldVerificationTests {
    @Test
    public void emptyEmail(){
        assertEquals(false, fieldVerificationUtil.isValidEmail(""));
    }
    @Test
    public void emailBadDomain(){
        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.ed"));
        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.de"));

        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.du"));
        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.ud"));

        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.eu"));
        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.ue"));

        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.deu"));
        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.due"));
        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.eud"));
        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.ued"));
        assertEquals(false, fieldVerificationUtil.isValidEmail("test@test.ude"));

    }
    @Test
    public void invalidEmail(){
        assertEquals(false, fieldVerificationUtil.isValidEmail("test@."));
        assertEquals(false, fieldVerificationUtil.isValidEmail("@test.test"));
        assertEquals(false, fieldVerificationUtil.isValidEmail("@."));
        assertEquals(false, fieldVerificationUtil.isValidEmail("test"));


    }
    @Test
    public void validEmail(){
        assertEquals(true, fieldVerificationUtil.isValidEmail("test@test.edu"));
    }

}
