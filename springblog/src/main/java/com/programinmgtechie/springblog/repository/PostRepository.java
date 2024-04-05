package com.programinmgtechie.springblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programinmgtechie.springblog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
