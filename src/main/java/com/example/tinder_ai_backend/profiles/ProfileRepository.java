package com.example.tinder_ai_backend.profiles;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile , String> {

    //we use aggregation query to get a random profile from mongoDB and it looks like
    @Aggregation(pipeline = {"{ $sample: {size:1 } }"}) //this means get me a random sampling / document of size 1 i.e 1 document
    Profile getRandomProfile();
}

