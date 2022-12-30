/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.repository;

import backend.resumerryv2.mentor.domain.ClassWeekSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassWeekScheduleRepository extends JpaRepository<ClassWeekSchedule, Long> {
  @Override
  <S extends ClassWeekSchedule> S save(S entity);

  Optional<ClassWeekSchedule> findAllByMentorClassId(Long id);
}
