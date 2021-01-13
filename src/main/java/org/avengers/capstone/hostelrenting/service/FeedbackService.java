package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.feedback.FeedbackDTOUpdate;
import org.avengers.capstone.hostelrenting.model.Feedback;

import java.util.Collection;

/**
 * @author duattt on 10/26/20
 * @created 26/10/2020 - 13:32
 * @project youthhostelapp
 */

public interface FeedbackService {
    void checkExist(Integer id);
    Feedback findById(Integer feedbackId);
    Feedback create(Feedback reqModel);
    Feedback update(Feedback exModel, FeedbackDTOUpdate reqDTO);
    void deleteById(Integer feedbackId);
    Collection<Feedback> findByTypeId(Integer typeId, String orderBy, int page, int size, boolean asc);

    int countFeedbackByTypeId(Integer typeId);
}
