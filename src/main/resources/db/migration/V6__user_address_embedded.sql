-- Torna o endereço do usuário um Value Object embutido, com os mesmos campos
-- já usados pela tabela de restaurantes.

ALTER TABLE challenge.users ADD COLUMN street       VARCHAR(255);
ALTER TABLE challenge.users ADD COLUMN number       VARCHAR(255);
ALTER TABLE challenge.users ADD COLUMN neighborhood VARCHAR(255);
ALTER TABLE challenge.users ADD COLUMN city         VARCHAR(255);
ALTER TABLE challenge.users ADD COLUMN state        VARCHAR(255);
ALTER TABLE challenge.users ADD COLUMN zip_code     VARCHAR(255);

-- Backfill: o endereço livre existente vai para street; os demais campos
-- (obrigatórios) recebem um placeholder por não haver como decompô-lo.
UPDATE challenge.users
   SET street       = address,
       number       = '-',
       neighborhood = '-',
       city         = '-',
       state        = '-',
       zip_code     = '-'
 WHERE address IS NOT NULL;

ALTER TABLE challenge.users ALTER COLUMN street       SET NOT NULL;
ALTER TABLE challenge.users ALTER COLUMN number       SET NOT NULL;
ALTER TABLE challenge.users ALTER COLUMN neighborhood SET NOT NULL;
ALTER TABLE challenge.users ALTER COLUMN city         SET NOT NULL;
ALTER TABLE challenge.users ALTER COLUMN state        SET NOT NULL;
ALTER TABLE challenge.users ALTER COLUMN zip_code     SET NOT NULL;

ALTER TABLE challenge.users DROP COLUMN address;
