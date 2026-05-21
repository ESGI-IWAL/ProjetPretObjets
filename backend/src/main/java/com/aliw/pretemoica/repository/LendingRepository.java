package com.aliw.pretemoica.repository;

import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.LendingStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LendingRepository extends JpaRepository<LendingEntity, Long> {

  @Query("""
select distinct l
from LendingEntity l
join l.object o
join l.borrowedBy b
where (:objectName is null or lower(o.name) like lower(concat('%', :objectName, '%')))
and (:borrowerName is null or lower(b.username) like lower(concat('%', :borrowerName, '%')))
and (:startAt is null or l.startedAt >= :startAt)
and (:endAt is null or l.endedAt <= :endAt)
and (:status is null or l.status = :status)
order by l.startedAt desc
""")
  List<LendingEntity> search(
      @Param("objectName") String objectName,
      @Param("borrowerName") String borrowerName,
      @Param("startAt") LocalDateTime startAt,
      @Param("endAt") LocalDateTime endAt,
      @Param("status") LendingStatus status);
}
