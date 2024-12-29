create table produit
(
    id_produit  integer not null
        primary key,
    nom         varchar(255),
    description varchar(255),
    categorie   varchar(255)
);

create table prix_fournisseur
(
    id_fournisseur integer,
    id_produit     integer,
    prix           integer
);

create table lot_de_produit
(
    id_produit        integer,
    quantite          integer,
    date_achat        date,
    date_peremption   date,
    prix_achat        double precision,
    prix_unitaire     double precision,
    id_fournisseur    integer,
    id_lot_de_produit uuid not null
        constraint lot_de_produit_pk
            primary key
);

create table fournisseur
(
    nom_societe varchar,
    siret       integer,
    adresse     varchar,
    email       varchar
);

create table contrat
(
    id_produit     integer,
    quantite_min   integer,
    date_debut     date,
    date_fin       date,
    prix_produit   double precision,
    id_fournisseur integer,
    id_contrat     uuid not null
        constraint contrat_pk
            primary key
);

create table contact_associe
(
    siret     integer,
    nom       varchar,
    prenom    varchar,
    fonction  varchar,
    email     varchar,
    telephone varchar
);

create table vente
(
    numero_ticket     varchar,
    id_produit        integer,
    date_vente        date,
    prix_unite        double precision,
    quantite          integer,
    id_lot_de_produit uuid
);

create table commande_a_effectuer
(
    id_commande uuid    not null
        constraint commande_a_effectuer_pk
            primary key,
    id_produit  integer not null
);

