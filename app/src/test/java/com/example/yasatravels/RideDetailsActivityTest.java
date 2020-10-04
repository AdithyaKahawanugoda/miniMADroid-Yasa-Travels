package com.example.yasatravels;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RideDetailsActivityTest {
    private RideDetailsActivity rideDetailsActivity;
    @Before
    public void setUp() throws Exception {

        rideDetailsActivity = new RideDetailsActivity();

    }

    @Test
    public void calculatetest1_isCorrect() {
        int Cost = 5000;
        int Distance = 75;
        int result = rideDetailsActivity.CalculatePrice(Cost,Distance);
        assertEquals(375000,result,0.1);
    }

    @Test
    public void calculatetest2_isCorrect() {
        int Cost = 5000;
        int Distance = 75;
        int result = rideDetailsActivity.CalculatePrice(Cost,Distance);
        assertEquals(374500.0,result,0.1);
    }

    @Test
    public void calculatetest3_isCorrect() {
        int Cost = 5000;
        int Distance = 75;
        int result = rideDetailsActivity.CalculatePrice(Cost,Distance);
        assertEquals(374999.9,result,0.1);
    }
}