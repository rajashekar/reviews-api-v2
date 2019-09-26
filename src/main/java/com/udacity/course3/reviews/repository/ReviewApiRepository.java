package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Reviews;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewApiRepository extends MongoRepository<Reviews, Integer> {

}