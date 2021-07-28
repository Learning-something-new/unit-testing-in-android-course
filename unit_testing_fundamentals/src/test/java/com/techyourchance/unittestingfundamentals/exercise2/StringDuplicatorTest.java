package com.techyourchance.unittestingfundamentals.exercise2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringDuplicatorTest {

    private StringDuplicator SDT;
    
    @Before
    public void setUp() throws Exception {
        SDT = new StringDuplicator();
    }

    @Test
    public void duplicate_emptyString_emptyStringReturned() {
        String result = SDT.duplicate("");
        assertEquals("", result);
    }

    @Test
    public void duplicate_singleCharacter_twoSameCharacterReturned() {
        String result = SDT.duplicate("g");
        assertEquals("gg", result);
    }

    @Test
    public void duplicate_longString_duplicatedLongStringReturned() {
        String result = SDT.duplicate("Gl Hf");
        assertEquals("Gl HfGl Hf", result);
    }
}