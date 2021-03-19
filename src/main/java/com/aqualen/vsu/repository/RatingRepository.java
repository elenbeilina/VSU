package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.enums.UserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingByTechnology, RatingByTechnology.Key> {
    List<RatingByTechnology> findByKeyTechnologyAndUserRoleOrderByRating(TechnologyName technologyName, UserRole userRole, Pageable pageable);

    boolean existsByKeyTechnologyAndUser(TechnologyName technology, User user);
}
