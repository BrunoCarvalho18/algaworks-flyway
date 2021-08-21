package com.algaworks.algafood.domain.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.excpetion.EntidadeEmUsoException;
import com.algaworks.algafood.domain.excpetion.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	  
	  private static final String MSG_CIDADE_EM_USO  = 
		        "Cidade de código %d não pode ser removido, pois está em uso";

	  private static final String MSG_CIDADE_NAO_ENCONTRADA = 
		        "Não existe um cadastro de cidade com código %d";

	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.findById(estadoId).orElseThrow(() 
				-> new EntidadeNaoEncontradaException(
				String.format(MSG_CIDADE_NAO_ENCONTRADA, estadoId)));

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_CIDADE_EM_USO, cidadeId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
	
	@Transactional
	public Cidade buscarOuFalhar(Long cidadeId) {
	    return cidadeRepository.findById(cidadeId)
	        .orElseThrow(() -> new EntidadeNaoEncontradaException(
	                String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
	}   

}
