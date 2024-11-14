package com.example.springboot3_backend_jwt_auth_cart;

import com.example.springboot3_backend_jwt_auth_cart.models.CartStatusEnum;
import com.example.springboot3_backend_jwt_auth_cart.models.ERole;
import com.example.springboot3_backend_jwt_auth_cart.models.Postatus;
import com.example.springboot3_backend_jwt_auth_cart.models.Role;
import com.example.springboot3_backend_jwt_auth_cart.repository.PostatusRepository;
import com.example.springboot3_backend_jwt_auth_cart.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Springboot3BackendJwtAuthCartApplication {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PostatusRepository postatusRepository;

	public static void main(String[] args) {
		SpringApplication.run(Springboot3BackendJwtAuthCartApplication.class, args);
	}

	@Bean
	public ApplicationRunner init() {
		//Khoi tao 3 role
		return args -> {
			if(roleRepository.findByName(ERole.ROLE_ADMIN) == null) {
				roleRepository.save(new Role(ERole.ROLE_ADMIN));
			}
			if(roleRepository.findByName(ERole.ROLE_USER) == null) {
				roleRepository.save(new Role(ERole.ROLE_USER));
			}

			if(roleRepository.findByName(ERole.ROLE_MODERATOR) == null) {
				roleRepository.save(new Role(ERole.ROLE_MODERATOR));
			}
			//Khoi tao 2 postatus
			if(postatusRepository.findByStatus(CartStatusEnum.CART_PENDING) == null) {
				postatusRepository.save(new Postatus("Giỏ hàng chưa thanh toán",CartStatusEnum.CART_PENDING));
			}
			if(postatusRepository.findByStatus(CartStatusEnum.CART_CHECKOUT) == null) {
				postatusRepository.save(new Postatus("Giỏ hàng đã thanh toán",CartStatusEnum.CART_CHECKOUT));
			}
		};
	}
}
