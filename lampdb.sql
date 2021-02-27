CREATE TABLE account
(
    account_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    balance double,
    client_owner integer,
    CONSTRAINT account_pkey PRIMARY KEY (account_id),
    CONSTRAINT fk_account_owner_client FOREIGN KEY (client_owner) REFERENCES client (client_id)
)

CREATE TABLE client
(
    client_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name character varying(50),
    last_name character varying(50),
    email character varying(100) ,
    CONSTRAINT client_pkey PRIMARY KEY (client_id)
)