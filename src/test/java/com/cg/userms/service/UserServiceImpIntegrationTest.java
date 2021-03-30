package com.cg.userms.service;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.cg.userms.entity.User;
import com.cg.userms.exception.InvalidIdException;
import com.cg.userms.exception.InvalidUsernameException;
import com.cg.userms.exception.UserNotFoundException;
import com.cg.userms.repository.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@Import(UserServiceImpl.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class UserServiceImpIntegrationTest
{
	

	 /**
     * Scenario: negative userid 
     */
    @Autowired
    UserServiceImpl userService;
    @Autowired
    EntityManager entityManager;
    
    /**
     * Scenario: negative userid 
     */
    
    @Test
    public void testFindById_1()
    {
    	long userid=-1l;
        Executable executable = ()->userService.findById(userid);
        Assertions.assertThrows(InvalidIdException.class,executable);
    }


    
    /**
     * Scenario: userid exist in database
     */
   
    
    @Test
    public void testFindById_2()
    {
  User user=new User("shipra","1");
  entityManager.persist(user);
  Long assignedUserId=user.getUserId();
  User result=userService.findById(assignedUserId);
  Assertions.assertNotNull(result);
  Assertions.assertEquals(assignedUserId,result.getUserId());
  Assertions.assertEquals(result,1);
   }
    
    
    /**
     * Scenario: userid does not exist in the database
     */
    @Test
    public void testFindById_3()
    {
    long  userid=(Long) null;
       Executable executable=()->userService.findById(userid);
       Assertions.assertThrows(UserNotFoundException.class,executable);
   }
    
    
//   
//    /**
//     * Scenario: userid exist in database
//     */
//    @Test
//    public void testFindById_4()
//    {
//    long  userid=1;
//    User user = new User();
//   
//    entityManager.persist(userid);
//    boolean result = userService.findById(userid) != null;
//    Assertions.assertTrue(result);
//    }

 
}