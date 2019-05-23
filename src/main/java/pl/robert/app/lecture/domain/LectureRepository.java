package pl.robert.app.lecture.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

interface LectureRepository extends JpaRepository<Lecture, Long> {

    Lecture findLectureById(long id);

    @Query(value = "SELECT lecture_id FROM users_lecture WHERE user_id = :userId", nativeQuery = true)
    List<Long> findAlreadySubscribedLecturesByUsername(@Param("userId") Long userId);
}
