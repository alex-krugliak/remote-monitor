package com.web.service;

import com.web.wrapper.comport.ComPortDataWrapper;
import com.web.wrapper.comport.ComPortDataWriteWrapper;

/**
 * Created on 8/4/16.
 */
public interface DataProvider {

    ComPortDataWrapper loadTagsData(ComPortDataWriteWrapper dataWrite);

}
