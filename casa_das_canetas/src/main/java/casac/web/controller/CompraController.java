package casac.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import casac.web.model.Compra;
import casac.web.model.Pessoa;
import casac.web.model.Produto;
import casac.web.service.CompraService;
import casac.web.service.PessoaService;
import casac.web.service.ProdutoService;

@Controller
@RequestMapping("/compra")
public class CompraController {

	@Autowired
	private CompraService compraService;

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping("/finalizacompra")//pagina final do usuario realizando as compras
	public ModelAndView finalizarCompra() {
		
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		List<Produto> listadeprodutos = pessoa.getCarrinho();
		Compra compra = new Compra();
		compra.setPessoa(pessoa);
		compra.setEstado("Em avaliação");
		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy");
		compra.setData(String.valueOf(formatador.format(data)));
		compra.setItens(new ArrayList<Produto>());
		compraService.adicionarCompra(compra);
				
		float valor = (float) 0;
		for (Produto produto : listadeprodutos) {
			valor = valor + Float.parseFloat(produto.getValor());
			List<Produto> prodcompra = new ArrayList<Produto>();
			prodcompra = compra.getItens();
				
			prodcompra.add(produto);
			
			int aux = produto.getQtd_estoque();
			produto.setQtd_estoque(aux-1);
			produtoService.atualizaProduto(produto);
			
			compra.setItens(prodcompra);
			compra.setValor(String.valueOf(valor));
			compraService.adicionarCompra(compra);
		}
		
		
		listadeprodutos.clear();//limpa a lista para devolve-la a pessoa
		pessoa.setCarrinho(listadeprodutos);
		pessoaService.atualizarPessoa(pessoa);
		
		ModelAndView mv = new ModelAndView("redirect:/inicio");
		return mv;
	}
	
	
	
	@PostMapping("/adiciona")
	public ModelAndView adicionaCompra(Compra compra) {
		compraService.adicionarCompra(compra);
		ModelAndView mv = new ModelAndView("redirect:/compra/listarcompras");
		return mv;
	}

	@GetMapping("/listarcompras")//pagina onde o adm gerencia as compras no site
	public ModelAndView listaCompras() {
		List<Compra> compras = compraService.listarCompras();
		ModelAndView mv = new ModelAndView("lista-compras");
		mv.addObject("todasAsCompras", compras);
		return mv;
	}
		
	@RequestMapping("/att/{id}")
	public ModelAndView atualizaCompra(@PathVariable Long id) {
		Compra compra = compraService.buscarPorId(id);
		
		ModelAndView mv = new ModelAndView("att-compra");
		mv.addObject("compra", compra);
		return mv;
	}
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView removerCompra(@PathVariable Long id) {
		compraService.removerCompra(id);
		ModelAndView mv = new ModelAndView("redirect:/listarcompras");
		return mv;
	}
	
	
}
