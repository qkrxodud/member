package abley.member.repository;

import abley.member.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

    Optional<Authorization> findByMailAndPhoneNumber(String mail, String phoneNum);

    Boolean existsByPhoneNumber(String phoneNumber);

}
