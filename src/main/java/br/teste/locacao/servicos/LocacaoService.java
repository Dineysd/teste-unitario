package br.teste.locacao.servicos;

import static br.teste.locacao.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.teste.locacao.entidades.Locacao;
import br.teste.locacao.entidades.Produto;
import br.teste.locacao.entidades.Usuario;
import br.teste.locacao.exceptions.LocadoraException;
import br.teste.locacao.exceptions.ProdutoSemEstoqueException;

public class LocacaoService {

	@SuppressWarnings("unused")
	public Locacao alugar(Usuario usuario, List<Produto> produtos)
			throws ProdutoSemEstoqueException, LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usuario vazio!");
		}
		if (produtos == null || produtos.isEmpty()) {
			throw new LocadoraException("Produto vazio!");
		}

		Locacao locacao = new Locacao();
		for (Produto produto : produtos) {
			if (produto.getEstoque() == 0) {
				throw new ProdutoSemEstoqueException();
			}
		}

		Double valorTotal = calcularValorTotalProduto(produtos);

		locacao.setDataLocacao(new Date());
		locacao.setValor(valorTotal);
		locacao.setProdutos(produtos);
		locacao.setUsuario(usuario);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar metodo para salvar

		return locacao;
	}

	private Double calcularValorTotalProduto(List<Produto> produtos) {

		Double valorTotal = 0d;
		for (int i = 0; i < produtos.size(); i++) {
			Produto produto = produtos.get(i);
			Double valorProduto = produto.getPrecoLocacao();
			if (i == 2) {
				valorProduto = valorProduto * 0.75;
			}
			if (i == 3) {
				valorProduto = valorProduto * 0.5;
			}
			if (i == 4) {
				valorProduto = valorProduto * 0.25;
			}
			valorTotal += valorProduto;
		}
		return valorTotal;
	}

}