package br.com.HidroSafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 8, max = 64, message = "o nome deve ter entre 8 a 64 caracteres")
    @NotBlank(message = "o campo nome é obrigatório")
    private String nomeCompleto;

    @Email(message = "e-mail inválido")
    @NotBlank(message = "o campo email é obrigatório")
    @Column(unique = true)
    private String email;

    @Size(min = 6, message = "deve ter pelo menos 6 caracteres")
    private String senha;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CargoUsuario cargo = CargoUsuario.USUARIO;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    @NotNull(message = "o campo endereço é obrigatório")
    private Endereco endereco;
}
