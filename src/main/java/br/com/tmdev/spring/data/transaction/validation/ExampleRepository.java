package br.com.tmdev.spring.data.transaction.validation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleEntity, Integer> {

    @Modifying
    @Query("UPDATE ExampleEntity SET usedBy = :usedBy, inUse = 1 WHERE id =:id AND inUse = 0")
    int updateUsedBy(String usedBy, Integer id);

}
