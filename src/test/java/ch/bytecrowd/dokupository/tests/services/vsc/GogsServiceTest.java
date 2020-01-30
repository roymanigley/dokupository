package ch.bytecrowd.dokupository.tests.services.vsc;

import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.enums.RepoType;
import ch.bytecrowd.dokupository.models.vsc.CommitModelWrapper;
import ch.bytecrowd.dokupository.models.vsc.GogsCommitModelWrapper;
import ch.bytecrowd.dokupository.services.vsc.GogsCommitService;
import ch.bytecrowd.dokupository.utils.JsonUtil;
import com.google.gson.GsonBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Application.class, GogsCommitService.class, RepoType.class, JsonUtil.class})
public class GogsServiceTest {

    private static Logger LOG = LoggerFactory.getLogger(GogsServiceTest.class);

    private final Application APP;

    public GogsServiceTest() {

        this.APP = new Application();
        APP.setRepositoryUrl("http://localhost:3000/api/v1/repos/roy/dummy-repo/");
        APP.setBranch("master");
        APP.setToken("c7dd2741406149f7bbdeed36fc9717ba314178f6");
    }

    /**
     * run docker gogs instance:
     * docker run -p 3000:3000 gogs/gogs
     */
    @Test
    @Ignore
    public void pocGSONTest() {

        try {

            final Set<CommitModelWrapper> commits = RepoType.GOGS.fetchAllCommitsByApplication(APP);

            commits.forEach(wrapper -> {
                System.out.println("******** COMMIT ********");
                System.out.println(wrapper.getCommitCommiter());
                System.out.println(wrapper.getCommitISODate());
                System.out.println(wrapper.getCommitHash());
                System.out.println(wrapper.getCommitMessage());
                System.out.println(wrapper.getCommitUrl());
                System.out.println("************************");
            });


            assertThat(commits, not(empty()));
            assertThat(commits.size(), is(7));
        } catch (Exception e) {
            LOG.error("RepoType.GOGS.fetchAllCommitsByApplication failed", e);
            fail(e.getMessage());
        }
    }


    @Test
    public void fetchCommitsTest() {

        final HashMap<String, String> urlToJsonFilesMap = new HashMap<>();
        urlToJsonFilesMap.put("http://localhost:3000/api/v1/repos/roy/dummy-repo/commits/master?token=c7dd2741406149f7bbdeed36fc9717ba314178f6", "json/gogs/gogs-dummy-repo_01.json");
        urlToJsonFilesMap.put("http://localhost:3000/api/v1/repos/roy/dummy-repo/commits/bcce4ab4fc3fc971d873ff8a5e61a331dd16ad63?token=c7dd2741406149f7bbdeed36fc9717ba314178f6", "json/gogs/gogs-dummy-repo_02.json");
        urlToJsonFilesMap.put("http://localhost:3000/api/v1/repos/roy/dummy-repo/commits/5b026a43cbd2309890c3483cfad68fda1e95926d?token=c7dd2741406149f7bbdeed36fc9717ba314178f6", "json/gogs/gogs-dummy-repo_03.json");
        urlToJsonFilesMap.put("http://localhost:3000/api/v1/repos/roy/dummy-repo/commits/93570bd3c764102e524be5d688a48b03bfff988b?token=c7dd2741406149f7bbdeed36fc9717ba314178f6", "json/gogs/gogs-dummy-repo_04.json");
        urlToJsonFilesMap.put("http://localhost:3000/api/v1/repos/roy/dummy-repo/commits/464dca0fa3641f6ff4a40c649ce7dcd9e44d900b?token=c7dd2741406149f7bbdeed36fc9717ba314178f6", "json/gogs/gogs-dummy-repo_05.json");
        urlToJsonFilesMap.put("http://localhost:3000/api/v1/repos/roy/dummy-repo/commits/9d69872a7344211771e9c217ca3e229d2a6dbac4?token=c7dd2741406149f7bbdeed36fc9717ba314178f6", "json/gogs/gogs-dummy-repo_06.json");
        urlToJsonFilesMap.put("http://localhost:3000/api/v1/repos/roy/dummy-repo/commits/e1d274a6f0118403ed43ec75fd7213693c7ecb79?token=c7dd2741406149f7bbdeed36fc9717ba314178f6", "json/gogs/gogs-dummy-repo_07.json");


        PowerMockito.mockStatic(JsonUtil.class);
        Mockito.when(JsonUtil.getFromJsonUrl(Mockito.any(), Mockito.any())).thenAnswer(invocationOnMock -> {

            final Object url = invocationOnMock.getArguments()[0];
            final String jsonFile = urlToJsonFilesMap.get(url);
            final InputStream resourceAsStream = GogsServiceTest.class.getClassLoader().getResourceAsStream(jsonFile);
            final InputStreamReader streamReader = new InputStreamReader(resourceAsStream);
            return new GsonBuilder().create().fromJson(streamReader, GogsCommitModelWrapper.GogsCommit.class);
        });

        final Application app = new Application();
        app.setRepositoryUrl("http://localhost:3000/api/v1/repos/roy/dummy-repo/");
        app.setBranch("master");
        app.setToken("c7dd2741406149f7bbdeed36fc9717ba314178f6");

        try {
            Set<CommitModelWrapper> commits = RepoType.GOGS.fetchAllCommitsByApplication(app);

            commits.forEach(wrapper -> {
                System.out.println("******** COMMIT ********");
                System.out.println(wrapper.getCommitCommiter());
                System.out.println(wrapper.getCommitISODate());
                System.out.println(wrapper.getCommitHash());
                System.out.println(wrapper.getCommitMessage());
                System.out.println(wrapper.getCommitUrl());
                System.out.println("************************");
            });

            assertThat(commits, not(empty()));
            assertThat(commits.size(), is(7));
        } catch (Exception e) {
            LOG.error("RepoType.GOGS.fetchAllCommitsByApplication failed", e);
            fail(e.getMessage());
        }
    }

}
