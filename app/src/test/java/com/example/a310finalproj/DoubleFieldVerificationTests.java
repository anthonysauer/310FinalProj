package com.example.a310finalproj;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoubleFieldVerificationTests {
    @Test
    public void emptyDouble(){
        assertEquals(false, fieldVerificationUtil.isValidDoubleEntry(""));
    }

    @Test
    public void nanDouble(){
        assertEquals(false, fieldVerificationUtil.isValidDoubleEntry("test"));

    }

    @Test
    public void negativeDouble(){
        assertEquals(false, fieldVerificationUtil.isValidDoubleEntry("-1.0"));


    }

    @Test
    public void validDouble(){
        assertEquals(true, fieldVerificationUtil.isValidDoubleEntry("1.0"));

    }

}
