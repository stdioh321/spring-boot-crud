package com.stdioh321.crud.repository;

import com.stdioh321.crud.model.JwtCheckList;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JwtCheckListRepository extends IRepositoryExtender<JwtCheckList, String> {

    /*@Query("SELECT j FROM JwtCheckList j WHERE token = ?1")
    Optional<JwtCheckList> findByJwt(String jwt);*/


    @Query("SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END FROM JwtCheckList j WHERE  token = ?1 AND blacklist = true")
    boolean isBlacklisted(String jwt);

}
