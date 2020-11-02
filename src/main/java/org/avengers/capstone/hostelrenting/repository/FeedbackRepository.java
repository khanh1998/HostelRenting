package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author duattt on 10/26/20
 * @created 26/10/2020 - 13:32
 * @project youthhostelapp
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Collection<Feedback> findByType_TypeIdAndIsDeletedIsFalseOrderByRatingDesc(Integer typeId);
}
