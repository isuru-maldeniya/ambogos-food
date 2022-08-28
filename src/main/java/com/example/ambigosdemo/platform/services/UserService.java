package com.example.ambigosdemo.platform.services;

import com.example.ambigosdemo.exceptions.types.UsernameNotAvailableException;
import com.example.ambigosdemo.platform.entities.UserRole;
import com.example.ambigosdemo.platform.models.UserRoles;
import com.example.ambigosdemo.platform.repositories.RoleRepository;
import com.example.ambigosdemo.platform.repositories.UserRepository;
import com.example.ambigosdemo.platform.entities.User;
import com.example.ambigosdemo.platform.models.UserInputModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(UserInputModel userInputModel){
        Optional<User> userOptional = userRepository.findByUsername(userInputModel.getUsername());
        if(userOptional.isPresent()){
            log.error("Username is not available"); // should throw an exception
            throw UsernameNotAvailableException.getUsernameNotAvailableException("username");
        }
        return userRepository.save(getUserFormUserInputModel(userInputModel));

    }

    public User getUserByUsername(String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            throw UsernameNotAvailableException.getUsernameNotAvailableException("Username");
        }
        return userOptional.get();
    }

    private User getUserFormUserInputModel(UserInputModel userInputModel){
        User user = new User();
        user.setUsername(userInputModel.getUsername());
        user.setEmail(userInputModel.getEmail());
        user.setPassword(passwordEncoder.encode(userInputModel.getPassword()));
        user.setEmail(userInputModel.getEmail());
        user.setRoles(getUserRolesList(userInputModel.getRoles()));
        return user;
    }
    private List<UserRole> getUserRolesList(List<com.example.ambigosdemo.platform.models.UserRoles> inputUserRoles){
        return inputUserRoles.stream().map(userRole -> {
            Optional<UserRole> roleOptional = roleRepository.getUserRoleByName(userRole.getName());
            if (roleOptional.isEmpty()) {
                // throw exception
            }
            return roleOptional.get();
        }).collect(Collectors.toList());
    }

    public void saveRole(UserRoles userRole){
        Optional<UserRole> userRoleOptional = roleRepository.getUserRoleByName(userRole.getName());
        if(userRoleOptional.isEmpty()){
            roleRepository.save(new UserRole(userRole.getName()));
        }else{
            log.warn("User role is already there");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usernameOptional = userRepository.findByUsername(username);
        if(usernameOptional.isEmpty()){
            throw UsernameNotAvailableException.getUsernameNotAvailableException("username");
        }
        User user = usernameOptional.get();
        List<GrantedAuthority> grantAuthorities = user.getRoles().stream().map(userRole -> new SimpleGrantedAuthority(userRole.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantAuthorities);
    }
}
