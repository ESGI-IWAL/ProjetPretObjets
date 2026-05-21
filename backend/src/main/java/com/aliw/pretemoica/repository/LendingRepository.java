package com.aliw.pretemoica.repository;

import com.aliw.pretemoica.entity.LendingEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LendingRepository extends JpaRepository<LendingEntity, Long> {

  @Query(
      """
			select case when count(l) > 0 then true else false end
			from LendingEntity l
			where l.object.id = :objectId
				and l.startedAt <= :endDate
				and (l.endedAt is null or l.endedAt >= :startDate)
			""")
  boolean existsOverlappingLending(
      @Param("objectId") Long objectId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}
