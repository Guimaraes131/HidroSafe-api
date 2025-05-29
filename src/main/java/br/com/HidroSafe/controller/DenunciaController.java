package br.com.HidroSafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.HidroSafe.model.Denuncia;
import br.com.HidroSafe.repository.DenunciaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/denuncias")
@Slf4j
public class DenunciaController {

    @Autowired
    private DenunciaRepository repository;

    @GetMapping
    public List<Denuncia> index() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Denuncia get(@PathVariable Long id) {
        log.info("buscando denuncia " + id);
        return getDenuncia(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Denuncia post(@RequestBody @Valid Denuncia denuncia) {
        log.info("registrando denuncia " + denuncia);
        return repository.save(denuncia);
    }

    @PutMapping("{id}")
    public Denuncia update(@RequestBody @Valid Denuncia denuncia, @PathVariable Long id) {
        log.info("atualizando denuncia " + id + " para " + denuncia);
        denuncia.setId(id);

        return repository.save(denuncia);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("deletando denuncia " + id);

        repository.delete(getDenuncia(id));
    }

    private Denuncia getDenuncia(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }
}
