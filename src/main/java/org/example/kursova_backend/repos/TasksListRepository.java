package org.example.kursova_backend.repos;

import org.example.kursova_backend.entities.TaskBoard;
import org.example.kursova_backend.entities.TasksList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksListRepository extends JpaRepository<TasksList, Long> {
}

