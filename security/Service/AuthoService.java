package com.example.security.Service;

import com.example.security.ApiResponse.APIException;
import com.example.security.Model.User;
import com.example.security.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthoService {


    private final AuthRepository authRepository;

    public void register(User user){
        user.setRole("CUSTOMER");
        String hash_password=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash_password);//never save password without decrypt
        authRepository.save(user);

    }

public List<User> getAll(){
        if (authRepository.findAll().isEmpty())
            throw  new APIException("empty list");
        else return authRepository.findAll();
}

//    public List<User> getAll(String usernameAdmin){
//        User admin=authRepository.findUserByUsername(usernameAdmin);
//        if (admin==null){throw  new APIException("Wrong request");
//        }
//
//        if (admin.getRole().equalsIgnoreCase("ADMIN")) {
//            return authRepository.findAll();
//        }
//        else {
//            throw  new APIException("GET ALL NOT ALLOW FOR YOU");
//        }
//    }

public void delete(String usernameAdmin,String username){
        User admin=authRepository.findUserByUsername(usernameAdmin);
        User user=authRepository.findUserByUsername(username);
        if (admin==null){throw  new APIException("DELETION NOT ALLOW FOR YOU");
        }
        if (admin.getRole().equalsIgnoreCase("ADMIN")){
        authRepository.delete(user);
        }
    }

    public void update(String username,User usernew){
        User user=authRepository.findUserByUsername(username);
        if (user==null){throw  new APIException("USER NOT Found");
        }
       user.setPassword(usernew.getPassword());
        user.setUsername(usernew.getUsername());
        user.setRole(usernew.getRole());
        authRepository.save(user);

    }

    public User login(String username,String password){
        User user=authRepository.findUserByUsername(username);

        if (user==null){throw  new APIException("Please register first");}


        else return user;
    }
}
