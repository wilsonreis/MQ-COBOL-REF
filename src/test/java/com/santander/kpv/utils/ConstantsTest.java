package com.santander.kpv.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantsTest {

    @Test
    void testConstants() {
        assertEquals(1000L, Constants.SECOND);
        assertEquals(Constants.MINUTE, 60 * Constants.SECOND);
        assertEquals(Constants.HOUR, 60 * Constants.MINUTE);
        assertEquals("appdatatype", Constants.DATATYPE);
        assertEquals("AMQ.", Constants.TEMPQUEUEPREFIX);
    }

    @Test
    void testDataTypesEnum() {
        assertEquals(10, Constants.DataTypes.OURDATATYPE.getValue());
        assertEquals(20, Constants.DataTypes.OUROTHERDATATYPE.getValue());
    }
}
