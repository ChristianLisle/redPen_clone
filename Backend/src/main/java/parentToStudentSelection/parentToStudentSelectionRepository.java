package parentToStudentSelection;

import java.util.Collection;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface parentToStudentSelectionRepository extends CrudRepository<parentToStudentSelection, Integer> {
	
	Collection<parentToStudentSelection> findAll();
	
	Collection<parentToStudentSelection> findById(@Param("id") int id);
	
	parentToStudentSelection save(parentToStudentSelection p2ss);
	
}