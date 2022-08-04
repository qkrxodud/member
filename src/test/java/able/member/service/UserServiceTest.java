package able.member.service;

import able.member.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void 멤버추가() {
//        User user = User.CreateUser("test", "123124","콩코득여객기", "박태영", "qkrxodyud00@gmail.com");
//        userService.join(user);


    }

}