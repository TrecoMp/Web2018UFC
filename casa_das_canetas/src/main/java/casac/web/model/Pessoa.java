package casac.web.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Entity
public class Pessoa implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String endereco;
	private String email;
	
	@ManyToMany(fetch= FetchType.LAZY)
	private List<Produto> carrinho;
	

	@OneToMany(mappedBy = "pessoa")
	private List<Compra> hcompras;
	
	
	public List<Produto> getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(List<Produto> carrinho) {
		this.carrinho = carrinho;
	}
	
	public List<Compra> getHcompras() {
		return hcompras;
	}

	public void setHcompras(List<Compra> hcompras) {
		this.hcompras = hcompras;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	private String tell;
	private String login;
	private String senha;
	
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	private String data_nasc;
	
	@ManyToMany(fetch= FetchType.EAGER)
	@JoinTable( 
	        name = "pessoas_roles", 
	        joinColumns = @JoinColumn(
	          name = "pessoa_id", referencedColumnName = "id"), 
	        inverseJoinColumns = @JoinColumn(
	          name = "role_id", referencedColumnName = "papel"))
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTell() {
		return tell;
	}
	public void setTell(String tell) {
		this.tell = tell;
	}
	public String getData_nasc() {
		return data_nasc;
	}
	
	public void setData_nasc(String data_nasc) {
		this.data_nasc = data_nasc;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return (Collection<? extends GrantedAuthority>) this.roles;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.login;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
