package aor.paj.proj3_vc_re_jc.bean;


import aor.paj.proj3_vc_re_jc.dao.TokenDao;
import aor.paj.proj3_vc_re_jc.dao.UserDao;
import aor.paj.proj3_vc_re_jc.dto.*;
import aor.paj.proj3_vc_re_jc.entity.TokenEntity;
import aor.paj.proj3_vc_re_jc.entity.UserEntity;
import aor.paj.proj3_vc_re_jc.enums.UserRole;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.hibernate.annotations.Check;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Stateless
public class UserBean implements Serializable {

    @EJB
    UserDao userDao;

    @EJB
    TokenDao tokenDao;

    int tokenTimer = 12000;

    public String login(String username, String password) {
        UserEntity userEntity = userDao.findUserByUsername(username);
        if (userEntity != null && !userEntity.isDeleted()) {
            if (userEntity.getPassword().equals(password)) {
                String token = generateNewToken();
                userEntity.setTokenId(token);
                TokenEntity t = new TokenEntity();
                t.setId(token);
                t.setDeleted(false);
                t.setTokenExpiration(Instant.now().plus(tokenTimer, ChronoUnit.SECONDS));
                tokenDao.persist(t);
                return token;
            }
        }
        return null;
    }


    public boolean register(UserDto user) {
        UserEntity u = userDao.findUserByUsername(user.getUsername());
        if (u == null) {
            userDao.persist(convertUserDtotoUserEntity(user));
            return true;
        } else
            return false;
    }

    private UserEntity convertUserDtotoUserEntity(UserDto user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhone(user.getPhone());
        userEntity.setPhotoURL(user.getPhotoURL());
        userEntity.setTokenId(user.getToken());
        userEntity.setDeleted(user.isDeleted());
        userEntity.setRole(user.getRole());
        return userEntity;
    }

    private UserDto convertUserEntitytoUserDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setPhotoURL(user.getPhotoURL());
        userDto.setToken(user.getTokenId());
        userDto.setDeleted(user.isDeleted());
        userDto.setRole(user.getRole());
        return userDto;
    }

    private String generateNewToken() {
        SecureRandom secureRandom = new SecureRandom(); //threadsafe
        Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public boolean logout(String token) {
        UserEntity u = userDao.findUserByToken(token);
        TokenEntity t = tokenDao.findTokenById(token);
        if (u != null) {
            u.setTokenId(null);
            t.setDeleted(true);
            //  tokenDao.remove(t);
            return true;
        }
        System.out.println("HERE");
        System.out.println(token);
        return false;
    }



    public boolean tokenExist(String token) {
        UserEntity u = userDao.findUserByToken(token);
        TokenEntity t = tokenDao.findTokenById(token);
        if (u != null && isTokenValid(t))
            return true;
        else {
            return false;
        }
    }

    public void updateProfile(EditDto user, String token) {
        UserEntity u = userDao.findUserByToken(token);
        TokenEntity t = tokenDao.findTokenById(token);
        if (u != null) {
            u.setPhotoURL(user.getPhotoURL());
            u.setPhone(user.getPhone());
            u.setPassword(user.getPassword());
            u.setLastName(user.getLastName());
            u.setFirstName(user.getFirstName());
            u.setEmail(user.getEmail());
            t.setTokenExpiration(Instant.now().plus(tokenTimer, ChronoUnit.SECONDS));
            userDao.persist(u);
        }
    }

    public void updateRole(RoleDto user, String token) {
        UserEntity u = userDao.findUserByUsername(user.getUsername());
        TokenEntity t = tokenDao.findTokenById(token);
        if (u != null) {
            u.setRole(user.getRole());
            t.setTokenExpiration(Instant.now().plus(tokenTimer, ChronoUnit.SECONDS));
            userDao.persist(u);
        }
    }



    private boolean isTokenValid(TokenEntity t) {
        Instant now = Instant.now();
        Instant expiration = t.getTokenExpiration();
        if (expiration.isAfter(now)) {
            return true;
        }
        return false;
    }

    public CheckProfileDto checkProfile (String username, String token) {
        UserEntity u = userDao.findUserByUsername(username);
        TokenEntity t = tokenDao.findTokenById(token);
        CheckProfileDto checkU = new CheckProfileDto();
        checkU.setUsername(u.getUsername());
        checkU.setFirstName(u.getFirstName());
        checkU.setLastName(u.getLastName());
        checkU.setEmail(u.getEmail());
        checkU.setPhone(u.getPhone());
        checkU.setRole(u.getRole().getValue());
        checkU.setPhotoURL(u.getPhotoURL());
        t.setTokenExpiration(Instant.now().plus(tokenTimer, ChronoUnit.SECONDS));
        return checkU;
    }

    public boolean createUser (String token, UserDto user) {
        UserEntity u = userDao.findUserByUsername(user.getUsername());
        TokenEntity t = tokenDao.findTokenById(token);
        if (u == null) {
            userDao.persist(convertUserDtotoUserEntity(user));
            t.setTokenExpiration(Instant.now().plus(tokenTimer, ChronoUnit.SECONDS));
            return true;
        } else
            return false;
    }

    public ArrayList<CheckProfileDto> checkAll (String token) {
        TokenEntity t = tokenDao.findTokenById(token);
        List<UserEntity> userList = userDao.allUsers();

        ArrayList<CheckProfileDto> dtos = new ArrayList<>();

        for (UserEntity u : userList) {
            CheckProfileDto checkU = new CheckProfileDto();
            checkU.setUsername(u.getUsername());
            checkU.setFirstName(u.getFirstName());
            checkU.setLastName(u.getLastName());
            checkU.setEmail(u.getEmail());
            checkU.setPhone(u.getPhone());
            checkU.setRole(u.getRole().getValue());
            checkU.setPhotoURL(u.getPhotoURL());
            checkU.setDeleted(u.isDeleted());
            dtos.add(checkU);
        }
        t.setTokenExpiration(Instant.now().plus(tokenTimer, ChronoUnit.SECONDS));
        return dtos;
    }

    public void deleteUser (String username, String token) {
        TokenEntity t = tokenDao.findTokenById(token);
        UserEntity u = userDao.findUserByUsername(username);
        if (u != null) {
            u.setDeleted(true);
            t.setTokenExpiration(Instant.now().plus(tokenTimer, ChronoUnit.SECONDS));
            userDao.persist(u);

        }
    }
}





