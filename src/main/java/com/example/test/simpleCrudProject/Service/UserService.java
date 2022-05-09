package com.example.test.simpleCrudProject.Service;

import com.example.test.simpleCrudProject.Entity.User;
import com.example.test.simpleCrudProject.Repo.UserRepo;
import com.example.test.simpleCrudProject.Validation.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.rmi.RemoteException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {


    @Autowired
    UserRepo userRepo;

    public boolean saveUserData(User user) throws RemoteException {

        try {
            if(checkUserValidation(user)) {
                this.userRepo.save(user);
                return true;
            }else {
                throw new CustomException(" data is not valid", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RemoteException("Exception "+ e.getMessage());
        }

    }

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public boolean updateUserData(User user) throws RemoteException{
        try {
            User userData = this.userRepo.findById(user.getId()).orElse(null);
            if(userData!=null && checkUserValidation(user)){
                this.userRepo.save(user);
                return true;
            }else {
                throw new CustomException(" data is not valid", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RemoteException("Exception " + e.getMessage());
        }
    }

    public boolean deleteUserData(Integer userId) throws RemoteException{
        try {
            User userData = this.userRepo.findById(userId).orElse(null);
            if(userData!=null){
                this.userRepo.deleteById(userData.getId());
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    boolean checkUserValidation(User user){
        String regexEmail = "^(.+)@(.+)$";
        String regexNumeric ="^\\d{10,15}$";
        Pattern patternEmail = Pattern.compile(regexEmail);
        Matcher matcherEmail = patternEmail.matcher(user.getEmail());
        Pattern patternNumeric = Pattern.compile(regexNumeric);
        Matcher matcherNumeric = patternNumeric.matcher(user.getNumber());
        if(user.getName().length()>50){
            throw new CustomException("name length greater than 50 char", HttpStatus.BAD_REQUEST);
        }else if(!matcherNumeric.matches()){
            throw new CustomException("number format is not valid", HttpStatus.BAD_REQUEST);
        }else if (!matcherEmail.matches()){
            throw new CustomException("email format is not correct " , HttpStatus.BAD_REQUEST);
        }else if (user.getAddress().isEmpty()){
            throw new CustomException("address can't be empty", HttpStatus.BAD_REQUEST);
        }
        return true;
    }

    public User findById(int id) throws RemoteException {

        return this.userRepo.findById(id).orElse(null);

    }
}
