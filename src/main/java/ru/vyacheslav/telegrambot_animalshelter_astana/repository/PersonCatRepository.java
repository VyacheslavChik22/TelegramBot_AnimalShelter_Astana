package ru.vyacheslav.telegrambot_animalshelter_astana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonCat;

import java.util.Optional;

@Repository
public interface PersonCatRepository extends JpaRepository<PersonCat, Long> {

    Optional<PersonCat> findPersonByChatId(Long chatId);
}
