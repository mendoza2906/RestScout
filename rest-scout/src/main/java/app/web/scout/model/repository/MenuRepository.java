package app.web.scout.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
