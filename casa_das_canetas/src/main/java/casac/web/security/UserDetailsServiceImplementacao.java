package casac.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import casac.web.model.Pessoa;
import casac.web.repository.PessoaRepository;

@Repository
public class UserDetailsServiceImplementacao implements UserDetailsService{

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
		Pessoa pessoa = pessoaRepository.findByLogin(login);
	
		if (pessoa == null){
			throw new UsernameNotFoundException("Deu merda");
		}
		
		return pessoa;
	}
}
