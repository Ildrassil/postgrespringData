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

    // Hier wird eine Liste aller User aus der Datenbank geholt.
    // Diese Methode soll nur von einem Admin ausgeführt werden können.
    // Diese müsste dementsprechend Verschlüsselt werden.
    public List<AccountUser> getAllUsers() {
        return userRepo.findAll();
    }

    // Damit ein User erstellt werden kann, wird überprüft ob der Username
    // bereits existiert. Wenn nicht, wird der User erstellt.
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

        } else throw new IllegalArgumentException("UserName already exist");
    }
    //Um die daten zu aktualisieren, wird der User aus der Datenbank geholt
    // und mit den neuen Daten gespeichert.
    // Das Passwort wir hier auch überprüft, da es sonst möglich wäre, das
    // ein User das Passwort eines anderen Users ändern könnte.
    // Spring Security bietet hier eine gute Lösung, mit der ich allerdings
    // noch nicht vertraut bin.
    // Dies ist nur eine Lösung um die Funktionalität zu zeigen. Dieser simple
    // Passwort validator kommt auch in der getUser Methode vor.
    public UserResponse updateUserInfo(String id, UserRequest userRequest) {
        Optional<AccountUser> isUser = userRepo.findById(id);
        boolean password = isUser.get().getPassword().matches(userRequest.password());
        if (isUser.isPresent()) {
            if (password) {
                AccountUser user = isUser.get();
                userRepo.save(new AccountUser(user.getId(), userRequest.userName(),
                        userRequest.password(), userRequest.userInfo(), userRequest.taxInfo()));
                return new UserResponse(userRequest.userName(), userRequest.userInfo(), userRequest.taxInfo());

            } else throw new IllegalArgumentException("Password is wrong");
        } else throw new NoSuchElementException("User doesnt exist with " + userRequest.userName());

    }

    // Hier würde eine Abfrage auf die Datenbank erfolgen, ob der User existiert
    // und ob das Passwort stimmt.
    public UserResponse getUser(String id, UserRequest userRequest) {
        Optional<AccountUser> user = userRepo.findById(id);
        boolean password = user.get().getPassword().matches(userRequest.password());
        if (user.isPresent()) {
            if (password) {
                AccountUser userEntity = user.get();
                return new UserResponse(userEntity.getUsername(), userEntity.getUserInfo(), userEntity.getTaxInfo());
            } else throw new IllegalArgumentException("Password is wrong");
        } else throw new NoSuchElementException("User does not Exist");
    }


    // Hier wird der User aus der Datenbank geholt und gelöscht.
    // Wenn der User nicht existiert, wird eine Exception geworfen.
    // Dies soll eine reine Admin funktion sein und nicht von jedem User
    // ausgeführt werden können. Die Verschlüsselung dafür ist noch nicht
    // in meinem Wissen vorhanden.
    public void deleteUser(String id) {
        Optional<AccountUser> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            userRepo.deleteById(userOptional.get().getId());
        } else {
            throw new NoSuchElementException("User does not Exist");
        }
    }


}
