package com.kdatalab.bridge.user.service;

import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.mapper.UserMapper;
import com.kdatalab.bridge.user.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

/**
 * 사용자 정보 Service
 * Security에 사용 하는 UserDetailService Interface
 * @author Enclouds
 * @since 2020.12.11
 */

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public static Map<String, String> GENERATED_CODES = new HashMap<>();

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * 사용자 정보 조회(로그인 정보)
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto params = new UserDto();
        params.setLoginId(username);
        UserDto userInfo = userMapper.selectAllUserInfo(params);
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(userInfo.getLoginId(), userInfo.getPassword(), true, true, true, !userInfo.getStatus().equals('Y'), authorities );
    }

    /**
     * 사용자 정보 조회
     *
     * @param username
     * @return
     * @throws Exception
     */
    public UserDto getUserInfo(String username) throws Exception{
        UserDto params = new UserDto();
        params.setLoginId(username);
        UserDto userInfo = (UserDto) userMapper.selectUserInfo(params);

        return userInfo;
    }
    /**
     * 사용자 정보 조회
     *
     * @param username
     * @return
     * @throws Exception
     */
    public UserDto getAllUserInfo(String username){
        UserDto params = new UserDto();
        params.setLoginId(username);
        return userMapper.selectAllUserInfo(params);
    }

    public HttpEntity<String> checkPassword(String inputPassword, String username) {
        if(inputPassword.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password incorrect");
        }
        UserDto params = new UserDto();
        params.setLoginId(username);
        UserDto userInfo = (UserDto) userMapper.selectAllUserInfo(params);
        if(passwordEncoder.matches(inputPassword, userInfo.getPassword())){
            String generateStringCode = generateStringCode(200);
            GENERATED_CODES.put(username, generateStringCode);
            return ResponseEntity.ok(generateStringCode);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password incorrect");
        }
    }

    /**
     * generate code with the given number length
     * @param count code length
     * @return generated code
     */
    private String generateStringCode(int count) {
        String string = "QUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVphYmNkZWZnaGlqa2xtbm9wcXJzdd4dd444b5c7665f0309cecc8901ce7c12175cebccb67f670d21c7d6dc9672425Oa05e1887ef3fb7";
        byte[] array = new byte[count];
        StringBuilder generatedString = new StringBuilder();
        new Random().nextBytes(array);
        for (int i = 0; i < array.length; i++) {
            if(array[i]<1){
                array[i] = (byte)Math.abs(array[i]+1);
            }
            generatedString.append(string.charAt(array[i]));
        }
        return generatedString.toString();
    }

    public String editUserInfo(String newPassword, String newEmail, String name, Model model) {
        if (!newPassword.isEmpty()) {
            userRepository.updateAccountInfo(name, passwordEncoder.encode(newPassword), newEmail.isEmpty()? null : newEmail);
        }else if(!newEmail.isEmpty()){
            userRepository.updateAccountInfo(name, null, newEmail);
        }
        model.addAttribute("success", true);
        return "mypage/myPage";
    }

    public boolean leaveTheMembership(String name) {
        userRepository.updateStatus(name, 'Y', name);
        return true;
    }

    public List<UserDto> getUsersByQcChk(char n) {
        return userMapper.selectUserByQcChk(n);
    }
}

