package ch.bytecrowd.dokupository.repositories.jpa;

import ch.bytecrowd.dokupository.models.jpa.Commit;

import java.util.List;

public interface CommitRepository extends Repository<Commit> {

    public List<Commit> findAllByApplicationId(Long idApplication);

    public List<Commit> findAllByDocumentationNodeId(Long idDocumentationNode);
    public List<Commit> findAllByDocumentationNodeIdAndHash(Long idDocumentationNode, String hash);
    public List<Commit> findAllByIgnoreForDocumentationIsTrue();
}
