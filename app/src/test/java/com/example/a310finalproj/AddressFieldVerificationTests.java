package com.example.a310finalproj;

import org.junit.Test;

import static org.junit.Assert.*;

public class AddressFieldVerificationTests {
    @Test
    public void emptyAddress(){
        assertEquals(false, fieldVerificationUtil.isValidAddress(""));
    }

    @Test
    public void invalidAddress(){
        assertEquals(false, fieldVerificationUtil.isValidAddress("1234"));
        assertEquals(false, fieldVerificationUtil.isValidAddress("test test"));
        assertEquals(false, fieldVerificationUtil.isValidAddress("1234 1234"));
        assertEquals(false, fieldVerificationUtil.isValidAddress("test"));
    }

    @Test
    public void validAddress(){
        assertEquals(true, fieldVerificationUtil.isValidAddress("1234 Street"));

    }

}
