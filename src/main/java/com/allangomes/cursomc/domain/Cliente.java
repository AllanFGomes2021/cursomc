package com.allangomes.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.allangomes.cursomc.domain.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "CLIENTE")
public class Cliente  implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Integer id;

	@Column(name = "NOME", length = 255, nullable = false)
    private String nome;

	@Column(name = "EMAIL", length = 255, nullable = false)
    private String email;

	@Column(name = "CPFOUCNPJ", length = 20, nullable = false)
    private String cpfoucnpj;

	@Column(name = "TIPO", nullable = false)
    private Integer tipo;
	
    @JsonManagedReference
    @OneToMany(mappedBy="cliente")
	private List<Endereco> enderecos = new ArrayList<>();
	
    @ElementCollection
    @CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>();
	
    @JsonBackReference
    @OneToMany(mappedBy="cliente")
    private List<Pedido> pedidos = new ArrayList<>();
    
    @Deprecated
    public Cliente() {
	}

    public Cliente(Integer id, String nome, String email, String cpfoucnpj, TipoCliente tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfoucnpj = cpfoucnpj;
		this.tipo = tipo.getCod();
	}

	public Cliente(Integer id, String nome, String email, String cpfoucnpj, TipoCliente tipo, List<Endereco> enderecos,
			Set<String> telefones) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfoucnpj = cpfoucnpj;
		this.tipo = tipo.getCod();
		this.enderecos = enderecos;
		this.telefones = telefones;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfoucnpj() {
		return cpfoucnpj;
	}

	public void setCpfoucnpj(String cpfoucnpj) {
		this.cpfoucnpj = cpfoucnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}
    
	
}
