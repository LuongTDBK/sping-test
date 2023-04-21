package vn.neo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.neo.model.User;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    User findUserByUsername(String username);
}
