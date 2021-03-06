package com.authenticationservice.rabbitmq;

import com.authenticationservice.common.exception.PersonMissingException;
import com.authenticationservice.common.model.Person;
import com.authenticationservice.service.PersonService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonMessageConsumer {
    @Autowired
    private PersonService personService;

    @RabbitListener(queues = { "#{authenticationPersonCreateQueue.name}" })
    public void receivedPersonCreated(Person person) {
        personService.createPerson(person);
    }

    @RabbitListener(queues = { "#{authenticationPersonDeleteQueue.name}" })
    public void receivedPersonDeleted(String personnummer) {
        try {
            personService.deletePerson(personnummer);
        } catch (PersonMissingException e) {
            // do nothing
        }
    }
}
