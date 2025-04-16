package de.needfulapps.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Account {
    private String accountNumber;
    private String name;
    private BigDecimal balance;
}
