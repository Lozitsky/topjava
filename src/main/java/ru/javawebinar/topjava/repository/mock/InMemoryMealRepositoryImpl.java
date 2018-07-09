package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepositoryImpl.class);
    private Map <Integer, Meal> repository = new ConcurrentHashMap <>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.debug("save meal {}", meal);
        if (!SecurityUtil.checkUser(meal.getUserId())) {
//            new NotFoundException("Meal was not changed. The wrong user!");
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        Integer userId = repository.get(id).getUserId();
        log.debug("delete, meal id {}, userId {}", id, userId);
        if (!SecurityUtil.checkUser(userId)) {
//            new NotFoundException("It is not your meal!");
            return false;
        }
        return repository.remove(id).getId() == id;
    }

    @Override
    public Meal get(int id) {
        Integer userId = repository.get(id).getUserId();
        log.debug("get, meal id {}, userId {}", id, userId);
        if (!SecurityUtil.checkUser(userId)) {
//            new NotFoundException("It is not your meal!");
            return null;
        }
        return repository.get(id);
    }

    @Override
    public Collection <Meal> getAll() {

        return repository.values().stream()
                .filter(meal -> SecurityUtil.checkUser(meal.getUserId()))
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }
}

