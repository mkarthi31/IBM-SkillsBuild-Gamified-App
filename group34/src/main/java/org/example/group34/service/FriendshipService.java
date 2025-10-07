package org.example.group34.service;

import org.example.group34.model.FriendRequest;
import org.example.group34.model.Friendship;
import org.example.group34.model.FriendshipStatus;
import org.example.group34.model.User;
import org.example.group34.repo.FriendRequestRepository;
import org.example.group34.repo.FriendshipRepository;
import org.example.group34.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    //make a new friend
    public String sendFriendRequest(int requesterId, int receiverId){

        //find sender and receiver by id
        User requester = userRepository.findById(requesterId);
        User receiver = userRepository.findById(receiverId);

        //check if a friend request has already been sent (not currently working)
        if (friendshipRepository.findByRequesterAndReceiver(requester, receiver).isPresent()) {
           return "Friend request already sent!";
        }

        //create new instance of friendship
        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setReceiver(receiver);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);

        return "Friend request sent!";

    }

    //accept a friend request by setting status to accepted then deleting the request from the table
    public void acceptFriendRequest(int requestId, String currentUsername){
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findById(requestId);

        if (friendRequestOptional.isPresent()) {
            FriendRequest friendRequest = friendRequestOptional.get();
            User requester = friendRequest.getSender();
            User receiver = friendRequest.getReceiver();

            if (!receiver.getUsername().equals(currentUsername)) {
                throw new IllegalStateException("Only recevier can accept friend request");
            }


            Friendship friendship = new Friendship();
            friendship.setRequester(requester);
            friendship.setReceiver(receiver);
            friendship.setStatus(FriendshipStatus.ACCEPTED);
            friendshipRepository.save(friendship);

            friendRequestRepository.deleteById(requestId);
        }
    }

    //get all friends
    public List<User> getFriends(String username) {
        User user = userRepository.findByUsername(username);
        List<Friendship> friendships = friendshipRepository.findAllByUser(user);

        //when adding friends it checks if the user is a receiver or requester then based of that gets the other
        return friendships.stream()
                .map(f -> f.getRequester().equals(user) ? f.getReceiver() : f.getRequester())
                //only shows unique; no copies
                .distinct()
                .toList();

    }

    //for disabling the add friend button when 2 users are friends
    public boolean areFriends(User user1, User user2) {
        return friendshipRepository.existsByRequesterAndReceiver(user1, user2) ||
                friendshipRepository.existsByRequesterAndReceiver(user2, user1);
    }

    public void unfriend(String currentUsername, String friendUsername) {
        User currentUser = userRepository.findByUsername(currentUsername);
        User friend = userRepository.findByUsername(friendUsername);

        // Find and delete the friendship where the current user is either the sender or receiver
        Optional<Friendship> friendship = friendshipRepository.findByRequesterAndReceiver(currentUser, friend);
        if (friendship.isEmpty()) {
            friendship = friendshipRepository.findByRequesterAndReceiver(friend, currentUser);
        }

        friendship.ifPresent(friendshipRepository::delete);
    }






}
