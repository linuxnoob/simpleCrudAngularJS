package com.example.test.simpleCrudProject.Controller;

import com.example.test.simpleCrudProject.Entity.User;
import com.example.test.simpleCrudProject.Service.UserService;
import com.example.test.simpleCrudProject.Validation.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin",origins =  {"http://localhost:4200"})
public class UserController {

    public static final Logger logger =  LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @RequestMapping(path = "/getAllUser", method = RequestMethod.GET,  produces = { MediaType.APPLICATION_JSON_VALUE, //
            MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<User> findAllUser() {
        logger.info("Fetching all User {}");
        return this.userService.getAllUser();

    }

    @RequestMapping(path = "/saveUser", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, //
            MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public ResponseEntity<String> saveUser(@RequestBody User user) throws Exception{

        try {

            boolean success =this.userService.saveUserData(user);
            if(success) {
                return new ResponseEntity<String>(HttpStatus.OK);
            }else
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            throw  new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(path = "/updateUser", method = RequestMethod.PUT,produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public ResponseEntity<String> updateUser(@RequestBody User user) throws Exception{
        try {
            boolean success =this.userService.updateUserData(user);
            if(success){
                return new ResponseEntity<String>(HttpStatus.OK);
            }else
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            System.out.println("msg " + e.getMessage());
            throw  new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(path = "/deleteUser", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public ResponseEntity<String> deleteUser(@RequestParam("userId")  Integer userId) throws Exception{
        boolean result = false;
        try {
            result=this.userService.deleteUserData(userId);
            if(!result){
                return new ResponseEntity<String>( HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            throw  new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public ResponseEntity<?> getUser(@PathVariable("id") int id) throws RemoteException {
        logger.info("Fetching User with id {}", id);
        User user = userService.findById(id);
        if (user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity("User not found ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}
