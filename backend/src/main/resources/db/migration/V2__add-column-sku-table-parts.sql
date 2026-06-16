alter table parts
    add column code_sku VARCHAR(4) unique;

update parts set code_sku = '2314'
    where id = 1;

alter table parts
    alter column code_sku set NOT NULL ;