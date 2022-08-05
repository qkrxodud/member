package able.member.repository;

import able.member.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

    Optional<Authorization> findByPhoneNumber(String userId);

    Boolean existsByPhoneNumber(String phoneNumber);

}
