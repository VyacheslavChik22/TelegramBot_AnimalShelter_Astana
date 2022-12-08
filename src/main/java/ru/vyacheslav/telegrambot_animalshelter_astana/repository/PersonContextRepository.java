package ru.vyacheslav.telegrambot_animalshelter_astana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonContext;

import java.util.Optional;

@Repository
public interface PersonContextRepository extends JpaRepository<PersonContext, Long> {

    Optional<PersonContext> findByChatId(Long chatId);
}
