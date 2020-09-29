package com.ponezha.slava.ancp.repos;

import com.ponezha.slava.ancp.model.Ordin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdinsRepo extends CrudRepository<Ordin, Integer> {

    List<Ordin> findByUrlLike(String url);

    List<Ordin> findByCaseNumbersLike(String caseNumber);

    @Query(value = "SELECT * FROM ordins\n" +
            "INNER JOIN ordin_casenumbers ON ordins.id = ordin_casenumbers.ordin_id\n" +
            "WHERE casenumbers = :name ", nativeQuery = true)
    List<Ordin> findByCaseNumbersOrMe(@Param("name") String caseNumber);


}
