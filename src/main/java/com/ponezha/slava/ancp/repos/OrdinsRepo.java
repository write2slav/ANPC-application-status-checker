package com.ponezha.slava.ancp.repos;

import com.ponezha.slava.ancp.model.Ordin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdinsRepo extends CrudRepository<Ordin, Integer> {
    @Query(value = "SELECT * FROM ordins\n" +
            "INNER JOIN ordin_casenumbers ON ordins.id = ordin_casenumbers.ordin_id\n" +
            "WHERE casenumber = :number ", nativeQuery = true)
    List<Ordin> findByCaseNumber(@Param("number") String number);

    @Query(value = "select count (casenumber)\n" +
            "from ordin_casenumbers\n" +
            "where casenumber  like %:number% ", nativeQuery = true)
    String getCasesProcessedFor(@Param("number") String number);
}
