package ua.volivach.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.volivach.library.models.Person;
import ua.volivach.library.services.PeopleService;


import java.sql.SQLException;
import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Optional<Person> existingPerson = peopleService.findByFullName(person.getFullName());
        if (existingPerson.isPresent()) {
            if (person.getId() != 0 && existingPerson.get().getId() == person.getId()) {
                return;
            }
            errors.rejectValue("fullName", "", "This name is already in use");
        }
    }
}
