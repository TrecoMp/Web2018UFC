package casac.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import casac.web.model.Compra;
import casac.web.model.Pessoa;
import casac.web.repository.CompraRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;

	public void adicionarCompra(Compra compra) {
		compraRepository.save(compra);
	}
	
	public List<Compra> listarCompras() {
		return compraRepository.findAll();
	}
	
	public Compra buscarPorId(Long id) {
		return compraRepository.getOne(id);
	}
	
	public void removerCompra(Long id) {
		compraRepository.deleteById(id);
	}
}
