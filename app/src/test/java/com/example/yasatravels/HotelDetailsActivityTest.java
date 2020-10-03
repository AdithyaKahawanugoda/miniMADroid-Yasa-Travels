//package com.example.yasatravels;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class HotelDetailsActivityTest {
//    private HotelDetailsActivity hotelDetailsActivity;
//    @Before
//    public void setUp() throws Exception {
//        hotelDetailsActivity = new HotelDetailsActivity();
//    }
//
//    @Test
//    public void calculateSum_isCorrect() {
//        int heads = 3;
//        int Doublebed = 10000;
//        int Singlebed = 5000;
//        int Breakfast = 1000;
//        int Lunch = 2000;
//        int Dinner = 1500;
//
//        float estimationVal = hotelDetailsActivity.calculateSum(heads,Doublebed,Singlebed,Breakfast,Lunch,Dinner);
//        assertEquals(28500.0,estimationVal,0.1);
//
//    }
//
//}