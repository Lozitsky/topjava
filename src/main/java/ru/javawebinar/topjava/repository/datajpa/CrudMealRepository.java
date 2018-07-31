package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

/*    @Transactional
    @Modifying
    @Query("UPDATE Meal m SET m.user.id=:userId")
    Meal save(@Param("meal") Meal meal, int userId);*/
//    <S extends Meal> S save(S entity, int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.user.id=:userId AND m.id=:id")
    int delete(@Param("id")int id,@Param("userId") int userId);

    @Modifying
    @Query("select m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC ")
    List<Meal> findAll(@Param("userId") int userId);

    @Modifying
    List<Meal> findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int id, LocalDateTime start, LocalDateTime end);

}
