package able.member.repository;

import able.member.entity.ConfirmLog;

import java.util.Optional;

public interface ConfirmLogCustom {
    Optional<ConfirmLog> findTop1ByAuthorization(String phoneNumber);

    Optional<Integer> findCountAuthorization(String phoneNumber);
}
