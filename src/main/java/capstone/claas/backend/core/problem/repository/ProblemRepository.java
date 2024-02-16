package capstone.claas.backend.core.problem.repository;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.repository.custom.CustomJpqlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProblemRepository extends JpaRepository<UUID, Problem>, CustomJpqlRepository {
}
