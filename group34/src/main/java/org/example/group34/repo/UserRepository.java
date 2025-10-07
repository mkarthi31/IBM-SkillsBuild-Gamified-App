package org.example.group34.repo;

import org.example.group34.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findById(int userId);

    //find username ignoring lower/uppercase
    List<User> findByUsernameContainingIgnoreCase(String username);

    //Find all users ordered by points descending
    List<User> findAllByOrderByPointsDesc();


}
