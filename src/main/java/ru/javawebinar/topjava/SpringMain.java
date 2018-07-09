package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userNameD", "email1@mail.ru", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "userNameA", "email2@mail.ru", "password", Role.ROLE_USER));
            SecurityUtil.setId(2);
            adminUserController.create(new User(null, "userNameC", "email3@mail.ru", "password", Role.ROLE_USER));
            adminUserController.create(new User(null, "userNameB", "email4@mail.ru", "password", Role.ROLE_ADMIN));
            System.out.println(adminUserController.getByMail("email1@mail.ru"));
            System.out.println(adminUserController.getByMail("email4@mail.ru"));
            System.out.println(adminUserController.getAll());
            MealRepository mealRepository = appCtx.getBean(MealRepository.class);
            System.out.println(mealRepository.getAll());
            System.out.println(mealRepository.get(5));
            SecurityUtil.setId(1);
            User user = adminUserController.get(1);
            System.out.println(adminUserController.getAll());
            System.out.println(user);

            mealRepository = appCtx.getBean(MealRepository.class);
            System.out.println(mealRepository.getAll());
            System.out.println(mealRepository.get(5));

            adminUserController.delete(1);
        }
    }
}
