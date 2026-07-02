-- Semeia os tipos de usuário canônicos usados pela aplicação.
-- Os nomes devem casar exatamente com domain.model.UserTypeNames.
INSERT INTO challenge.user_types (id, name) VALUES
    (gen_random_uuid(), 'RESTAURANT_OWNER'),
    (gen_random_uuid(), 'CUSTOMER')
ON CONFLICT (name) DO NOTHING;
