package com.example.yasatravels;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HotelDetailsActivityTest {
    private HotelDetailsActivity hotelDetailsActivity;

    @Before
    public void setUp() throws Exception {
        hotelDetailsActivity = new HotelDetailsActivity();
    }

    @Test
    public void calculateSum1_isCorrect() {

        int Login = 5000;
        int Breakfast = 1000;
        int Lunch = 2000;
        int Dinner = 1500;

        float estimationVal = hotelDetailsActivity.finalCalc(Login,Breakfast,Lunch,Dinner);
        assertEquals(9500.0,estimationVal,0.1);

    }

    @Test
    public void calculateSum2_isCorrect() {

        int Login = 39000;
        int Breakfast = 1000;
        int Lunch = 2000;
        int Dinner = 1500;

        float estimationVal = hotelDetailsActivity.finalCalc(Login,Breakfast,Lunch,Dinner);
        assertEquals(43499.0,estimationVal,0.1);

    }

    @Test
    public void calculateSum3_isCorrect() {

        int Login = 39000;
        int Breakfast = 1000;
        int Lunch = 2000;
        int Dinner = 1500;

        float estimationVal = hotelDetailsActivity.finalCalc(Login,Breakfast,Lunch,Dinner);
        assertEquals(43499.9,estimationVal,0.1);

    }

}