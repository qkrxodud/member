package ably.member.repository;

import ably.member.entity.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long>, MessageLogCustom {

}
