package app.web.scout.model.repository.security;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.security.TokenBlackList;


@Repository
public interface TokenBlackListRepo  extends JpaRepository<TokenBlackList, Integer> {
	
   Optional<TokenBlackList> findByJti(String jti);
   
   List<TokenBlackList> queryAllByUserIdAndIsBlackListedTrue(Integer userId);

//   void save(TokenBlackList tokenBlackList);

   List<TokenBlackList> deleteAllByUserIdAndExpiresBefore(Integer userId, Long date);
   
}
