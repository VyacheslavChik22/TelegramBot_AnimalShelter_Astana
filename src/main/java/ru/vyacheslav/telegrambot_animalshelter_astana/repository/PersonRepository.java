package ru.vyacheslav.telegrambot_animalshelter_astana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;

import java.util.Optional;

/**
 * @author Oleg Alekseenko
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByChatId(Long chatId);
}
