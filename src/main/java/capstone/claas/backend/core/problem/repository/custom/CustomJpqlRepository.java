package capstone.claas.backend.core.problem.repository.custom;

import capstone.claas.backend.core.problem.Problem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomJpqlRepository {
    List<Problem> findAllList( );
}
