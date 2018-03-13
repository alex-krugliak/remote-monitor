package com.web.service.imp;


import com.web.entity.EventType;
import com.web.entity.Tag;
import com.web.service.*;
import com.web.wrapper.comport.ComPortDataWrapper;
import com.web.wrapper.comport.ComPortDataWriteWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created on 28.10.15.
 */
@Service
public class TagsServiceImpl implements TagsService {

    private static final Logger logger = Logger.getLogger(TagsServiceImpl.class);

    //speed will be calculated according to this interval value
    public static final int scheduledPeriod = 5000;

    private boolean isFirstStart = true;

    private AtomicBoolean isReadInProcess = new AtomicBoolean(false);

    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private ArchiveService archiveService;
    @Autowired
    private DataProvider dataProvider;
    @Autowired
    private UsersService usersService;


    @Scheduled(fixedRate = scheduledPeriod)
    private void fixedRateTask() {

        try {
            if (isReadInProcess.compareAndSet(false, true)) {
                saveCurrentTag();
                new Thread(() -> {
                    try {
                        Set<String> roles = new HashSet<>();
                        roles.add("ROLE_SERVER");
                        usersService.getAsUser("server", roles);

                        webSocketService.broadcastCurrentData(archiveService.getAllCurrentViewData());
                    } catch (Exception ex) {
                        logger.error("Websocket error - " + ex.getMessage());
                    }
                }).start();

            } else {
                logger.error("Preview process haven't been finished");
            }
        } catch (Exception ex) {
            logger.error("fixed rate task error - " + ex.getMessage());
        } finally {
            isReadInProcess.set(false);
        }
    }

    public void saveCurrentTag() {

        Tag formerTag = archiveService.getLastTag();
        Tag formerTagConnectionOK = archiveService.getLastTagConnectionOK();
        ComPortDataWriteWrapper dataWrite = new ComPortDataWriteWrapper();

        boolean isNewDay = !archiveService.isDayTheSame(formerTag);

        boolean isCountersMainModuleReseted = false;
        if (isNewDay) {
            archiveService.mainModuleCountersReset();
            isCountersMainModuleReseted = true;
        }

        ComPortDataWrapper comPortDataCurrent = dataProvider.loadTagsData(dataWrite);

        Tag tag = archiveService.saveNewTag(comPortDataCurrent);
        if (tag == null) {
            throw new RuntimeException("Error when create new Tag");
        }

        if (this.isFirstStart) {
            archiveService.saveEvent(EventType.COMP_TURN_ON.name(), tag);
        }

        archiveService.saveCommonEvents(tag, formerTag, isNewDay, isCountersMainModuleReseted);

        if (tag.getConnectionOk()) {
            archiveService.saveMainModuleEvents(tag, formerTagConnectionOK, isNewDay);
        }

        isFirstStart = false;


    }

}
