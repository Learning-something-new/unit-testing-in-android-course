package com.techyourchance.unittestingfundamentals.exercise1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NegativeNumberValidatorTest {

    private NegativeNumberValidator NNV;

    @Before
    public void setUp() {
        NNV = new NegativeNumberValidator();
    }

    @Test
    public void isNegativeNumberValidTest() {
        boolean result = NNV.isNegative(-1);
        Assert.assertTrue(result);
    }

    @Test
    public void isPositiveNumberValidTest() {
        boolean result = NNV.isNegative(1);
        Assert.assertFalse(result);
    }

    @Test
    public void isNeutralNumberValidTest() {
        boolean result = NNV.isNegative(0);
        Assert.assertFalse(result);
    }
}