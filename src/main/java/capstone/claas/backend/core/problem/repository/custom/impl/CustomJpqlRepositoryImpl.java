package capstone.claas.backend.core.problem.repository.custom.impl;

import capstone.claas.backend.core.problem.Problem;
import capstone.claas.backend.core.problem.repository.custom.CustomJpqlRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class CustomJpqlRepositoryImpl implements CustomJpqlRepository {
    private final EntityManager em;
    private final boolean isDeleted = false;

    @Override
    public List<Problem> findAllList() {
        return em.createQuery(
                "SELECT P FROM Problem P WHERE P.isDeleted = :isDeleted", Problem.class
        )
                .setParameter("isDeleted", isDeleted)
                .getResultList();
    }
}
