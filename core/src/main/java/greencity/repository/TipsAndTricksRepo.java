package greencity.repository;

import greencity.entity.TipsAndTricks;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipsAndTricksRepo extends JpaRepository<TipsAndTricks, Long> {
    /**
     * Method returns {@link TipsAndTricks} by specific tags.
     *
     * @param tags list of tags to search by.
     * @return {@link TipsAndTricks} by specific tags.
     */
    @Query("SELECT DISTINCT tt FROM TipsAndTricks tt "
        + "JOIN tt.tipsAndTricksTags ttt "
        + "WHERE ttt.name in :tags "
        + "ORDER BY tt.creationDate DESC")
    Page<TipsAndTricks> find(Pageable pageable, List<String> tags);

    /**
     * Method returns all {@link TipsAndTricks} by page.
     *
     * @param page of tips & tricks.
     * @return all {@link TipsAndTricks} by page.
     */
    Page<TipsAndTricks> findAllByOrderByCreationDateDesc(Pageable page);

    /**
     * Method returns {@link TipsAndTricks} by search query and page.
     *
     * @param searchQuery query to search
     * @return list of {@link TipsAndTricks}
     */
    @Query("select tt from TipsAndTricks tt "
        + "where lower(tt.title) like lower(CONCAT('%', :searchQuery, '%')) "
        + "or lower(tt.text) like lower(CONCAT('%', :searchQuery, '%')) "
        + "or tt.id in (select tt.id from TipsAndTricks tt inner join tt.tipsAndTricksTags ttt "
        + "where lower(ttt.name) like lower(CONCAT('%', :searchQuery, '%')))")
    Page<TipsAndTricks> searchTipsAndTricks(Pageable pageable, String searchQuery);
}
