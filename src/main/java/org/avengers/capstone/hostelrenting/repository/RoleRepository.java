package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author duattt on 9/28/20
 * @created 28/09/2020 - 16:26
 * @project youthhostelapp
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
