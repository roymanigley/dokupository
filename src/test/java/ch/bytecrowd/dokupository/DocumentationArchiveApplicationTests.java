package ch.bytecrowd.dokupository;

import ch.bytecrowd.dokupository.repositories.jpa.DocumentationNodeRepository;
import ch.bytecrowd.dokupository.repositories.jpa.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentationArchiveApplicationTests {

    @Autowired
    private DocumentationNodeRepository documentationNodeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
/*
        final Commit commit = new Commit();
        commit.setHash("1111");

        final DocumentationNode documentationNode = new DocumentationNode();
        //documentationNode.setStyle("DocumentationNode 01");
        documentationNode.getCommits().add(commit);

        final DocumentationNode save = chapterRepository.save(documentationNode);

        final List<DocumentationNode> all = chapterRepository.findAll();
        assertThat(all, not(empty()));
        assertThat(all.get(0).getCommits(), not(empty()));
    }

	/*

	ENV:

	docker run -p 3000:3000 gogs/gogs (select SQL-Ligth)

    GET /repos/:username/:reponame/commits/:sha

	Changeserfasen

+--------------------------------------------------------------------------------------------------------------------+
|                                                                                                                    |
+--> ChangeLog Recorer <---------------------------------------------------------------------------------------------+
|                                                                                                                    |
+--------------------------------------------------------------------------------------------------------------------+
+----------------------------------------------+ +-----------------------------+ +---+   +-----------------+ +---+   |
|            Commits (undocumented)  | Refresh | | Applications              v | |add|   | Versions      v | |add|   |
+------------------------------------+---------+ +-----------------------------+ +---+   +-----------------+ +---+   |
| f33b4e121df57db3c865e0174a8440f3... as Link  | +---------------------------------------------------------+ +---+   |
| Author: Roy <roy.manigley@gmail.com>         | | Changes (Chapters)                                    v | |add|   |
| Date:   Sat Apr 6 15:26:51 2019 +0200        | +---------------------------------------------------------+ +---+   |
|                                              | +---------------------------------------------------------------+   |
| Message:                                     | | +------------------------------+ +--------------------------+ |   |
| Dependencies für ojdbc eingebunden           | | | Title:                       | | Parent (chapter):        | |   |
+------------------------------------+---------+ | +------------------------------+ +--------------------------+ |   |
|| add/remove (from chapter)         | ignore || | +------------------------------+ +--------------------------+ |   |
+------------------------------------+---------+ | | Order:                       | | Version End: (Version)   | |   |
|                                              | | +------------------------------+ +--------------------------+ |   |
|                                              | | +-----------------------------------------------------------+ |   |
|                                              | | | Text:                                                     | |   |
|                                              | | |                                                           | |   |
|                                              | | |                                                           | |   |
|                                              | | |                                                           | |   |
|                                              | | |                                                           | |   |
|                                              | | |                                                           | |   |
|                                              | | |                                                           | |   |
|                                              | | |                                                           | |   |
|                                              | | |                                                           | |   |
|                                              | | +-----------------------------------------------------------+ |   |
|                                              | | +---------------------------------------------------+         |   |
|                                              | | | Print screens:                           +------+ | +-----+ |   |
|                                              | | |                                          | edit | | | add | |   |
+----------------------------------------------+ | |                                          +------+ | +-----+ |   |
|             Commits (documented)             | | +---------------------------------------------------+         |   |
+----------------------------------------------+ +------------------------------------------------------+--------+   |
|              Commits (ignored)               | | save  |                                    | delete  | cancel |   |
+----------------------------------------------+-+-------+------------------------------------+---------+--------+---+




	 */
    }
}
