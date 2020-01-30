package ch.bytecrowd.dokupository.services.jpa;

import ch.bytecrowd.dokupository.models.jpa.Commit;
import ch.bytecrowd.dokupository.repositories.jpa.CommitRepository;

import java.util.List;

public class CommitCrudService extends GenericCrudService<Commit> {

    private final CommitRepository commitRepository;

    public CommitCrudService(CommitRepository repository) {
        super(repository);
        this.commitRepository = repository;
    }

    public List<Commit> findAllByIgnoreForDocumentationIsTrue() {
        return commitRepository.findAllByIgnoreForDocumentationIsTrue();
    }

    public List<Commit> findAllByApplicationId(Long idApplication) {
        return commitRepository.findAllByApplicationId(idApplication);
    }

    public List<Commit> findAllByDocumentationNodeId(Long idDocumentationNode) {
        return commitRepository.findAllByDocumentationNodeId(idDocumentationNode);
    }

    public List<Commit> findAllByDocumentationNodeIdAndHash(Long idDocumentationNode, String hash) {
        return commitRepository.findAllByDocumentationNodeIdAndHash(idDocumentationNode, hash);
    }
}
