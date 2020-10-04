

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
        public void calculatesumm_isCorrect(){    // Done by Priyasarani W.A.L - IT19802022

        int AdultTicketPrice = 50;
        int ChildTicketPrice = 20;
        int NoOfAdults = 10;
        int NoOfChildren = 5;

        int estimateVal = locationDetailsActivity.CalculatePrice(AdultTicketPrice,ChildTicketPrice,NoOfAdults , NoOfChildren);

            assertEquals(600, estimateVal,0.1); // check pass or fail
        }

        @Test
        public void calculatesumm1_isCorrect(){    // Done by Priyasarani W.A.L - IT19802022

            int AdultTicketPrice = 50;
            int ChildTicketPrice = 20;
            int NoOfAdults = 10;
            int NoOfChildren = 5;

            int estimateVal = locationDetailsActivity.CalculatePrice(AdultTicketPrice,ChildTicketPrice,NoOfAdults , NoOfChildren);

            assertEquals(800, estimateVal,0.1); // check pass or fail
        }


    @Test
    public void calculatesumm2_isCorrect(){      // Done by Priyasarani W.A.L - IT19802022

        int AdultTicketPrice = 100;
        int ChildTicketPrice = 50;
        int NoOfAdults = 10;
        int NoOfChildren = 5;

        int estimateVal = locationDetailsActivity.CalculatePrice(AdultTicketPrice,ChildTicketPrice,NoOfAdults , NoOfChildren);

        assertEquals(1250, estimateVal,0.1); // check pass or fail
    }





    //Test Case4
    @Test
    public void calculatesumm3_isCorrect(){      //This test case done by Yasasmi-IT19025704

        int AdultTicketPrice = 200;
        int ChildTicketPrice = 100;
        int NoOfAdults = 2;
        int NoOfChildren = 2;

        int estimateVal = locationDetailsActivity.CalculatePrice(AdultTicketPrice,ChildTicketPrice,NoOfAdults , NoOfChildren);

        assertEquals(600, estimateVal,0.1); // check pass or fail
    }


    }
