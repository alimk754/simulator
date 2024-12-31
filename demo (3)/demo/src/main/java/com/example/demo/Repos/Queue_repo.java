package com.example.demo.Repos;

import com.example.demo.classes.Queueing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Queue_repo extends JpaRepository<Queueing,Integer> {
}
