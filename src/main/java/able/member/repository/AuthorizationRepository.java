package able.member.repository;

import able.member.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

    Optional<Authorization> findByMailAndPhoneNumber(String mail, String phoneNum);

    Boolean existsByPhoneNumber(String phoneNumber);

}
