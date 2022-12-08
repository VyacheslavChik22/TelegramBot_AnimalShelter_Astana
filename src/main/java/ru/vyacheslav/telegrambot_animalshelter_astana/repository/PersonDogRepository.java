package ru.vyacheslav.telegrambot_animalshelter_astana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonDog;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Alekseenko
 */
@Repository
public interface PersonDogRepository extends JpaRepository<PersonDog, Long> {
    Optional<PersonDog> findPersonByChatId(Long chatId);

    List<PersonDog> findAllByLastReportDateBeforeOrLastReportDateIsNullAndAnimalIsNotNull(LocalDate date);
}
