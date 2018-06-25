package casac.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import casac.web.model.Produto;
import casac.web.repository.ProdutoRepository;
import casac.web.util.ImagemUtils;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public void adicionarProduto(Produto produto, MultipartFile imagem) {
		produtoRepository.save(produto);
		String caminho = "images/" + produto.getId() + ".png";
		ImagemUtils.salvarImagem(caminho, imagem);
	}

	public void atualizaProduto(Produto produto) {
		produtoRepository.save(produto);
	}
	public List<Produto> listaProdutos(){
		return produtoRepository.findAll();				
	}

	public Produto buscaPorId(Long id) {
		return produtoRepository.getOne(id);
	}
	
	public void removerProduto(Long id) {
		String caminho = "images/" + id + ".png";
		ImagemUtils.removeImagem(caminho);
		produtoRepository.deleteById(id);
		
	}
	
	
}
