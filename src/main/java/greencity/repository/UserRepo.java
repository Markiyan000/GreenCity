package greencity.repository;

import greencity.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Provides an interface to manage {@link User} entity.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * Find {@code User} by email.
     *
     * @param email user email.
     * @return {@code User}
     */
    Optional<User> findByEmail(String email);

    /**
     * Find {@code User} by page.
     *
     * @param pageable pageable configuration.
     * @return {@code Page<User>}
     * @author Rostyslav Khasanov
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Find id by email.
     *
     * @param email - User email
     * @return User id
     * @author Zakhar Skaletskyi
     */
    @Query("SELECT id from User where email=:email")
    Optional<Long> findIdByEmail(String email);
}