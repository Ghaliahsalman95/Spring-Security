package com.example.security.Controller;

import com.example.security.ApiResponse.APIResponse;
import com.example.security.Model.User;
import com.example.security.Service.AuthoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController@RequestMapping("/api/v1/auth")
public class AuthController {

   private final AuthoService authoService;
@PostMapping("/register")
   public ResponseEntity register(@RequestBody @Valid User user){
       authoService.register(user);
       return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Registered Successfully"));

   }

   @GetMapping("/logout")
public ResponseEntity logout(){
    return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Log out successfully"));

}
@GetMapping("/get-all/{admin}")
public  ResponseEntity getAll(@PathVariable String admin){
    return ResponseEntity.status(200).body(authoService.getAll(admin));
}///delete/{admin}/{username}   /get-all/{admin}
@DeleteMapping("/delete/{admin}/{username}")
public ResponseEntity delete(@PathVariable String admin, @PathVariable String username){
    authoService.delete(admin,username);
    return ResponseEntity.status(200).body(new APIResponse("Delete successfully"));
}
@PutMapping("/update/{username}")
public ResponseEntity update(@PathVariable String username, @RequestBody @Valid User user)
{authoService.update(username,user);
    return ResponseEntity.status(200).body(new APIResponse("updated successfully"));
}

@GetMapping("/login/{username}/{password}")
    public ResponseEntity login(@PathVariable String username,@PathVariable String password){
    return ResponseEntity.status(200).body(authoService.login(username,password));
}
}
