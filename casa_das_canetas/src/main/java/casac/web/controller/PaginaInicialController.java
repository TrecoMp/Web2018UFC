package casac.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import casac.web.model.Produto;
import casac.web.service.ProdutoService;

@Controller

public class PaginaInicialController {

	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("redirect:/inicio");
		return mv;
	}
	
	@RequestMapping("/inicio")
	public ModelAndView paginaInicial() {
		List<Produto> produtos = produtoService.listaProdutos();
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("todosOsProdutos", produtos);
		
		return mv;
	}
	
	@RequestMapping("/sobre")
	public String paginaSobre() {
		return "sobre";
	}

}
