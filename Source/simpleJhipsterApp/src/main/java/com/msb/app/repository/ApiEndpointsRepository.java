package com.msb.app.repository;

import com.msb.app.domain.ApiEndpoints;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ApiEndpoints entity.
 */
@SuppressWarnings("unused")
public interface ApiEndpointsRepository extends MongoRepository<ApiEndpoints,String> {

}
