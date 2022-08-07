package abley.member.repository;

import abley.member.entity.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long>, MessageLogCustom {

}
