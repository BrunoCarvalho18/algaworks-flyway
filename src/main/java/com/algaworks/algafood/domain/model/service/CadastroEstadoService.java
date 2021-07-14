package com.algaworks.algafood.domain.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.algaworks.algafood.domain.excpetion.EntidadeEmUsoException;
import com.algaworks.algafood.domain.excpetion.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	  @Autowired
      private EstadoRepository estadoRepository;
	  
	  private static final String MSG_ESTADO_EM_USO  = 
		        "Estado de código %d não pode ser removido, pois está em uso";

      
      public Estado salvar(Estado estado) {
          return estadoRepository.save(estado);
      }
      
      public void excluir(Long estadoId) {
          try {
              estadoRepository.deleteById(estadoId);
              
          } catch (EmptyResultDataAccessException e) {
              throw new EstadoNaoEncontradoException(estadoId);
          
          } catch (DataIntegrityViolationException e) {
				throw new EntidadeEmUsoException(
                  String.format(MSG_ESTADO_EM_USO, estadoId));
          }
      }

      public Estado buscarOuFalhar(Long estadoId) {
    	    return estadoRepository.findById(estadoId)
    	        .orElseThrow(()-> new EstadoNaoEncontradoException(estadoId));
    	}
}
