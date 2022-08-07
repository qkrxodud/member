package abley.member.repository;

import abley.member.entity.User;

import java.util.Optional;

public interface UserCustom {
    Optional<User> existsDynamicUserQuery(String email, String phoneNum);
}
