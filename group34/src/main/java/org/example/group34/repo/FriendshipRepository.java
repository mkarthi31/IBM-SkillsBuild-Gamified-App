package org.example.group34.repo;

import org.example.group34.model.Friendship;
import org.example.group34.model.FriendshipStatus;
import org.example.group34.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    //to find who it is sent to and its status
    List<Friendship> findByReceiverAndStatus(User receiver, FriendshipStatus status);
    //to find who sent it and its status
    List<Friendship> findByRequesterAndStatus(User requester, FriendshipStatus status);
    //to find who sent it and who got it
    Optional<Friendship> findByRequesterAndReceiver(User requester, User receiver);
    //find all friends of username
    @Query("SELECT f FROM Friendship f WHERE f.requester = :user OR f.receiver = :user")
    List<Friendship> findAllByUser(@Param("user") User user);
    //find if friends exist
    boolean existsByRequesterAndReceiver(User requester, User receiver);
    boolean existsByRequesterAndReceiverOrReceiverAndRequester(User requester, User receiver, User receiver2, User requester2);

    void deleteByRequesterAndReceiverOrReceiverAndRequester(User requester, User receiver, User receiver2, User requester2);


    Optional<Friendship> findByRequesterAndReceiverOrReceiverAndRequester(User user1, User user2, User user21, User user11);

}
