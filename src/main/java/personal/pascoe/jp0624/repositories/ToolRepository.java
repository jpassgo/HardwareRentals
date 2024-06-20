package personal.pascoe.jp0624.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.pascoe.jp0624.models.Tool;

import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    Optional<Tool> findByToolCode(String toolCode);
}