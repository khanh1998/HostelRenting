package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.GroupSchedule;

/**
 * @author duattt on 22/12/2020
 * @created 22/12/2020 - 15:23
 * @project youthhostelapp
 */
public interface GroupScheduleService {
    void checkExist(Integer id);
    GroupSchedule findById(Integer id);
    void delete(Integer id);
}
