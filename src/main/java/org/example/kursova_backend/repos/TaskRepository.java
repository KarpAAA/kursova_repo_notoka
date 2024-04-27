package org.example.kursova_backend.repos;

import org.example.kursova_backend.entities.Task;
import org.example.kursova_backend.entities.TasksList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

