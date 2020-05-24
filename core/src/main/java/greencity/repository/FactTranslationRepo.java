package greencity.repository;

import greencity.entity.FactTranslation;
import greencity.entity.HabitFact;
import greencity.entity.Language;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FactTranslationRepo extends JpaRepository<FactTranslation, Long> {
    /**
     * Method for getting random content by habit id and language code.
     * This method use native SQL query to reduce the load on the backend
     *
     * @param habitId Id of HabitDictionary
     * @return {@link FactTranslation} in Optional
     * @author Vitaliy Dzen
     */
    @Query(nativeQuery = true, value = "SELECT * FROM fact_translations WHERE language_id = "
        + "(SELECT id FROM languages WHERE code = ?1)"
        + " AND habit_fact_id = "
        + "(SELECT id FROM habit_facts WHERE habit_dictionary_id = ?2 ORDER BY RANDOM() LIMIT 1);")
    Optional<FactTranslation> getRandomFactTranslationByHabitIdAndLanguage(String languageCode, Long habitId);

    /**
     * Method find {@link FactTranslation} by content and language code.
     *
     * @param languageCode of {@link Language}
     * @param habitFact    of {@link HabitFact}
     * @return {@link FactTranslation} in Optional
     * @author Vitaliy Dzen
     */
    Optional<FactTranslation> findFactTranslationByLanguage_CodeAndHabitFact(String languageCode, String habitFact);

    /**
     * Method finds random fact in 3 languages between all facts that were not used during this iteration.
     *
     * @return optional list of {@link FactTranslation}
     */
    @Query(nativeQuery = true, value = "SELECT * FROM fact_translations where habit_fact_id in "
        + "(select habit_fact_id from fact_translations where fact_of_day_status = 0 order by RANDOM() LIMIT 1)")
    Optional<List<FactTranslation>> findRandomFact();


    Optional<List<FactTranslation>> findAllByFactOfDayStatusAndLanguageId(int factOfDayStatus, Long languageId);

    /**
     * Method to replace all outdated factOfDay statuses by updated.
     *
     * @param outdated status that needs to be changed
     * @param updated  new status
     */
    @Modifying
    @Query("UPDATE FactTranslation f set f.factOfDayStatus = ?2 where f.factOfDayStatus = ?1")
    void updateFactOfDayStatus(int outdated, int updated);

    /**
     * Method to change fact of day status for all facts with certain habit fact id.
     *
     * @param status      new fact of day status.
     * @param habitfactId habit fact id of group of facts in which method will change fact of day status
     */
    @Modifying
    @Query("UPDATE FactTranslation f set f.factOfDayStatus = ?1 WHERE f.habitFact.id = ?2")
    void updateFactOfDayStatusByHabitfactId(int status, Long habitfactId);
}
