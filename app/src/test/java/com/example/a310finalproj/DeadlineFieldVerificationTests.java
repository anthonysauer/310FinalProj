package com.example.a310finalproj;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeadlineFieldVerificationTests {
    @Test
    public void emptyDeadline(){
        assertEquals(false, fieldVerificationUtil.isValidDeadline(""));
    }
    @Test
    public void invalidDeadlineFormat(){
        assertEquals(false, fieldVerificationUtil.isValidDeadline("test"));
        assertEquals(false, fieldVerificationUtil.isValidDeadline("2023/09/09"));
        assertEquals(false, fieldVerificationUtil.isValidDeadline("2023/09/9"));
        assertEquals(false, fieldVerificationUtil.isValidDeadline("2023/9/09"));
        assertEquals(false, fieldVerificationUtil.isValidDeadline("2023/aa/09"));
        assertEquals(false, fieldVerificationUtil.isValidDeadline("2023/09/aa"));
        assertEquals(false, fieldVerificationUtil.isValidDeadline("aaaa/09/09"));
        assertEquals(false, fieldVerificationUtil.isValidDeadline("2023/9/09"));
        assertEquals(false, fieldVerificationUtil.isValidDeadline("t/t/t"));

    }
    @Test
    public void deadlineWithinYear(){
        assertEquals(false, fieldVerificationUtil.isValidDeadline("08/08/2024"));

    }
    @Test
    public void deadlineNotPassed(){
        assertEquals(false, fieldVerificationUtil.isValidDeadline("01/01/2022"));


    }
    @Test
    public void validDeadline(){
        assertEquals(true, fieldVerificationUtil.isValidDeadline("01/01/2023"));

    }

}
