package net.duke.dkuguide.data.repositories;

import net.duke.dkuguide.data.models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends CrudRepository<Course, Long> {
}
