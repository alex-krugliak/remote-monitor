package com.web.service;

import com.web.entity.Event;
import com.web.entity.Tag;
import com.web.wrapper.comport.ComPortDataWrapper;
import com.web.wrapper.response.DataCurrentWrapper;
import com.web.wrapper.response.StatisticWrapper;

import java.util.List;

/**
 * Created on 21.06.16.
 */
public interface ArchiveService {
    DataCurrentWrapper getAllCurrentViewData();

    Tag saveNewTag(ComPortDataWrapper comPortDataWrapper);

    void saveMainModuleEvents(Tag tag, Tag formerTagConnectionOK, boolean isNewDay);

    void saveCommonEvents(Tag tag, Tag formerTag, boolean isNewDay, boolean isCountersMainModuleReseted);

    Event saveEvent(String description, Tag tag);

    List<StatisticWrapper> getStatistic(String wallpaperLineName, Long dateFrom, Long dateTo);

    boolean isDayTheSame(Tag lastTag);

    Tag getLastTag();

    Tag getLastTagConnectionOK();

    void mainModuleCountersReset();
}
