package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntervalsAdjacencyDetectorTest {

    IntervalsAdjacencyDetector IAD;

    @Before
    public void setUp() throws Exception {
        IAD = new IntervalsAdjacencyDetector();
    }

    // interval1 is before interval2

    @Test
    public void isAdjacent_interval1BeforeInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(8, 12);
        boolean result = IAD.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 is contained within interval2

    @Test
    public void isAdjacent_interval1ContainedWithinInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-4, 12);
        boolean result = IAD.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }
    // interval1 contains interval2

    @Test
    public void isAdjacent_interval1ContainsInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(0, 3);
        boolean result = IAD.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }
    // interval1 is after interval2

    @Test
    public void isAdjacent_interval1AfterInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-10, -3);
        boolean result = IAD.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_interval1BeforeAdjacentInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(5, 8);
        boolean result = IAD.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }

    @Test
    public void isAdjacent_interval1AfterAdjacentInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-3, -1);
        boolean result = IAD.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }

    @Test
    public void isAdjacent_interval1AdjacentSaveInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-1, 5);
        boolean result = IAD.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }
}