package org.delivery.db.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "account")
//https://velog.io/@choish03030/SuperBuilder-EqualsAndHashCode
public class AccountEntity extends BaseEntity {
}
