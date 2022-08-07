package ably.member.repository;

import ably.member.entity.MessageLog;

import java.util.Optional;

public interface MessageLogCustom {
    Optional<MessageLog> searchTop1ByPhoneNumber(String phoneNumber);

    Optional<Integer> countMessageLogByPhoneNumber(String phoneNumber);
}
