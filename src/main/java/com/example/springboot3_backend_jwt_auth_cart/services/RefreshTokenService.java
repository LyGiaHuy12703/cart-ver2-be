package com.example.springboot3_backend_jwt_auth_cart.services;

import com.example.springboot3_backend_jwt_auth_cart.exception.AppException;
import com.example.springboot3_backend_jwt_auth_cart.exception.ErrorEnum;
import com.example.springboot3_backend_jwt_auth_cart.models.RefreshToken;
import com.example.springboot3_backend_jwt_auth_cart.models.User;
import com.example.springboot3_backend_jwt_auth_cart.repository.RefreshTokenRepository;
import com.example.springboot3_backend_jwt_auth_cart.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {
    @Value("${minhbui.app.jwtSecretRT}")
    private String jwtSecretRT;

    @Value("${minhbui.app.jwtExpireRT}")
    private long jwtExpireRT;

    @Autowired
    public JwtUtils jwtUtils;

    @Autowired
    public RefreshTokenRepository refreshTokenRepository;

    public String createToken(User user) {
        //1. Kiem tra da co rf chua
        RefreshToken foundRefreshToken = refreshTokenRepository.findByUser(user);
        String token = jwtUtils.generateJwtToken(user,jwtExpireRT,jwtSecretRT);
        if (foundRefreshToken == null) {
            RefreshToken refreshToken = new RefreshToken(
                    user,
                    token
            );
            refreshTokenRepository.save(refreshToken);
        }else {
            foundRefreshToken.setToken(token);
            refreshTokenRepository.save(foundRefreshToken);
        }
        return token;
    }
    //có thời hạn
    public String createToken(User user,Date expiration) {
        //1. Kiem tra da co rf chua
        RefreshToken foundRefreshToken = refreshTokenRepository.findByUser(user);
        String token = jwtUtils.generateJwtToken(user,expiration,jwtSecretRT);
        if (foundRefreshToken == null) {
            RefreshToken refreshToken = new RefreshToken(
                    user,
                    token
            );
            refreshTokenRepository.save(refreshToken);
        }else {
            foundRefreshToken.setToken(token);
            refreshTokenRepository.save(foundRefreshToken);
        }
        return token;
    }

    public RefreshToken findRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void deleteToken(User user) throws AppException {
        //2. found rf
        RefreshToken foundRefreshToken = refreshTokenRepository.findByUser(user);
        if(foundRefreshToken == null) {
            throw new AppException(ErrorEnum.USER_NOT_FOUND);
        }
        refreshTokenRepository.delete(foundRefreshToken);
    }


}
