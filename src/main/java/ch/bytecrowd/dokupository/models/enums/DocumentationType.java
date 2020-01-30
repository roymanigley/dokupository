package ch.bytecrowd.dokupository.models.enums;

import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.models.jpa.WordTemplate;
import ch.bytecrowd.dokupository.utils.WordUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public enum DocumentationType {

    CHANGE_LOG((documentationNodes, wordTemplate, map, targetFile) -> {

        try {

            WordUtil.fillNodes(documentationNodes, wordTemplate, map, targetFile);
        } catch (IOException e) {
            throw e;
        }
    }),

    PROJECT_DOCUMENTATION( (documentationNodes, wordTemplate, map, targetFile) -> {}),
    SYSTEM_DOCUMENTATION( (documentationNodes, wordTemplate, map, targetFile) -> {}),
    TEST_DOCUMENTATION((documentationNodes, wordTemplate, map, targetFile) -> {}),
    USER_DOCUMENTATION((documentationNodes, wordTemplate, map, targetFile) -> {

        try {
            WordUtil.fillNodes(documentationNodes, wordTemplate, map, targetFile);
        } catch (IOException e) {
            throw e;
        }
    });

    private WordFiller wordFiller = null;

    DocumentationType(WordFiller wordFiller) {

        this.wordFiller = wordFiller;
    }

    public void generateDocumentation(List<DocumentationNode> documentationNodes, WordTemplate wordTemplate, Map<String, String> map, File targetFile) throws IOException {

        wordFiller.fillWord(documentationNodes, wordTemplate, map, targetFile);
    }

    @FunctionalInterface
    interface WordFiller {

        public void fillWord(List<DocumentationNode> documentationNodes, WordTemplate wordTemplate, Map<String, String> map, File targetFile) throws IOException;
    }
}
