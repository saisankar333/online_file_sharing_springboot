package com.service;

import com.entity.Groups;
import com.entity.User;
import com.repository.GroupsRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Service
public class GroupShareService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupsRepository groupRepository;

    // Create a new group
    public boolean createGroup(String groupName, String memberEmail) {
        User user = userRepository.findByEmail(memberEmail);
        if (user == null) return false;

        Groups group = new Groups();
        group.setGroupName(groupName);
        group.setOwnerId(user.getId());

        groupRepository.save(group);

        File folder = new File("./" + group.getGroupId());
        folder.mkdir();
        return true;
    }

    // Add members to a group
    public boolean addMembersToGroup(Integer groupId, String memberEmail) {
        Groups group = groupRepository.findByGroupId(groupId);
        User groupMemberUser = userRepository.findByEmail(memberEmail);

        if (group == null || groupMemberUser == null) return false;

        // Add user to group
        Set<User> memberSet = group.getUsers();
        if (memberSet == null) memberSet = new HashSet<>();
        memberSet.add(groupMemberUser);
        group.setUsers(memberSet);

        // Add group to user
        Set<Groups> groupSet = groupMemberUser.getGroups();
        if (groupSet == null) groupSet = new HashSet<>();
        groupSet.add(group);
        groupMemberUser.setGroups(groupSet);

        userRepository.save(groupMemberUser);
        groupRepository.save(group);

        return true;
    }

    // Get all groups of a user by userId
    public Set<Groups> getUserGroups(Integer userId) {
        return userRepository.findById(userId)
                .map(User::getGroups)
                .orElse(new HashSet<>());
    }

    // Get all groups of a user by email (needed for controller)
    public Groups[] listUserGroups(String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) return new Groups[]{};

        User user = userRepository.findByEmail(userEmail);
        if (user == null) return new Groups[]{};

        Set<Groups> groupSet = user.getGroups();
        if (groupSet == null) return new Groups[]{};

        return groupSet.toArray(new Groups[groupSet.size()]);
    }

    // Get all users in a group by groupId (needed for controller)
    public User[] listGroupMembers(int groupId) {
        Groups group = groupRepository.findByGroupId(groupId);
        if (group == null) return new User[]{};

        Set<User> memberSet = group.getUsers();
        if (memberSet == null) return new User[]{};

        return memberSet.toArray(new User[memberSet.size()]);
    }

    // Upload a file to a group folder
    public void uploader(MultipartFile file, Integer groupId) {
        try {
            byte[] bytes = file.getBytes();
            File dir = new File("./" + groupId);
            if (!dir.exists()) dir.mkdir();
            Path path = Paths.get("./" + groupId + "/" + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}