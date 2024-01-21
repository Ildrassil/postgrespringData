package de.buhl.postgrespringdata;

import de.buhl.postgrespringdata.model.dto.UserRequest;
import de.buhl.postgrespringdata.model.dto.UserResponse;
import de.buhl.postgrespringdata.model.entity.AccountUser;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import de.buhl.postgrespringdata.repository.UserRepo;
import de.buhl.postgrespringdata.service.UserService;
import de.buhl.postgrespringdata.util.IdService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserServiceUnitTest {

    private UserRepo userRepo = mock(UserRepo.class);

    private IdService idService = mock(IdService.class);

    private UserService userService = new UserService(userRepo, idService);

    @Test
    void getAllUsersTest() {
        //GIVEN
        AccountUser user1 = new AccountUser("1", "test","1234" ,new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);
        AccountUser user2 = new AccountUser("2", "test1","1234" ,new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);

        //WHEN
        when(userRepo.findAll()).thenReturn(List.of(user1, user2));
        List<AccountUser> actual = userService.getAllUsers();

        //THEN
        List<AccountUser> expected = List.of(user1, user2);
        assertEquals(actual, expected);
    }

    @Test
    void getUserByIdTest() {
        //GIVEN
        AccountUser user1 = new AccountUser("1", "test","1234" ,new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);
        UserRequest userRequest = new UserRequest( "test","1234" ,new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);
        //WHEN
        when(userRepo.findById("1")).thenReturn(java.util.Optional.of(user1));
        UserResponse actual = userService.getUser("1", userRequest);
        //THEN
        UserResponse expected = new UserResponse("test" ,new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);
        assertEquals(actual, expected);
    }

    @Test
    void createUserTest() {
        //GIVEN
        UserRequest userRequest = new UserRequest("test", "1234", new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);
        AccountUser user1 = new AccountUser("1", "test", "1234", new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);
        //WHEN
        when(userRepo.findById("1")).thenReturn(java.util.Optional.of(user1));
        when(userRepo.save(user1)).thenReturn(user1);
        userService.createUser(userRequest);
        AccountUser actual = userRepo.findById("1").get();
        //THEN
        AccountUser expected = new AccountUser("1", "test", "1234", new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);
        assertEquals(actual, expected);
    }

    @Test
    void updateUserTest() {
        //GIVEN
        UserRequest userRequest = new UserRequest("test", "1234", new UserInfo("2", "1", "1", "1", "1", "1", "1"), null);
        AccountUser user1 = new AccountUser("1", "test", "1234", new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);
        AccountUser userUpdate = new AccountUser("1", "test", "1234", new UserInfo("2", "1", "1", "1", "1", "1", "1"), null);
        //WHEN
        when(userRepo.findById("1")).thenReturn(java.util.Optional.of(user1));
        when(userRepo.save(userUpdate)).thenReturn(userUpdate);

        UserResponse actual = userService.updateUserInfo("1", userRequest);
        //THEN
        UserResponse expected = new UserResponse("test", new UserInfo("2", "1", "1", "1", "1", "1", "1"), null);
        assertEquals(actual, expected);
    }

    @Test
    void deleteUserTest() {
        //GIVEN
        AccountUser user1 = new AccountUser("1", "test", "1234", new UserInfo("1", "1", "1", "1", "1", "1", "1"), null);
        //WHEN
        when(userRepo.findById("1")).thenReturn(Optional.of(user1));
        when(userRepo.save(user1)).thenReturn(user1);
        userRepo.save(user1);
        userService.deleteUser("1");

        Optional<AccountUser> actual = Optional.empty();
        //THEN
        Optional<AccountUser> expected = Optional.empty();
        assertEquals(actual, expected);
    }





}
