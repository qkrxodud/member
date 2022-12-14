package ably.member.repository;

import ably.member.entity.QUser;
import ably.member.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

import static ably.member.entity.QUser.*;

@RequiredArgsConstructor
public class UserCustomImpl implements UserCustom{

    @Autowired
    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<User> existsDynamicUserQuery(String email, String phoneNum) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!ObjectUtils.isEmpty(email)) {
            builder.and(user.mail.eq(email));
        }

        if (!ObjectUtils.isEmpty(phoneNum)) {
            builder.and(user.phoneNumber.eq(phoneNum));
        }

        User user = queryFactory
                .selectFrom(QUser.user)
                .where(builder)
                .fetchOne();

        return Optional.ofNullable(user);
    }
}
