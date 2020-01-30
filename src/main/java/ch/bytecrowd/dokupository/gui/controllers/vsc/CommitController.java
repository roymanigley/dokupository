package ch.bytecrowd.dokupository.gui.controllers.vsc;

import ch.bytecrowd.dokupository.DokuPositoryApplication;
import ch.bytecrowd.dokupository.gui.controllers.crud.edit.EditDocumentationViewController;
import ch.bytecrowd.dokupository.gui.utils.DialogUtil;
import ch.bytecrowd.dokupository.models.jpa.Application;
import ch.bytecrowd.dokupository.models.jpa.Commit;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import ch.bytecrowd.dokupository.models.vsc.CommitModelWrapper;
import ch.bytecrowd.dokupository.repositories.jpa.CommitRepository;
import ch.bytecrowd.dokupository.services.jpa.CommitCrudService;
import ch.bytecrowd.dokupository.utils.I18nUtil;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CommitController {

    private static final Logger LOG = LoggerFactory.getLogger(CommitController.class);

    private Set<CommitModelWrapper> commitModelWrappers = new HashSet<>();

    private CommitCrudService commitService;

    @Autowired
    private EditDocumentationViewController editDocumentationViewController;

    @Autowired
    public CommitController(CommitRepository commitService) {
        this.commitService = new CommitCrudService(commitService);
    }

    public void initCommits(Long selectedIdDocumentationNode) {

        List<HBox> commitsUnrelated = new ArrayList<>();
        List<HBox> commitsRelated = new ArrayList<>();

        initCommitList(selectedIdDocumentationNode, commitsUnrelated, commitsRelated);
        initCommitToggler(commitsRelated, commitsUnrelated);
    }

    private void initCommitList(Long selectedIdDocumentationNode, List<HBox> commitsUnrelated, List<HBox> commitsRelated) {
        try {
            //initCommitsByApplication();
            final List<Commit> commitsFromDbRelated = commitService.findAllByDocumentationNodeId(selectedIdDocumentationNode);
            final List<Commit> commitsFromDb = commitService.findAllByApplicationId(editDocumentationViewController.getSelectedApplication().getId());

            /*
             * @TODO PERFORMANCE!!!!!
             * */
            editDocumentationViewController.getCommitsList().getItems().clear();
            commitModelWrappers.forEach(commit -> {

                final AtomicBoolean related = new AtomicBoolean(false);
                final AtomicBoolean ignored = new AtomicBoolean(false);

                // all from application
                if (commitsFromDbRelated.stream().filter(c -> !c.getIgnoreForDocumentation() && c.getHash().equals(commit.getCommitHash())).findAny().isPresent()) {

                    // related and not ignored
                    final HBox commitHbox = commitToHBox(commit, selectedIdDocumentationNode,  true);
                    editDocumentationViewController.getCommitsList().getItems().add(commitHbox);
                    commitsRelated.add(commitHbox);

                } else if (commitsFromDb.stream().filter(c -> c.getHash().equals(commit.getCommitHash())).findAny().isEmpty()) {

                    // unrelated
                    final HBox commitHbox = commitToHBox(commit, selectedIdDocumentationNode,  false);
                    commitsUnrelated.add(commitHbox);
                }
            });

            if (LOG.isDebugEnabled())
                LOG.debug("commits initialized for application: " + editDocumentationViewController.getSelectedApplication().getNameDe());

        } catch (Exception e) {
            LOG.error("Fetching commits failed for application: " + editDocumentationViewController.getSelectedApplication().getNameDe(), e);
        }
    }

    private void initCommitToggler(List commitsRelated, List commitsUnrelated) {

        editDocumentationViewController.getCommitTypeToggler().setSelected(true);
        editDocumentationViewController.getCommitTypeToggler().setOnAction(event -> {
            if (!editDocumentationViewController.getCommitTypeToggler().isSelected()) {
                editDocumentationViewController.getCommitTypeToggler().setText(I18nUtil.getI18nString("label.commits.unrelated"));
                editDocumentationViewController.getCommitsList().getItems().setAll(commitsUnrelated);
            } else {
                editDocumentationViewController.getCommitTypeToggler().setText(I18nUtil.getI18nString("label.commits.related"));
                editDocumentationViewController.getCommitsList().getItems().setAll(commitsRelated);
            }
        });
    }

    private HBox commitToHBox(CommitModelWrapper commit, Long selectedIdDocumentationNode, boolean related) {


        final JFXButton ignoreCommitBtn = new JFXButton();
        ignoreCommitBtn.getStyleClass().add("button-ignore");
        ignoreCommitBtn.setTooltip(new Tooltip(I18nUtil.getI18nString("label.button.commit.ignore")));
        ignoreCommitBtn.setOnAction(event -> {

            LOG.debug("ignore commit: " + commit.getCommitHash());
            if (selectedIdDocumentationNode != null && selectedIdDocumentationNode != 0L) {
                saveCommit(commit, selectedIdDocumentationNode, true);
            } else {
                DialogUtil.showWarnDialog("label.save.before.relating.commits");
            }
        });

        final JFXButton addCommitBtn = new JFXButton();

        if (related) {
            addCommitBtn.getStyleClass().add("button-cancel");
            addCommitBtn.setTooltip(new Tooltip(I18nUtil.getI18nString("label.button.commit.unrelate")));

            addCommitBtn.setOnAction(event -> {

                LOG.debug("deleting commits Commit: " + commit.getCommitHash());
                if (selectedIdDocumentationNode != null && selectedIdDocumentationNode != 0L) {

                    DialogUtil.showDeleteDialog(selectedIdDocumentationNode, viewEntityDocumentationNode -> {

                        try {
                            final List<Commit> allByDocumentationNodeIdAndHash = commitService.findAllByDocumentationNodeIdAndHash(selectedIdDocumentationNode, commit.getCommitHash());
                            commitService.deleteRecords(allByDocumentationNodeIdAndHash);
                            initCommits(selectedIdDocumentationNode);
                        } catch (Exception e) {
                            DialogUtil.showErrorDialog(e);
                        }
                    });

                }
            });
        } else {
            addCommitBtn.getStyleClass().add("button-add");
            addCommitBtn.setTooltip(new Tooltip(I18nUtil.getI18nString("label.button.commit.relate")));

            addCommitBtn.setOnAction(event -> {

                LOG.debug("relating Commit: " + commit.getCommitHash());
                if (selectedIdDocumentationNode != null && selectedIdDocumentationNode != 0L) {
                    saveCommit(commit, selectedIdDocumentationNode, false);
                } else {
                    DialogUtil.showWarnDialog("label.save.before.relating.commits");
                }
            });
        }

        String commitText = commit.getCommitISODate();
        commitText += (" " + commit.getCommitCommiter());
        commitText += ("\n" + commit.getCommitMessage());

        final Hyperlink hyperlink = new Hyperlink(commit.getCommitHash());
        hyperlink.setOnAction(event -> {
            DokuPositoryApplication.getApplicationHostServices().showDocument(commit.getCommitUrl());
        });

        final Label message = new Label(commitText);
        message.setMaxWidth(240);
        message.setWrapText(true);

        final VBox vBoxCommit = new VBox(message, hyperlink);
        final VBox vBoxBtn = new VBox(addCommitBtn);
        if (!related)
            vBoxBtn.getChildren().add(ignoreCommitBtn);

        vBoxBtn.setSpacing(-10);

        return new HBox(vBoxBtn, vBoxCommit);
    }

    private void saveCommit(CommitModelWrapper commit, Long selectedIdDocumentationNode, boolean ignore) {

        final Commit jpaCommit = new Commit();
        jpaCommit.setApplication(editDocumentationViewController.getSelectedApplication());
        jpaCommit.setHash(commit.getCommitHash());
        jpaCommit.setIgnoreForDocumentation(ignore);
        DocumentationNode documentationNode = new DocumentationNode();
        editDocumentationViewController.getSelectedViewEnity().fillEntity(documentationNode);
        jpaCommit.setDocumentationNode(documentationNode);
        commitService.saveRecord(jpaCommit);
        editDocumentationViewController.getCommitTypeToggler().setText(I18nUtil.getI18nString("label.commits.related"));
        initCommits(selectedIdDocumentationNode);
    }


    public void initCommitsByApplication(Application application) throws Exception {
        if (LOG.isDebugEnabled())
            LOG.debug("Start fetching commits: " + editDocumentationViewController.getSelectedApplication().getRepositoryUrl());
        commitModelWrappers = application.getRepoType().fetchAllCommitsByApplication(application);

        if (LOG.isDebugEnabled())
            LOG.debug("End fetching commits, size: " + commitModelWrappers.size());
    }

    public Set<CommitModelWrapper> getCommitModelWrappers() {
        return commitModelWrappers;
    }

    public void setCommitModelWrappers(Set<CommitModelWrapper> commitModelWrappers) {
        this.commitModelWrappers = commitModelWrappers;
    }
}
