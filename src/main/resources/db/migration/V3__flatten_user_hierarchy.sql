-- Achata a hierarquia de usuários: cpf passa a pertencer a todos os usuários
-- e as tabelas de subtipos (customers/restaurant_owners) deixam de existir.

-- 1. cpf entra em users (nullable temporário para backfill)
ALTER TABLE challenge.users ADD COLUMN cpf VARCHAR(14);

-- 2. backfill dos clientes existentes
UPDATE challenge.users u
   SET cpf = c.cpf
  FROM challenge.customers c
 WHERE c.id = u.id;

-- 3. cpf passa a ser obrigatório e único
ALTER TABLE challenge.users ALTER COLUMN cpf SET NOT NULL;
ALTER TABLE challenge.users ADD CONSTRAINT uq_users_cpf UNIQUE (cpf);

-- 4. remove as tabelas da herança
DROP TABLE challenge.customers;
DROP TABLE challenge.restaurant_owners;
