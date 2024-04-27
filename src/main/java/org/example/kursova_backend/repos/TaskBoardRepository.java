package org.example.kursova_backend.repos;

import org.example.kursova_backend.entities.TaskBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskBoardRepository extends JpaRepository<TaskBoard, Long> {

}
