package org.example.group34.service;

import org.example.group34.model.FriendRequest;
import org.example.group34.model.FriendshipStatus;
import org.example.group34.model.User;
import org.example.group34.repo.FriendRequestRepository;
import org.example.group34.repo.FriendshipRepository;
import org.example.group34.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;


    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public String sendFriendRequest(String senderUsername, String receiverUsername) {
        if (senderUsername.equals(receiverUsername)) {
            return "You cannot send a friend request to yourself!";
        }

        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);

        // Check if they are already friends
        if (friendshipRepository.existsByRequesterAndReceiver(sender, receiver) ||
                friendshipRepository.existsByRequesterAndReceiver(receiver, sender)) {
            return "You are already friends with this user.";
        }

        // Check if a friend request already exists (in either direction)
        if (friendRequestRepository.existsBySenderAndReceiver(sender, receiver) ||
                friendRequestRepository.existsByReceiverAndSender(sender, receiver)) {
            return "Friend request already sent or pending.";
        }

        // Create a new friend request
        FriendRequest request = new FriendRequest(sender, receiver, FriendshipStatus.PENDING);
        friendRequestRepository.save(request);

        return "Friend request sent.";
    }


    //find all request not yet touched with status pending
    public List<FriendRequest> getPendingRequests(User user) {
        return friendRequestRepository.findByReceiverAndStatus(user, FriendshipStatus.PENDING);
    }

    //reject request by assigning it a status declined then deleting it from the friend request table
    public void rejectFriendRequest(int requestId) {
        Optional<FriendRequest> requestOptional = friendRequestRepository.findById(requestId);
        if (requestOptional.isPresent()) {
            FriendRequest request = requestOptional.get();
            request.setStatus(FriendshipStatus.DECLINED);
            friendRequestRepository.deleteById(requestId);

        }
    }

    //check if a friend request is pending
    public boolean isRequestPending(User sender, User receiver) {
        return friendRequestRepository.existsByReceiverAndSender(receiver, sender) ||
                friendRequestRepository.existsByReceiverAndSender(sender, receiver);
    }


}
