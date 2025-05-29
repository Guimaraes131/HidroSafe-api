package br.com.HidroSafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import br.com.HidroSafe.model.Usuario;
import br.com.HidroSafe.repository.UsuarioRepository;
import br.com.HidroSafe.specification.UsuarioSpecification;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    public record UsuarioFilter(String nomeCompleto, String email) {}

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public Page<Usuario> index(UsuarioFilter filtro,
        @PageableDefault(size = 10, sort = "nomeCompleto", direction = Direction.DESC) Pageable pageable) {

        var specification = UsuarioSpecification.withFilters(filtro);

        return repository.findAll(specification, pageable);
    }

    @GetMapping("{id}")
    public Usuario get(@PathVariable Long id) {
        log.info("buscando usuario " + id);
        return getUsuario(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void post(@RequestBody @Valid Usuario usuario) {
        log.info("cadastrando usuario " + usuario.getEmail());
        repository.save(usuario);
    }

    @PutMapping("{id}")
    public Usuario update(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        log.info("atualizando usuario " + id + " para " + usuario);
        usuario.setId(id);

        return repository.save(usuario);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("deletando usuario " + id);
        repository.delete(getUsuario(id));
    }

    private Usuario getUsuario(Long id) {
        return repository
                    .findById(id)
                    .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                    );
    }
}
