//
// NO CASO DE HAVER ERROR DE REFERENCIAS CICLICAS
//   AONDE TIVER @JsonManagedReference
//         RETIRAR
//   AONDE TIVER @JsonBackReference
//         COLOCAR JsonIgnore
//
package com.allangomes.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.allangomes.cursomc.domain.Categoria;
import com.allangomes.cursomc.domain.Cidade;
import com.allangomes.cursomc.domain.Cliente;
import com.allangomes.cursomc.domain.Endereco;
import com.allangomes.cursomc.domain.Estado;
import com.allangomes.cursomc.domain.ItemPedido;
import com.allangomes.cursomc.domain.Pagamento;
import com.allangomes.cursomc.domain.PagamentoComBoleto;
import com.allangomes.cursomc.domain.PagamentoComCartao;
import com.allangomes.cursomc.domain.Pedido;
import com.allangomes.cursomc.domain.Produto;
import com.allangomes.cursomc.domain.enums.EstadoPagamento;
import com.allangomes.cursomc.domain.enums.TipoCliente;
import com.allangomes.cursomc.repositories.CategoriaRepository;
import com.allangomes.cursomc.repositories.CidadeRepository;
import com.allangomes.cursomc.repositories.ClienteRepository;
import com.allangomes.cursomc.repositories.EnderecoRepository;
import com.allangomes.cursomc.repositories.EstadoRepository;
import com.allangomes.cursomc.repositories.ItemPedidoRepository;
import com.allangomes.cursomc.repositories.PagamentoRepository;
import com.allangomes.cursomc.repositories.PedidoRepository;
import com.allangomes.cursomc.repositories.ProdutoRepository;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Inform??tica");
		Categoria cat2 = new Categoria(null, "Administra????o");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "S??o Paulo");
		Cidade c1 = new Cidade(null, "Uberl??ndia", est1);
		Cidade c2 = new Cidade(null, "S??o Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		Cliente cli1 = new Cliente(null, "Maria", "maria@com", "123", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("1-1", "2-2"));
		
		Endereco e1 = new Endereco(null, "Rua a", "1", "bl", "LS", "0001", cli1, c1);
		Endereco e2 = new Endereco(null, "Av m", "2", "cl", "SS", "0002", cli1, c2);
			
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/aaaa hh:mm");
		Pedido ped1 = new Pedido(null, "30/09/2017 10:32", cli1, e1);
		Pedido ped2 = new Pedido(null, "30/10/2017 11:32", cli1, e2);
//		Pedido ped2 = new Pedido(null, sdf.parse("30/10/2017 11:32"), cli1, e2);
	
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
	
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, "20/10/2017 00:00", null); 
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
//				
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
//
		
	}

}
