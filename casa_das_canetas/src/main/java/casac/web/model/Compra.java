package casac.web.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private String estado;
	private String data;
	private String valor;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Produto> itens;

	public List<Produto> getItens() {
		return itens;
	}


	public void setItens(List<Produto> itens) {
		this.itens = itens;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}

	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}



	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}



