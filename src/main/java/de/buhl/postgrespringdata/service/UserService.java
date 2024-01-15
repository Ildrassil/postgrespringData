package de.buhl.postgrespringdata.service;

import de.buhl.postgrespringdata.model.entity.User;
import de.buhl.postgrespringdata.model.dto.UserRequest;
import de.buhl.postgrespringdata.model.dto.UserResponse;
import de.buhl.postgrespringdata.repository.UserRepo;
import de.buhl.postgrespringdata.util.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepo userRepo;


    private final IdService idService;


    public List<User> getAllUser() {
        return userRepo.findAll();
    }



    public void createUser(UserRequest userRequest) {

        boolean doesUserExist = userRepo.existsByUsername(userRequest.userName());

        if (!doesUserExist) {
            User userToBeCreated = new User(idService.randomId(),userRequest.userName(),
                    userRequest.password(),
                    userRequest.userInfo(),
                    userRequest.steuerInfo());
            userRepo.save(userToBeCreated);

        }
        else throw new IllegalArgumentException("UserName already exist");
    }

    public UserResponse updateUserInfo(String id,UserRequest userRequest) {
        Optional<User> isUser = userRepo.findById(id);
        if (isUser.isPresent()){
            User user = isUser.get();
            userRepo.save(new User(user.id(), userRequest.userName(),
                    userRequest.password(), userRequest.userInfo(), userRequest.steuerInfo()));
            return new UserResponse(userRequest.userName(),userRequest.userInfo(),userRequest.steuerInfo());

        }
        else throw new NoSuchElementException("User doesnt exist with " + userRequest.userName());

    }

    public UserResponse getUser(String id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()){
            User userEntity = user.get();
            return new UserResponse(userEntity.username(),userEntity.userInfo(),userEntity.steuerInfo());
        } else throw new NoSuchElementException("User does not Exist: " + user.get().username());
    }

    public void deleteUser(String id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()){
            userRepo.deleteById(userOptional.get().id());
        } else {
            throw new NoSuchElementException("User does not Exist: " + userOptional.get().username());
        }
    }


}
