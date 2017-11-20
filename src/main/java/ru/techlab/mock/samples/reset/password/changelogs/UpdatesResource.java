package ru.techlab.mock.samples.reset.password.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Flux;
import ru.techlab.mock.samples.reset.password.model.User;
import ru.techlab.mock.samples.reset.password.repository.UserRepository;

@ChangeLog(order = "1")
public class UpdatesResource {
  @ChangeSet(author = "dim777", id = "init", order = "001")
  public void init(MongoTemplate template, ApplicationContext applicationContext) {
    UserRepository userRepository = (UserRepository)applicationContext.getBean("userRepository");

    userRepository
            .saveAll(Flux.just(
                    User.getBuilder().account("zrb052775").name("D Test 0").password("111222").build(), //
                    User.getBuilder().account("zrb052776").name("D Test 1").password("111222").build(), //
                    User.getBuilder().account("zrb052777").name("D Test 2").password("111222").build())) //
            .then() //
            .block();
  }
}
