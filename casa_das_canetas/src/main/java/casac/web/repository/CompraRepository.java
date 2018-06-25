package casac.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import casac.web.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long>{
	
}
