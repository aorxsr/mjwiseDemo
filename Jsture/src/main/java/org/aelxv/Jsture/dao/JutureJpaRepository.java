package org.aelxv.Jsture.dao;

import org.aelxv.Jsture.entity.Juture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JutureJpaRepository extends JpaRepository<Juture, Integer> {

	public Juture findByName(String name);

}
