package com.ApiECommerce.apiec.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ApiECommerce.apiec.Model.File;

@Repository
public interface IFileRepository extends CrudRepository<File, Long>{

}
