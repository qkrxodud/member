package able.member.repository;

import able.member.entity.ConfirmLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static able.member.entity.QAuthorization.*;

@RequiredArgsConstructor
public class AuthorizationCustomImpl implements ConfirmLogCustom {

    @Autowired
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ConfirmLog> findTop1ByAuthorization(String phoneNumber) {
        LocalDateTime startDate = LocalDateTime.now().with(LocalTime.NOON);
        LocalDateTime endDate = LocalDateTime.now().with(LocalTime.MAX);

        ConfirmLog findAuthorization = queryFactory
                .select(authorization)
                .from(authorization)
                .where(authorization.phoneNumber.eq(phoneNumber)
                        .and(authorization.regDate.between(LocalDateTime.from(startDate), endDate)))
                .orderBy(authorization.regDate.desc())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(findAuthorization);
    }

    @Override
    public Optional<Integer> findCountAuthorization(String phoneNumber) {
        LocalDateTime startDate = LocalDateTime.now().with(LocalTime.NOON);
        LocalDateTime endDate = LocalDateTime.now().with(LocalTime.MAX);

        List<ConfirmLog> Authorization = queryFactory
                .selectFrom(authorization)
                .where(authorization.regDate.between(startDate, endDate))
                .fetch();
        return Optional.ofNullable(Authorization.size());
    }

}
