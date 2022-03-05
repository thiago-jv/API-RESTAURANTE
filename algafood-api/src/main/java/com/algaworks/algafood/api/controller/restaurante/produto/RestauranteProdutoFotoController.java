package com.algaworks.algafood.api.controller.restaurante.produto;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.foto.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.foto.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.produto.CadastroProdutoService;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;

	@PutMapping(value =  "/multpart/local", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
		
		var nomeArquivo = UUID.randomUUID().toString() + "_" +fotoProdutoInput.getArquivo().getOriginalFilename();
		
		// informando onde quero salvar o arquivo
		var arquivoFoto = Path.of("C:\\Users\\thiago.melo\\Documents\\arquivos\\catalogo", nomeArquivo);
		
		System.out.println(fotoProdutoInput.getDescricao());
		System.out.println(arquivoFoto);
		System.out.println(fotoProdutoInput.getArquivo().getContentType());
		
		try {
			// transferindo o objeto para o caminho especificado
			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@PutMapping(value =  "/multpartFoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFotos(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
		
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		FotoProduto foto = new FotoProduto();
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto);
		
		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}
	
}
