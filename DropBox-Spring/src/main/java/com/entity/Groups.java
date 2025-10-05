package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_groups") // avoid reserved word
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "owner_id")
    private Integer ownerId;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<User> users;

    // Getters and Setters
    public Integer getGroupId() { return groupId; }
    public void setGroupId(Integer groupId) { this.groupId = groupId; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }

    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }
}