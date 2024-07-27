package org.example.webserver.components.user;

import jakarta.transaction.Transactional;
import org.example.webserver.lib.types.IsObjectDeleted;
import org.example.webserver.lib.types.UserRole;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import static org.example.webserver.lib.types.UserRole.MANAGER;
import static org.example.webserver.lib.types.UserRole.MEMBER;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getUsers() {
        // Get all users and filter out the ones that are disabled (soft-deleted)
        List<UserModel> userList = this.userRepository.findAll();
        userList.removeIf(user -> user.getIsDeleted() == IsObjectDeleted.TRUE);

        return userList;
    }

    public Optional<UserModel> getUserById(Integer id) {
        Optional<UserModel> foundUser = this.userRepository.findById(id);

        if (foundUser.isPresent() && foundUser.get().getIsDeleted() == IsObjectDeleted.TRUE)
            return Optional.empty();

        return foundUser;
    }

    public Optional<UserModel> getUserByUserName(String userName) {
        Optional<UserModel> foundUser = this.userRepository.findByUserName(userName);

        if (foundUser.isPresent() && foundUser.get().getIsDeleted() == IsObjectDeleted.TRUE)
            return Optional.empty();

        return foundUser;
    }

    public List<UserModel> getUsersByRole(String role) {
        List<UserModel> foundUsers = this.userRepository.findByRole(role);

        foundUsers.removeIf(user -> user.getIsDeleted() == IsObjectDeleted.TRUE);

        return foundUsers;
    }

    public UserModel addUser(UserModel user) {
        return this.userRepository.save(user);
    }

    public List<UserModel> getMembers() {
        List<UserModel> members = this.userRepository.findMembers();
        members.removeIf(user -> user.getIsDeleted() == IsObjectDeleted.TRUE);

        return members;
    }

    public List<UserModel> getManagers() {
        // Filtering has been implemented in the repository query injection
        List<UserModel> managers = this.userRepository.findManagers();
        managers.removeIf(user -> user.getIsDeleted() == IsObjectDeleted.TRUE);

        return managers;
    }

    @Transactional
    public String updateUser(Integer id, UserModel updatedUser) {
        Optional<UserModel> currentUser = this.userRepository.findById(id);
        UserModel finalUser = new UserModel();

        finalUser.setId(id);

        if (updatedUser.getFirstName() != null)
            finalUser.setFirstName(updatedUser.getFirstName());
        else finalUser.setFirstName(currentUser.get().getFirstName());

        if (updatedUser.getLastName() != null)
            finalUser.setLastName(updatedUser.getLastName());
        else finalUser.setLastName(currentUser.get().getLastName());

        if (updatedUser.getUserName() != null)
            finalUser.setUserName(updatedUser.getUserName());
        else finalUser.setUserName(currentUser.get().getUserName());

        if (updatedUser.getEmail() != null)
            finalUser.setEmail(updatedUser.getEmail());
        else finalUser.setEmail(currentUser.get().getEmail());

        if (updatedUser.getPassword() != null)
            finalUser.setPassword(updatedUser.getPassword());
        else finalUser.setPassword(currentUser.get().getPassword());

        if (updatedUser.getRole() != null)
            finalUser.setRole(updatedUser.getRole());
        else finalUser.setRole(currentUser.get().getRole());

        int updatedRow = this.userRepository.updateUser(finalUser.getId(), finalUser.getFirstName(), finalUser.getLastName(),
                finalUser.getEmail(), finalUser.getUserName(), finalUser.getPassword(), String.valueOf(finalUser.getRole()));

        return updatedRow == 1 ? "USER UPDATED SUCCESSFULLY" : "ERROR UPDATING USER";
    }

    public Optional<List<UserModel>> findUsersByTask(Integer taskId) {
        return this.userRepository.findUsersByTask(taskId);
    }

    // TODO: Assign Project Tasks to Users only if the user is a manager
    public String assignTask(Integer managerId, Integer userId, Integer taskId) {
         UserRole userRole = this.getUserById(managerId).get().getRole();

         if (userRole == MEMBER)
             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Managers can assign tasks");

        // TODO: Return HTTP response based on the result of the operation

        Integer assignedTaskRow = this.userRepository.assignTask(userId, taskId);
        return assignedTaskRow == 1 ? "TASK ASSIGNED SUCCESSFULLY" : "ERROR ASSIGNING TASK";
    }

    // Enable or disable a user - Recommended alternative to deleting a user
    public String softDelete(Integer id, IsObjectDeleted isDeleted) {
        UserModel user = userRepository.findById(id).orElseThrow(
            () -> new ObjectNotFoundException(id, "UserModel")
        );

        int deletedRows = this.userRepository.softDelete(id, String.valueOf(isDeleted));

        user.setIsDeleted(isDeleted);
        this.userRepository.save(user);

        return deletedRows == 1 ? "USER DELETED SUCCESSFULLY" : "ERROR DELETING USER";
    }
}
