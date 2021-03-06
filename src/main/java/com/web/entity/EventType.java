package com.web.entity;

/**
 * Created on 20.06.16.
 */
public enum EventType {
    LINE1_ON, LINE1_OFF, MATERIAL_ON_LINE1, MATERIAL_OFF_LINE1,
    LINE2_ON, LINE2_OFF, MATERIAL_ON_LINE2, MATERIAL_OFF_LINE2,
    COMP_TURN_ON, CONNECTION_DOWN, CONNECTION_UP, POWER_FAIL, POWER_ON,
    COUNTER_LINE1_WAS_RESET_NEW_DAY, COUNTER_LINE2_WAS_RESET_NEW_DAY,
    NEW_DAY
}
