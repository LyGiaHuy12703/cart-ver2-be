package com.example.springboot3_backend_jwt_auth_cart.services;

import com.example.springboot3_backend_jwt_auth_cart.dto.request.RefreshTokenRequest;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.SignInRequest;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.SignUpRequest;
import com.example.springboot3_backend_jwt_auth_cart.dto.response.RefreshTokenResponse;
import com.example.springboot3_backend_jwt_auth_cart.dto.response.SignInResponse;
import com.example.springboot3_backend_jwt_auth_cart.exception.AppException;
import com.example.springboot3_backend_jwt_auth_cart.exception.ErrorEnum;
import com.example.springboot3_backend_jwt_auth_cart.models.ERole;
import com.example.springboot3_backend_jwt_auth_cart.models.RefreshToken;
import com.example.springboot3_backend_jwt_auth_cart.models.Role;
import com.example.springboot3_backend_jwt_auth_cart.models.User;
import com.example.springboot3_backend_jwt_auth_cart.repository.RoleRepository;
import com.example.springboot3_backend_jwt_auth_cart.repository.UserRepository;
import com.example.springboot3_backend_jwt_auth_cart.security.jwt.JwtUtils;
import com.example.springboot3_backend_jwt_auth_cart.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Value("${minhbui.app.jwtSecretAT}")
    private String jwtSecretAT;

    @Value("${minhbui.app.jwtExpireAT}")
    private long jwtExpireAT;

    @Value("${minhbui.app.jwtSecretRT}")
    private String jwtSecretRT;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public String signup(SignUpRequest request) throws AppException {
        //1. Kiem tra username & email da dang ky
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorEnum.EMAIL_EXIST);
        }
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AppException(ErrorEnum.USERNAME_EXIST);
        }

        //2. Tao user
        var passwordEncoder = encoder.encode(request.getPassword());

        User user = new User(request.getUsername(), request.getEmail(),passwordEncoder);

        if(request.getRole().isEmpty()) {
            user.addRole(roleRepository.findByName(ERole.ROLE_USER));
        }else {
            for(String roleName: request.getRole()) {
                Role role = roleRepository.findByName(ERole.valueOf(roleName));
                if(role != null) {
                    user.addRole(role);
                }
            }
        }

        userRepository.save(user);
        return "Registered successfully";
    }


    public SignInResponse signIn(SignInRequest request) throws AppException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userDetails.getUser();
        String accessToken = jwtUtils.generateJwtToken(user,jwtExpireAT,jwtSecretAT);

        String refreshToken = refreshTokenService.createToken(user);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return new SignInResponse(accessToken,refreshToken,user.getId(),user.getUsername(),user.getEmail(),roles);
    }


    public void signOut() throws AppException {
        UserDetailsImpl userDetail = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //1. xoa trong rf token
        refreshTokenService.deleteToken(userDetail.getUser());

    }

    public RefreshTokenResponse refreshTokenService(RefreshTokenRequest refreshTokenRequest) throws AppException {
        //1. Kiem tra token
        try{
            var jwt = Jwts.parser().setSigningKey(jwtSecretRT).build().parseClaimsJws(refreshTokenRequest.getRefreshToken());
            var expirationRT = jwt.getPayload().getExpiration();
            RefreshToken foundRT = refreshTokenService.findRefreshToken(refreshTokenRequest.getRefreshToken());
            if(foundRT == null) {
                throw new AppException(ErrorEnum.RT_INVALID_TOKEN);
            }

            UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String accessToken = jwtUtils.generateJwtToken(userDetails.getUser(),jwtExpireAT,jwtSecretAT);

            String refreshToken = refreshTokenService.createToken(userDetails.getUser(),expirationRT);

            return new RefreshTokenResponse(accessToken,refreshToken);

        }catch (ExpiredJwtException e) {
            throw new AppException(ErrorEnum.RT_TOKEN_EXPIRED);
        }catch (SignatureException e) {
            throw new AppException(ErrorEnum.RT_INVALID_TOKEN);
        }


    }

}
