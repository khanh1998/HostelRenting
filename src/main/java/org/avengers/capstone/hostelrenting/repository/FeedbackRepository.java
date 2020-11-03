package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Feedback;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author duattt on 10/26/20
 * @created 26/10/2020 - 13:32
 * @project youthhostelapp
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByType_TypeIdAndIsDeletedIsFalseOrderByCreatedAtDesc(Integer typeId);
}
