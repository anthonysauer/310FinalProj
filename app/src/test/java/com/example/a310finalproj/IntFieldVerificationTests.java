package com.example.a310finalproj;
import org.junit.Test;

import static org.junit.Assert.*;
public class IntFieldVerificationTests {
    @Test
    public void emptyInt(){
        assertEquals(false,fieldVerificationUtil.isValidIntEntry(""));
    }
    @Test
    public void nanInt(){
        assertEquals(false,fieldVerificationUtil.isValidIntEntry("test"));

    }
    @Test
    public void negativeInt(){
        assertEquals(false,fieldVerificationUtil.isValidIntEntry("-1"));
    }

    @Test
    public void validInt(){
        assertEquals(true,fieldVerificationUtil.isValidIntEntry("1"));

    }
}
