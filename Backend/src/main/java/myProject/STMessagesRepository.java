package myProject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface STMessagesRepository extends JpaRepository<STMessages, Integer> {

}