package casac.web.controller;

import java.util.ArrayList;
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
import casac.web.model.Role;
import casac.web.service.PessoaService;
import casac.web.service.ProdutoService;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private ProdutoService produtoService;

	@RequestMapping("/formpessoa")
	public ModelAndView formularioPessoa() {
		ModelAndView mv = new ModelAndView("formulario-pessoa");
		mv.addObject("pessoa", new Pessoa());
		
		return mv;
	}

	@PostMapping("/adicionar")
	public ModelAndView adicionarPessoa(Pessoa pessoa) {
		//todo usuario adicionado ja entra como usuario
		
		Pessoa p1 = pessoaService.buscaPorLogin(pessoa.getLogin());
		
		if(p1==null) {
		
			Role role = new Role();
			List<Pessoa> pessoas = new ArrayList<Pessoa>();
			List<Role> roles = new ArrayList<Role>();
			role.setPapel("ROLE_USER");
			pessoas.add(pessoa);
			roles.add(role);
			role.setPessoas(pessoas);
			//fim
			pessoa.setRoles(roles);
			pessoaService.adicionarPessoa(pessoa);
			ModelAndView mv = new ModelAndView("redirect:/produto/listaprodutos");
			return mv;
		}else {
			int resposta = 1;
			ModelAndView mv = new ModelAndView("formulario-pessoa");
			mv.addObject("pessoa", pessoa);
			mv.addObject("resposta", resposta);
			return mv;
		}
	}

	@GetMapping("/listarpessoas")
	public ModelAndView listarPessoas() {
		List<Pessoa> pessoas = pessoaService.listarPessoas();
		
		ModelAndView mv = new ModelAndView("lista-pessoas");
		mv.addObject("todasAsPessoas", pessoas);
		
		return mv;
	}
	// nao seise isso vai dar certo
	@RequestMapping("/adicionacarrinho/{idprod}")
	public ModelAndView adicionaCarrinho(@PathVariable Long idprod) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		Produto produto = produtoService.buscaPorId(idprod);
		
		List<Produto> produtos = pessoa.getCarrinho();
		produtos.add(produto);
		pessoa.setCarrinho(produtos);
		
		pessoaService.atualizarPessoa(pessoa);
		
		ModelAndView mv = new ModelAndView("redirect:/produto/listaprodutos");
		return mv;
		}
	
	@RequestMapping("/limparcarrinho")
	public ModelAndView limpaCarrinho() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		List<Produto> lista = pessoa.getCarrinho();
		lista.clear();
		pessoa.setCarrinho(lista);
		pessoaService.atualizarPessoa(pessoa);
		ModelAndView mv = new ModelAndView("redirect:/pessoa/carrinho");
		return mv;
	} 
	
	@RequestMapping("/carrinho")
	public ModelAndView carrinho() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		List<Produto> produtos = pessoa.getCarrinho();
		float valor=(float) 0;
		for (Produto produto : produtos) {
			valor = valor + Float.parseFloat(produto.getValor());
		}
				
		ModelAndView mv = new ModelAndView("carrinho");
		mv.addObject("todosOsProdutos", produtos);
		mv.addObject("valorTotal", valor);
		return mv;
	}
	
	
	@RequestMapping("/att/{id}")
	public ModelAndView atualizarPessoa(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.buscarPorId(id);
		
		ModelAndView mv = new ModelAndView("formpessoa");
		mv.addObject("pessoa", pessoa);
		
		return mv;
	}
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView excluirPessoa(@PathVariable Long id) {
		pessoaService.removerPessoa(id);
		
		ModelAndView mv = new ModelAndView("redirect:/pessoa/listarpessoas");
		
		return mv;
	}
	
	@RequestMapping("/logar")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}

	@RequestMapping("/minhascompras")
	public ModelAndView minhasCompras() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
			
		List<Compra> compras = pessoa.getHcompras();
				
		ModelAndView mv = new ModelAndView("minhascompras");
		mv.addObject("todasAsCompras", compras);
		return mv;
	}
	
	@RequestMapping("/gerencia")
	public ModelAndView gerencia(){
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		String nomepessoa = "Olá "+ pessoa.getNome() + ", que a força esteja com você!!!";
		
		
		List<Produto> produtos = produtoService.listaProdutos();
		
		
		ModelAndView mv = new ModelAndView("gerencia");
		mv.addObject("Produtos", produtos);
		mv.addObject("frase", nomepessoa);
		return mv;
	}
	
	@RequestMapping("/removedocarrinho/{id}")
	public ModelAndView removedoCarrinho(@PathVariable Long id) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		List<Produto> produtos = pessoa.getCarrinho();
				
		produtos.remove(produtoService.buscaPorId(id));
		
		pessoa.setCarrinho(produtos);
		pessoaService.atualizarPessoa(pessoa);
		ModelAndView mv = new ModelAndView("redirect:/pessoa/carrinho");
		return mv;
	}
	
	@RequestMapping("/tornaadm/{id}")
	public ModelAndView tornarAdm(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.buscarPorId(id);
		Role role = new Role();
		role.setPapel("ROLE_ADMIN");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		pessoa.setRoles(roles);
		pessoaService.atualizarPessoa(pessoa);
		ModelAndView mv = new ModelAndView("redirect:/pessoa/listarpessoas");
		return mv;
	}
}
