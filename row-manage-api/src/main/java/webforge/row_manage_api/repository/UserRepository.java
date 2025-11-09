package webforge.row_manage_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webforge.row_manage_api.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
