package com.example.chatIslem.controllers;

import com.example.chatIslem.DTOs.request.UserDto;
import com.example.chatIslem.exceptions.EntityNotFoundException;
import com.example.chatIslem.models.user.ERole;
import com.example.chatIslem.models.user.UserModel;
import com.example.chatIslem.repositoies.RoleRepository;
import com.example.chatIslem.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @PostMapping("/add")
   public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        UserModel user = UserModel.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

          @GetMapping("/AllUser")
          public ResponseEntity<?> getAllUsers(){
              return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
            }
    @GetMapping("/AllUserEnabled")
    public ResponseEntity<?> getAllUsersEnabled(){
        return new ResponseEntity<>(userService.findAllUserEnabled(),HttpStatus.OK);
    }

        @GetMapping("/user/{id}")
        public ResponseEntity<?> getById(@PathVariable String id){
        return new ResponseEntity<>(userService.getUser(id),HttpStatus.OK);
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<?> deletUser(@PathVariable String id){
            UserModel user=userService.getUser(id);
            try {
                user.setEnabled(true);
                userService.saveUser(user);
            }catch (Exception ex){
                throw new EntityNotFoundException("user not found with id "+id);
            }
            return ResponseEntity.ok("user with id "+id+" is deleted !");
        }

    @GetMapping("/Erole")
    public ResponseEntity<?> getrole( ERole role){
        return new ResponseEntity<>(roleRepository.findByName(role),HttpStatus.OK);
    }



}
