package net.duke.dkuguide.service.implement;

import net.duke.dkuguide.data.models.Student;
import net.duke.dkuguide.data.repositories.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailsImpl implements UserDetailsService {

    private StudentRepo studentRepo;

    @Autowired
    public StudentDetailsImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        return UserDetailsImpl.build(student);
    }
}
