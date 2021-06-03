package br.com.zupacademy.armando.propostamicroservice.proposals.entities;

import br.com.zupacademy.armando.propostamicroservice.core.validations.CPFOrCNPJ;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_proposal")
public class Proposal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @CPFOrCNPJ
    private String document;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull @Positive
    private BigDecimal salary;

    public Proposal(String document, String email, String name, String address, BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

}
