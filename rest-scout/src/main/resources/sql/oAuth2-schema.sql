/*
 * Script para la creacion del esquema de seguridad.
 * 
 */
 
create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token VARBINARY(8000),
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token VARBINARY(8000),
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication VARBINARY(8000),
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token VARBINARY(8000),
  authentication VARBINARY(8000)
);

create table oauth_code (
  code VARCHAR(256), authentication VARBINARY(8000)
);

CREATE TABLE token_black_list (
	jti varchar(256) NOT NULL ,
	expires bigint NULL,
	is_black_listed bit NOT NULL,
	user_id int NULL,
	PRIMARY KEY(jti)
);


