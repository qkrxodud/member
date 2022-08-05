package able.member.repository;

import able.member.entity.ConfirmLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmLogRepository extends JpaRepository<ConfirmLog, Long>, ConfirmLogCustom {

}
