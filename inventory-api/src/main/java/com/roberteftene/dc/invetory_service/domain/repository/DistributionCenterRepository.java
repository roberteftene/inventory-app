package com.roberteftene.dc.invetory_service.domain.repository;

import com.roberteftene.dc.invetory_service.domain.models.DistributionCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionCenterRepository extends JpaRepository<DistributionCenter, Long> {
}
