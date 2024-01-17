package de.buhl.postgrespringdata.service;

import de.buhl.postgrespringdata.model.entity.Nutzer;

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


    public List<Nutzer> getAllUser() {
        return userRepo.findAll();
    }



    public void createUser(UserRequest userRequest) {

        boolean doesUserExist = userRepo.existsByUsername(userRequest.userName());

        if (!doesUserExist) {
            Nutzer userToBeCreated = new Nutzer(idService.randomId(), userRequest.userName(),
                    userRequest.password(),
                    userRequest.userInfo(),
                    userRequest.steuerInfo());
            userRepo.save(userToBeCreated);

        }
        else throw new IllegalArgumentException("UserName already exist");
    }

    public UserResponse updateUserInfo(String id,UserRequest userRequest) {
        Optional<Nutzer> isUser = userRepo.findById(id);
        if (isUser.isPresent()){
            Nutzer user = isUser.get();
            userRepo.save(new Nutzer(user.getId(), userRequest.userName(),
                    userRequest.password(), userRequest.userInfo(), userRequest.steuerInfo()));
            return new UserResponse(userRequest.userName(),userRequest.userInfo(),userRequest.steuerInfo());

        }
        else throw new NoSuchElementException("User doesnt exist with " + userRequest.userName());

    }

    public UserResponse getUser(String id) {
        Optional<Nutzer> user = userRepo.findById(id);
        if (user.isPresent()){
            Nutzer userEntity = user.get();
            return new UserResponse(userEntity.getUsername(),userEntity.getUserInfo(),userEntity.getSteuerInfo());
        } else throw new NoSuchElementException("User does not Exist");
    }

    public void deleteUser(String id) {
        Optional<Nutzer> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()){
            userRepo.deleteById(userOptional.get().getId());
        } else {
            throw new NoSuchElementException("User does not Exist");
        }
    }


}
