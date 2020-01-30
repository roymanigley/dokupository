package ch.bytecrowd.dokupository.repositories.jpa;

import ch.bytecrowd.dokupository.models.enums.DocumentationType;
import ch.bytecrowd.dokupository.models.jpa.DocumentationNode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentationNodeRepository extends Repository<DocumentationNode> {

    @Query("SELECT DISTINCT d FROM DocumentationNode d" +
            " JOIN FETCH d.versionBegin vb" +
            " LEFT JOIN FETCH d.printscreens p" +
            " JOIN FETCH vb.application app" +
            " WHERE app.id = :idApplication AND d.documentationType = :documentationType" +
            " ORDER BY d.sortierung")
    public List<DocumentationNode> findAllByVersionBeginApplicationIdAnAndDocumentationType(@Param("idApplication") Long applicationId, @Param("documentationType") DocumentationType documentationType);


    @Query("SELECT DISTINCT d FROM DocumentationNode d" +
            " JOIN FETCH d.versionBegin vb" +
            " LEFT JOIN FETCH d.printscreens p" +
            " LEFT JOIN FETCH d.versionEnd ve" +
            " JOIN FETCH vb.application app" +
            " WHERE app.id = :idApplication AND d.documentationType = :documentationType" +
            " AND vb.sortierung <= :sortierungMaxVersion" +
            " AND vb.sortierung > :sortierungMinVersion" +
            " AND (ve IS NULL OR ve.sortierung > :sortierungMinVersion)" +
            " AND (ve IS NULL OR ve.sortierung <= :sortierungMaxVersion)" +
            " ORDER BY d.sortierung")
    public List<DocumentationNode> findAllByVersionBeginApplicationIdAnAndDocumentationTypeAndVersionSorts(@Param("idApplication") Long applicationId, @Param("documentationType") DocumentationType documentationType, @Param("sortierungMinVersion") Integer sortierungMinVersion, @Param("sortierungMaxVersion") Integer sortierungMaxVersion);

    @Query("SELECT MAX(d.sortierung) FROM DocumentationNode d" +
            " JOIN d.versionBegin vb" +
            " JOIN vb.application app" +
            " WHERE app.id = :idApplication AND d.documentationType = :documentationType")
    public Integer findMaxSort(@Param("idApplication") Long applicationId, @Param("documentationType") DocumentationType documentationType);

}
