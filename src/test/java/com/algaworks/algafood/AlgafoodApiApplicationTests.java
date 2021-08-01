package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.service.CadastroCozinhaService;

@SpringBootTest
class AlgafoodApiApplicationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesas");

		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		assertThat(novaCozinha.getId()).isNotNull();

	}

	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinha.salvar(novaCozinha);
		});

		assertThat(erroEsperado).isNotNull();

	}



}
