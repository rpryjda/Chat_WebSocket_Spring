package com.pryjda.chat.repository;

import com.pryjda.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findChatsByTimeAfter(LocalDateTime time);
}
