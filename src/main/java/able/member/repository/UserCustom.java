package able.member.repository;

import able.member.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserCustom {
    Optional<User> existsDynamicUserQuery(String email, String phoneNum);
}
