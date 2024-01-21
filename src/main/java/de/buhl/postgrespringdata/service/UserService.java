package de.buhl.postgrespringdata.service;

import de.buhl.postgrespringdata.model.entity.AccountUser;

import de.buhl.postgrespringdata.model.dto.UserRequest;
import de.buhl.postgrespringdata.model.dto.UserResponse;
import de.buhl.postgrespringdata.repository.UserRepo;
import de.buhl.postgrespringdata.util.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepo userRepo;


    private final IdService idService;


    public List<AccountUser> getAllUser() {
        return userRepo.findAll();
    }



    public void createUser(UserRequest userRequest) {

        boolean doesUserExist = userRepo.existsByUsername(userRequest.userName());

        if (!doesUserExist) {
            AccountUser userToBeCreated = new AccountUser(
                    idService.randomId(),
                    userRequest.userName(),
                    userRequest.password(),
                    userRequest.userInfo(),
                    userRequest.taxInfo());
            userRepo.save(userToBeCreated);

        }
        else throw new IllegalArgumentException("UserName already exist");
    }

    public UserResponse updateUserInfo(String id,UserRequest userRequest) {
        Optional<AccountUser> isUser = userRepo.findById(id);
        if (isUser.isPresent()){
            AccountUser user = isUser.get();
            userRepo.save(new AccountUser(user.getId(), userRequest.userName(),
                    userRequest.password(), userRequest.userInfo(), userRequest.taxInfo()));
            return new UserResponse(userRequest.userName(),userRequest.userInfo(),userRequest.taxInfo());

        }
        else throw new NoSuchElementException("User doesnt exist with " + userRequest.userName());

    }

    public UserResponse getUser(String id) {
        Optional<AccountUser> user = userRepo.findById(id);
        if (user.isPresent()){
            AccountUser userEntity = user.get();
            return new UserResponse(userEntity.getUsername(),userEntity.getUserInfo(),userEntity.getTaxInfo());
        } else throw new NoSuchElementException("User does not Exist");
    }

    public void deleteUser(String id) {
        Optional<AccountUser> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()){
            userRepo.deleteById(userOptional.get().getId());
        } else {
            throw new NoSuchElementException("User does not Exist");
        }
    }


}
