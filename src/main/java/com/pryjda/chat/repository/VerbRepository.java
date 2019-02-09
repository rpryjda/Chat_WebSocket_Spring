package com.pryjda.chat.repository;

import com.pryjda.chat.entity.Verb;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerbRepository extends JpaRepository<Verb, Long> {

    List<Verb> findAllBy(Pageable pageable);

    Integer countAllBy();
}
