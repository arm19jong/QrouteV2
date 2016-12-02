package com.teamsmokeweed.qroute;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.*;

/**
 * Created by jongzazaal on 26/11/2559.
 */
public class ContentTest {

    @Test
    public void addition_isCorrect() throws Exception {
//                                         EndTime             CurrentTime         y M d H m
        assertEquals(Content.checkTime("2016-11-26 11:29", "2016-11-26 9:29"),    "0 0 0 2 0");
        assertEquals(Content.checkTime("2016-11-26 11:29", "2016-11-26 9:30"),    "0 0 0 1 0");
        assertEquals(Content.checkTime("2016-11-26 11:29", "2016-11-26 11:00"),   "0 0 0 0 29");
        assertEquals(Content.checkTime("2016-11-26 11:29", "2016-11-26 12:29"), "TimeOut");
    }
}