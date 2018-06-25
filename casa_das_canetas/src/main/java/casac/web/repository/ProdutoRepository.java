package casac.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import casac.web.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
