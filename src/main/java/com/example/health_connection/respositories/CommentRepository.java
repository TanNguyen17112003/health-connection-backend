package com.example.health_connection.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.health_connection.models.Comment;
import com.example.health_connection.models.Exercise;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByExercise(Exercise exercise);   
} 
