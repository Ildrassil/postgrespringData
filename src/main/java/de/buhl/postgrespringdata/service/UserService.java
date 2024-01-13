package de.buhl.postgrespringdata.service;

import de.buhl.postgrespringdata.dto.User;
import de.buhl.postgrespringdata.dto.UserRequest;
import de.buhl.postgrespringdata.dto.UserResponse;
import de.buhl.postgrespringdata.repo.UserRepo;
import de.buhl.postgrespringdata.util.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepo userRepo;

    private final IdService idService;

    List<User> getAllUser() throws RuntimeException{
        return userRepo.findAll();
    }

    User getUserByUserName( String userName) throws NoSuchElementException{
        List<User> userList = getAllUser();
        return userList.stream()
                .filter(user -> user.username().equals(userName))
                .collect(Collectors.toList())
                .get(0);
    }



    void createUser(UserRequest userRequest) throws IllegalArgumentException {

        List<User> userList = getAllUser();
        Optional<User> user = Optional.of(userList.stream().filter(user1 -> user1.username().equals(userRequest.userName())).collect(Collectors.toList()).get(0));
        if (user.isEmpty()) {
            User user1 = new User(idService.randomId(), userRequest.userName(),
                    userRequest.password(), userRequest.userInfo(), userRequest.steuerInfo());
            userRepo.save(user1);

        }
        else throw new IllegalArgumentException("UserName already exist");
    }

    UserResponse updateUserInfo(String id,UserRequest userRequest) throws NoSuchElementException{
        Optional<User> isUser = userRepo.findById(id);
        if (isUser.isPresent()){
            User user = isUser.get();
            userRepo.deleteById(user.id());
            userRepo.save(new User(user.id(), userRequest.userName(),
                    userRequest.password(), userRequest.userInfo(), userRequest.steuerInfo()));
            return new UserResponse(userRequest.userName(),userRequest.userInfo(),userRequest.steuerInfo());

        }
        else throw new NoSuchElementException("User doesnt exist with " + userRequest.userName());

    }

    UserResponse getUser(String id) throws NoSuchElementException{
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()){
            User userEntity = user.get();
            return new UserResponse(userEntity.username(),userEntity.userInfo(),userEntity.steuerInfo());
        } else throw new NoSuchElementException("User does not Exist: " + user.get().username());
    }

    void deleteUser(String id) throws NoSuchElementException{
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()){
            userRepo.deleteById(userOptional.get().id());
        } else {
            throw new NoSuchElementException("User does not Exist: " + userOptional.get().username());
        }
    }


}
