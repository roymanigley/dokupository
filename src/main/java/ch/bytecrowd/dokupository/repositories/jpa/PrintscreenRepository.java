package ch.bytecrowd.dokupository.repositories.jpa;

import ch.bytecrowd.dokupository.models.jpa.PrintScreen;

import java.util.List;

public interface PrintscreenRepository extends Repository<PrintScreen> {

    public List<PrintScreen> findAllByDocumentationNodeId(Long idDocumentationNode);
}
