package ru.techlab.mock.samples.reset.password;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.techlab.mock.samples.reset.password.model.User;
import ru.techlab.mock.samples.reset.password.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SamplesResetPasswordRestApplicationTests {

	@Autowired private WebTestClient webTestClient;

	@Autowired private ReactiveMongoOperations operations;
	@Autowired private UserRepository userRepository;

	@Before
	public void setUp() {
		operations.collectionExists(User.class) //
				.flatMap(exists -> exists ? operations.dropCollection(User.class) : Mono.just(exists)) //
				.flatMap(o -> operations.createCollection(User.class, CollectionOptions.empty().size(1024 * 1024).maxDocuments( 100).capped())) //
				.then() //
				.block();

		userRepository
				.saveAll(Flux.just(
						User.getBuilder().account("zrb052775").name("D Test 0").password("111222").build(), //
						User.getBuilder().account("zrb052776").name("D Test 1").password("111222").build(), //
						User.getBuilder().account("zrb052777").name("D Test 2").password("111222").build())) //
				.then() //
				.block();

	}

	@Test
	public void givenRouter_whenGetUsers_thenGotArticlesList() {
		webTestClient
				// We then create a GET request to test an endpoint
				.get().uri("/users?size=20")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(User.class)
				.hasSize(2)
				.consumeWith(allArticles -> assertThat(allArticles)
						.satisfies(article -> assertThat(article.getResponseBody().size()).isPositive()));

	}

	@Test
	public void givenNewUserForm_whenDataIsValid_thenSuccess(){
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>(8);
		formData.add("title", "Title 1");
		formData.add("alias", "title-1");
		formData.add("fulltext", "Fulltext fulltext");
		formData.add("created", "01-01-2017");
		formData.add("publish", "true");
		formData.add("createdBy", "dim777");
		formData.add("image", "");
		formData.add("language", "RU");

		webTestClient.post().uri("/user")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData(formData))
				.exchange()
				.expectStatus()
				.isOk();
	}

	/**
	 * Test RESTful API
	 */
	@Test
	public void givenNewUser_whenDataIsValid_thenSuccess(){
		Article article = new Article();
		article.setId(4L);
		article.setTitle("Title 2");
		article.setAlias("title-2");
		article.setFulltext("Fulltext fulltext");
		article.setCreated("01-02-2017");
		article.setPublish(true);
		article.setCreatedBy("dim777");
		article.setImage(null);
		article.setLanguage(Lang.EN);

		webTestClient.post()
				.uri("/article/json")
				.body(fromObject(article))
				.exchange()
				.expectStatus().isOk();
	}

}
