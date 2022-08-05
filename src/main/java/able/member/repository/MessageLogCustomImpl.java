package able.member.repository;

import able.member.entity.MessageLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static able.member.entity.QMessageLog.*;

@RequiredArgsConstructor
public class MessageLogCustomImpl implements MessageLogCustom {

    @Autowired
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MessageLog> searchTop1ByPhoneNumber(String phoneNumber) {
        LocalDateTime startDate = LocalDateTime.now().with(LocalTime.NOON);
        LocalDateTime endDate = LocalDateTime.now().with(LocalTime.MAX);

        MessageLog findAuthorization = queryFactory
                .select(messageLog)
                .from(messageLog)
                .where(messageLog.phoneNumber.eq(phoneNumber)
                        .and(messageLog.regDate.between(LocalDateTime.from(startDate), endDate)))
                .orderBy(messageLog.regDate.desc())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(findAuthorization);
    }

    @Override
    public Optional<Integer> countMessageLogByPhoneNumber(String phoneNumber) {
        LocalDateTime startDate = LocalDateTime.now().with(LocalTime.NOON);
        LocalDateTime endDate = LocalDateTime.now().with(LocalTime.MAX);

        List<MessageLog> Authorization = queryFactory
                .selectFrom(messageLog)
                .where(messageLog.regDate.between(startDate, endDate))
                .fetch();
        return Optional.ofNullable(Authorization.size());
    }

}
