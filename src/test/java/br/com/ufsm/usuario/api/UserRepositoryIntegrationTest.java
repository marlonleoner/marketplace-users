package br.com.ufsm.usuario.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ufsm.usuario.api.model.User;
import br.com.ufsm.usuario.api.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        User pedro = new User("Pedro", "Vargas", "52146985214", "pedro@gmail.com", "pedro123");
        entityManager.persist(pedro);
        entityManager.flush();

        // when
        User found = userRepository.findByEmail(pedro.getEmail());

        // then
        assertThat(found.getFirstName()).isEqualTo(pedro.getFirstName());
    }
    
    @Test
    public void whenFindByFirstNameContains_thenReturnUserList() {
        // given
        User pedro = new User("Pedro", "Vargas", "52146985214", "pedro@gmail.com", "pedro123");
        entityManager.persist(pedro);
        entityManager.flush();

        // when
        List<User> found = userRepository.findByFirstNameContains(pedro.getFirstName());

        // then
        assertThat(found.contains(pedro));
    }
	
}
