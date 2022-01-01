package com.smart.smartcontactmanager.dao;


import com.smart.smartcontactmanager.entities.Contactus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactusRepository extends JpaRepository<Contactus,Integer> {
}
