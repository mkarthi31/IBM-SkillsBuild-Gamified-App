package org.example.group34.repo;

import org.example.group34.model.FriendRequest;
import org.example.group34.model.FriendshipStatus;
import org.example.group34.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    //find friend request on receivers end
    List<FriendRequest> findByReceiverAndStatus(User receiver, FriendshipStatus status);
    //find request on senders end
    List<FriendRequest> findBySenderAndStatus(User sender, FriendshipStatus status);
    //check if a request exists
    boolean existsByReceiverAndSender(User sender,User receiver);
    boolean existsBySenderAndReceiver(User sender, User receiver);
}
