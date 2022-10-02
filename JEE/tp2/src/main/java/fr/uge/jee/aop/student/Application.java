package fr.uge.jee.aop.student;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class Application {

    public static void main(String[] args) throws InterruptedException {
        var context = new AnnotationConfigApplicationContext(Application.class);
        var registrationService = context.getBean(RegistrationService.class);
        registrationService.loadFromDB();
        var idHarry = registrationService.createStudent("Harry", "Potter");
        var idHermione = registrationService.createStudent("Hermione", "Granger");
        var idRon = registrationService.createStudent("Ron", "Wesley");
        registrationService.saveToDB();
        var idPotions = registrationService.createLecture("Potions");
        registrationService.register(idHarry, idPotions);
        registrationService.register(idHermione, idPotions);
        registrationService.register(idRon, idPotions);
        registrationService.saveToDB();
        var idMalfoy = registrationService.createStudent("Draco", "Malfoy");
        registrationService.saveToDB();
        registrationService.loadFromDB();
        var idDetention = registrationService.createLecture("Detention");
        registrationService.register(idHarry, idDetention);
        registrationService.register(idMalfoy, idDetention);

        registrationService.printReport();

        var timer = context.getBean(DBTimerAspect.class);
        timer.printReport();
    }
}
