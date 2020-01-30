package ch.bytecrowd.dokupository.services.jpa;

import ch.bytecrowd.dokupository.models.jpa.PrintScreen;
import ch.bytecrowd.dokupository.repositories.jpa.PrintscreenRepository;

import java.util.List;

public class PrintscreenCrudService extends GenericCrudService<PrintScreen> {

    private final PrintscreenRepository printscreenRepository;

    public PrintscreenCrudService(PrintscreenRepository repository) {
        super(repository);
        this.printscreenRepository = repository;
    }

    public List<PrintScreen> findAllByDocumentationNodeId(Long idDocumentationNode) {
        return printscreenRepository.findAllByDocumentationNodeId(idDocumentationNode);
    }
}
