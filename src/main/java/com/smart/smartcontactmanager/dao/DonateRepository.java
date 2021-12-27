package com.smart.smartcontactmanager.dao;

import com.smart.smartcontactmanager.entities.Donate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonateRepository extends JpaRepository<Donate,Integer> {
}
