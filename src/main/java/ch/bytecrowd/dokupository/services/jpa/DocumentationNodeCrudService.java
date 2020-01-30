package ch.bytecrowd.dokupository.services.jpa;

import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.repositories.jpa.DocumentationNodeRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public class DocumentationNodeCrudService extends GenericCrudService<DocumentationNode> {

    private final DocumentationNodeRepository documentationNodeRepository;

    public DocumentationNodeCrudService(DocumentationNodeRepository repository) {
        super(repository);
        this.documentationNodeRepository = repository;
    }

    public List<DocumentationNode> findAllByVersionBeginApplicationIdAnAndDocumentationType(Long appId, DocumentationType documentationType) {
        return documentationNodeRepository.findAllByVersionBeginApplicationIdAnAndDocumentationType(appId, documentationType);
    }

    public List<DocumentationNode> findAllByVersionBeginApplicationIdAnAndDocumentationTypeAndVersionSorts(Long appId, DocumentationType documentationType, Integer sortierungMinVersion, Integer sortierungMaxVersion) {
        return documentationNodeRepository.findAllByVersionBeginApplicationIdAnAndDocumentationTypeAndVersionSorts(appId, documentationType, sortierungMinVersion, sortierungMaxVersion);
    }

    public Integer findMaxSort(Long applicationId, DocumentationType documentationType) {
        return documentationNodeRepository.findMaxSort(applicationId, documentationType);
    }

}
