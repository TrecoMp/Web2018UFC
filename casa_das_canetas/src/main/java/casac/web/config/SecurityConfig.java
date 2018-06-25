package casac.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import casac.web.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImplementacao userDetailsImplementacao;

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/inicio").permitAll()
		.antMatchers("/pessoa/formpessoa").permitAll()
		.antMatchers("/pessoa/adicionar").permitAll()
		.antMatchers("/produto/listaprodutos").permitAll()
		.antMatchers("/pessoa/adicionarcarrinho/*").hasAnyRole("ADMIN","USER")
		.antMatchers("/pessoa/carrinho").hasAnyRole("USER", "ADMIN")
		.antMatchers("/pessoa/listar").hasRole("ADMIN")
		.antMatchers("/pessoa/excluir/*").hasRole("ADMIN")
		.antMatchers("/pessoa/*").permitAll()//.hasRole("ADMIN")
		.antMatchers("/produto/gerenciarprodutos").hasRole("ADMIN")
		.antMatchers("/produto/formproduto").hasRole("ADMIN")
		.antMatchers("/produto/adicionaproduto").hasRole("ADMIN")
		.antMatchers("/produto/gerenciarprodutos").hasRole("ADMIN")
		.antMatchers("/produto/exluir/*").hasRole("ADMIN")
		//.antMatchers("/produto/*").hasRole("ADMIN")
		.antMatchers("/compra/*").permitAll()
		.antMatchers("/compra/finalizacompra").permitAll()
		.antMatchers("/sobre").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/pessoa/logar").defaultSuccessUrl("/inicio").permitAll()
		.and().logout().logoutSuccessUrl("/inicio").logoutUrl("/logout").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsImplementacao).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**","/images/**");
	}
}

