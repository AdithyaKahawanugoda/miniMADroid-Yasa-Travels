package com.example.yasatravels;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationDetailsActivityTest {

    private LocationDetailsActivity locationDetailsActivity;


    @Before
    public void setUp() throws Exception {
        locationDetailsActivity = new LocationDetailsActivity();


        }

        @Test
        public void calculatesumm_isCorrect(){

        int AdultTicketPrice = 50;
        int ChildTicketPrice = 20;
        int NoOfAdults = 10;
        int NoOfChildren = 5;

        int estimateVal = locationDetailsActivity.CalculatePrice(AdultTicketPrice,ChildTicketPrice,NoOfAdults , NoOfChildren);

            assertEquals(600, estimateVal,0.1); // check pass or fail
        }

        @Test
        public void calculatesumm1_isCorrect(){

            int AdultTicketPrice = 50;
            int ChildTicketPrice = 20;
            int NoOfAdults = 10;
            int NoOfChildren = 5;

            int estimateVal = locationDetailsActivity.CalculatePrice(AdultTicketPrice,ChildTicketPrice,NoOfAdults , NoOfChildren);

            assertEquals(800, estimateVal,0.1); // check pass or fail
        }


    @Test
    public void calculatesumm2_isCorrect(){

        int AdultTicketPrice = 100;
        int ChildTicketPrice = 50;
        int NoOfAdults = 10;
        int NoOfChildren = 5;

        int estimateVal = locationDetailsActivity.CalculatePrice(AdultTicketPrice,ChildTicketPrice,NoOfAdults , NoOfChildren);

        assertEquals(1250, estimateVal,0.1); // check pass or fail
    }
    }
