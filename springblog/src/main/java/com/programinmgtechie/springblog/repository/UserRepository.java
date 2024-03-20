package com.programinmgtechie.springblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programinmgtechie.springblog.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	

}
