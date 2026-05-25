package com.aliw.pretemoica.repository;

import com.aliw.pretemoica.entity.LendingEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LendingRepository extends JpaRepository<LendingEntity, Long> {

  @Query(
      nativeQuery = true,
      value =
          """
select distinct l.id, l.borrowed_by_id, l.ended_at, l.object_id, l.offered_by_id, l.started_at, l.status
from lending l
join object o on o.id = l.object_id
join app_user b on b.id = l.borrowed_by_id
where (:objectName is null or lower(cast(o.name as varchar)) like lower(concat('%', cast(:objectName as varchar), '%')))
and (:borrowerName is null or lower(cast(b.username as varchar)) like lower(concat('%', cast(:borrowerName as varchar), '%')))
and (:startAt is null or l.started_at >= cast(:startAt as timestamp))
and (:endAt is null or l.ended_at <= cast(:endAt as timestamp))
and (:status is null or l.status = cast(:status as text))
order by l.started_at desc
""")
  List<LendingEntity> search(
      @Param("objectName") String objectName,
      @Param("borrowerName") String borrowerName,
      @Param("startAt") LocalDateTime startAt,
      @Param("endAt") LocalDateTime endAt,
      @Param("status") String status);

  @Query(
      """
select l from LendingEntity l
join l.object o
where o.id in :objectIds
order by o.id, l.startedAt desc
""")
  List<LendingEntity> findLendingsForObjects(@Param("objectIds") List<Long> objectIds);

  @Query("""
select l from LendingEntity l
join l.object o
order by o.id, l.startedAt desc
""")
  List<LendingEntity> findAllLendingsGroupedByObject();
}
