package casac.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import casac.web.model.Produto;
import casac.web.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping("/formproduto")
	public ModelAndView formularioProduto() {
		ModelAndView mv = new ModelAndView("formproduto");
		mv.addObject("produto", new Produto());
		return mv;
	}
	
	@PostMapping("/adicionaproduto")
	public ModelAndView salvarProduto(Produto produto, @RequestParam(value="imagem") MultipartFile imagem) {
		produtoService.adicionarProduto(produto, imagem);
		ModelAndView mv = new ModelAndView("redirect:/produto/gerenciarprodutos");
		return mv;
	}
	
	@GetMapping("/listaprodutos")
	public ModelAndView listaProdutos() {
		List<Produto> produtos = produtoService.listaProdutos();
		ModelAndView mv = new ModelAndView("lista-produtos");
		mv.addObject("todosOsProdutos", produtos);
		
		return mv;
	}
	
	@GetMapping("/gerenciarprodutos")
	public ModelAndView gerenciarProdutos() {
		List<Produto> produtos = produtoService.listaProdutos();
		ModelAndView mv = new ModelAndView("gerenciar-produtos");
		mv.addObject("todosOsProdutos", produtos);
		
		return mv;
	}
	
	
	@RequestMapping("{id}")
	public ModelAndView atualizaProduto(@PathVariable Long id) {
		Produto produto = produtoService.buscaPorId(id);
		
		ModelAndView mv = new ModelAndView("formproduto");
		mv.addObject("produto", produto);
		
		return mv;
	}
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView excluirProduto(@PathVariable Long id){
		produtoService.removerProduto(id);
		ModelAndView mv = new ModelAndView("redirect:/produto/gerenciarprodutos");
		return mv;
	}
	
}
