package org.example.kursova_backend.repos;

import org.example.kursova_backend.entities.Operation;
import org.example.kursova_backend.entities.TaskBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("SELECT o FROM Operation o WHERE o.task.id IN :taskIds")
    List<Operation> findByTask_IdIn(@Param("taskIds") List<Long> taskIds);
}
