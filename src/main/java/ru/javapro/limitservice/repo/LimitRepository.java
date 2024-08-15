package ru.javapro.limitservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javapro.limitservice.entity.Limit;

import java.util.Optional;

public interface LimitRepository extends JpaRepository<Limit, Long> {
    Optional<Limit> findByUserId(long userId);

    @Modifying
    @Transactional
    @Query(value = "update limits set limit_sum = :limit_sum", nativeQuery = true)
    void setDayLimit(@Param("limit_sum") double limitSum);

}
