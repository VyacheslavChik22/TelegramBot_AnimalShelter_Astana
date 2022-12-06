package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.dto.FotoObjectDto;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.NoAnimalAdoptedException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonAlreadyExistsException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.TextDoesNotMatchPatternException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonCatServiceTest.getTestPersonCat;
import static ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonDogServiceTest.getTestPerson;
import static ru.vyacheslav.telegrambot_animalshelter_astana.service.ReportServiceTest.addTestReport;

/**Unit tests for {@link TelegramBotUpdatesService} class with {@link PersonDogService} mock.
 *
 * @author Oleg Alekseenko
 */

@ExtendWith(MockitoExtension.class)
public class TelegramBotUpdatesServiceTest {

    @Mock
    private PersonDogService personDogService;
    @Mock
    private PersonCatService personCatService;
    @Mock
    private ReportService reportService;

    @InjectMocks
    private TelegramBotUpdatesService out;

    @Test
    void shouldCreateNewPersonDog_whenContactDataReceived() {
        PersonDog testPerson = new PersonDog();
        testPerson.setName("Test");
        testPerson.setPhone("79031234567");
        testPerson.setEmail("test@gmail.com");
        testPerson.setAddress("City");
        testPerson.setChatId(1L);

        String testMessage = "Имя: Test;\n" +
                "Телефон: +79031234567;\n" +
                "Email: test@gmail.com;\n" +
                "Адрес: City";

        when(personDogService.findPersonByChatId(anyLong())).thenReturn(Optional.empty());
        when(personDogService.createPerson(any(PersonDog.class))).thenReturn(testPerson);

        out.createPersonFromMessage(1L, testMessage, AnimalType.DOG);

        verify(personDogService, atLeastOnce()).createPerson(any(PersonDog.class));
        verify(personCatService, never()).createPerson(any(PersonCat.class));
    }

    @Test
    void shouldCreateNewPersonCat_whenContactDataReceived() {
        PersonCat testPerson = new PersonCat();
        testPerson.setName("Test");
        testPerson.setPhone("79031234567");
        testPerson.setEmail("test@gmail.com");
        testPerson.setAddress("City");
        testPerson.setChatId(1L);

        String testMessage = "Имя: Test;\n" +
                "Телефон: +79031234567;\n" +
                "Email: test@gmail.com;\n" +
                "Адрес: City";

        when(personCatService.findPersonByChatId(anyLong())).thenReturn(Optional.empty());
        when(personCatService.createPerson(any(PersonCat.class))).thenReturn(testPerson);

        out.createPersonFromMessage(1L, testMessage, AnimalType.CAT);

        verify(personCatService, atLeastOnce()).createPerson(any(PersonCat.class));
        verify(personDogService, never()).createPerson(any(PersonDog.class));
    }

    @Test
    void shouldThrowPersonAlreadyExistsException_whenCreateNewPersonFromContactData() {
        when(personDogService.findPersonByChatId(anyLong())).thenReturn(Optional.of(getTestPerson(1L, "Test")));

        assertThatThrownBy(() -> out.createPersonFromMessage(anyLong(), "anyString()", AnimalType.DOG)).isInstanceOf(PersonAlreadyExistsException.class);
        verify(personDogService, never()).createPerson(any(PersonDog.class));
    }

    @Test
    void shouldThrowPersonAlreadyExistsException_whenCreateNewPersonCatFromContactData() {
        when(personCatService.findPersonByChatId(anyLong())).thenReturn(Optional.of(getTestPersonCat(1L, "Test")));

        assertThatThrownBy(() -> out.createPersonFromMessage(anyLong(), "anyString()", AnimalType.CAT)).isInstanceOf(PersonAlreadyExistsException.class);
        verify(personCatService, never()).createPerson(any(PersonCat.class));
    }

    @Test
    void shouldThrowTextPatternDoesNotMatchException_whenCreateNewPersonFromWrongMessage() {
        String testMessage = "Имя: Test;\n" +
                "Телефон: +79;\n" +
                "Почта: test";

        when(personDogService.findPersonByChatId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.createPersonFromMessage(anyLong(), testMessage, AnimalType.DOG)).isInstanceOf(TextDoesNotMatchPatternException.class);
        verify(personDogService, never()).createPerson(any(PersonDog.class));
    }

    @Test
    void shouldThrowNoAnimalException_whenCountDaysFromAdoptionForPersonWithoutAnimal() {
        PersonDog testPerson = getTestPerson(1L, "Test");

        when(personDogService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.countDaysFromAdoption(anyLong(), AnimalType.DOG)).isInstanceOf(NoAnimalAdoptedException.class);
    }

    @Test
    void shouldThrowNoAnimalException_whenCountDaysFromAdoptionForPersonCatWithoutAnimal() {
        PersonCat testPerson = getTestPersonCat(1L, "Test");

        when(personCatService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.countDaysFromAdoption(anyLong(), AnimalType.CAT)).isInstanceOf(NoAnimalAdoptedException.class);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenCreateNewReportForUserNotInDB() {
        when(personDogService.findPersonByChatId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.createReportFromMessage(anyLong(), null, null, AnimalType.DOG)).isInstanceOf(PersonNotFoundException.class);
        verify(personDogService, never()).updatePerson(any(PersonDog.class));
        verify(reportService, never()).addReport(any(Report.class));
    }

    @Test
    void shouldThrowException_whenCreateReportWithoutAnimal() {
        PersonDog testPerson = new PersonDog();
        testPerson.setId(1L);
        testPerson.setChatId(1L);

        when(personDogService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.createReportFromMessage(testPerson.getChatId(), new FotoObjectDto(), "null", AnimalType.DOG))
                .isInstanceOf(NoAnimalAdoptedException.class);

        verify(personDogService, never()).updatePerson(any(PersonDog.class));
        verify(reportService, never()).addReport(any(Report.class));
    }

    @Test
    void shouldThrowException_whenCreateReportWithTheSameDate() {
        PersonDog testPerson = new PersonDog();
        testPerson.setId(1L);
        testPerson.setChatId(1L);
        testPerson.setAnimal(new Animal(1L, "Test cat", AnimalType.DOG));
        testPerson.setLastReportDate(LocalDate.now());

        when(personDogService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.createReportFromMessage(testPerson.getChatId(), new FotoObjectDto(), "null", AnimalType.DOG))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Report has sent");

        verify(personDogService, never()).updatePerson(any(PersonDog.class));
        verify(reportService, never()).addReport(any(Report.class));
    }

    @Test
    void shouldCreateNewReportFromMessage() {
        PersonDog testPerson = new PersonDog();
        testPerson.setId(1L);
        testPerson.setChatId(1L);
        testPerson.setAnimal(new Animal(1L, "Test cat", AnimalType.DOG));

        String caption = "text";

        FotoObjectDto fotoObjDto = new FotoObjectDto("type", "path", new byte[1], 1);

        Report testReport = addTestReport(1L, caption);
        testReport.setReportDate(LocalDate.now());
        testReport.setPhotoPath(fotoObjDto.getPhotoPath());
        testReport.setPhotoSize(fotoObjDto.getPhotoSize());
        testReport.setPhotoData(fotoObjDto.getPhotoData());
        testReport.setMediaType(fotoObjDto.getMediaType());
        testReport.setPersonDog(testPerson);

        when(personDogService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));
        when(reportService.addReport(any(Report.class))).thenReturn(testReport);

        Report result = out.createReportFromMessage(testPerson.getChatId(), fotoObjDto, caption, AnimalType.DOG);

        assertThat(result).isNotNull()
                .isInstanceOf(Report.class)
                .isEqualTo(testReport);

    }

    @Test
    void shouldCreateNewReportFromMessageForPersonCat() {
        PersonCat testPerson = new PersonCat();
        testPerson.setId(1L);
        testPerson.setChatId(1L);
        testPerson.setAnimal(new Animal(1L, "Test cat", AnimalType.CAT));

        String caption = "text";

        FotoObjectDto fotoObjDto = new FotoObjectDto("type", "path", new byte[1], 1);

        Report testReport = addTestReport(1L, caption);
        testReport.setReportDate(LocalDate.now());
        testReport.setPhotoPath(fotoObjDto.getPhotoPath());
        testReport.setPhotoSize(fotoObjDto.getPhotoSize());
        testReport.setPhotoData(fotoObjDto.getPhotoData());
        testReport.setMediaType(fotoObjDto.getMediaType());
        testReport.setPersonCat(testPerson);

        when(personCatService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));
        when(reportService.addReport(any(Report.class))).thenReturn(testReport);

        Report result = out.createReportFromMessage(testPerson.getChatId(), fotoObjDto, caption, AnimalType.CAT);

        assertThat(result).isNotNull()
                .isInstanceOf(Report.class)
                .isEqualTo(testReport);
    }

    @Test
    void shouldReturnListSizeTwo_whenFindPeopleToRemind() {
        LocalDate testDate = LocalDate.now();
        PersonDog dog_person = getTestPerson(1L, "Dog person");
        dog_person.setChatId(123L);
        PersonCat cat_person = getTestPersonCat(1L, "Cat person");
        cat_person.setChatId(456L);

        when(personDogService.findAllByLastReportDateBefore(testDate)).thenReturn(List.of(dog_person));
        when(personCatService.findAllByLastReportDateBefore(testDate)).thenReturn(List.of(cat_person));

        List<Long> result = out.findPeopleToRemind();

        assertThat(result).hasSize(2)
                .contains(dog_person.getChatId(), cat_person.getChatId());
    }


}
