USE ESIdeal;


-- -- Cria a Tabela "mecanicos"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`mecanicos` (
 Id varchar(45) NOT NULL PRIMARY KEY,
 Nome varchar(45) DEFAULT '',
 Password varchar(45) DEFAULT '');

-- Cria a Tabela "postos"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`postos` (
 Nome varchar(45) NOT NULL PRIMARY KEY,
 Especializacao varchar(45) DEFAULT '',
 Mecanico varchar(45) DEFAULT '',
 FOREIGN KEY (Mecanico) REFERENCES mecanicos(Id));


-- Cria a Tabela "motores"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`motores` (
 Codigo INT NOT NULL PRIMARY KEY,
 Tipo_Motor INT DEFAULT 0); -- 1-> CombustaoDiesel , 2-> CombustaoGasolina , 3-> Eletrico , 4-> Hibrido com CombustaoDiesel , 5-> Hibrido com CombustaoGasolina
 

 -- Cria a Tabela "motoresCombustao"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`motoresCombustao` (
 Codigo INT NOT NULL PRIMARY KEY,
 Oleo varchar(45) DEFAULT '',
 Filtro_Oleo varchar(45) DEFAULT '',
 Combustivel varchar(45) DEFAULT '',
 Ar_do_Motor varchar(45) DEFAULT '',
 Conversor varchar(45) DEFAULT '',
 Bateria_Arranque varchar(45) DEFAULT '',
 FOREIGN KEY (Codigo) REFERENCES motores(Codigo));

 -- Cria a Tabela "combustaoDiesel"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`combustaoDiesel` (
 Codigo INT NOT NULL PRIMARY KEY,
 Velas_Incandescencia varchar(45) DEFAULT '',
 Filtro_Particulas varchar(45) DEFAULT '',
 FOREIGN KEY (Codigo) REFERENCES motoresCombustao(Codigo));

 -- Cria a Tabela "combustaoGasolina"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`CombustaoGasolina` (
 Codigo INT NOT NULL PRIMARY KEY,
 Velas_Ignicao varchar(45) DEFAULT '',
 Valvula_Aceleracao varchar(45) DEFAULT '',
 FOREIGN KEY (Codigo) REFERENCES motoresCombustao(Codigo));

 -- Cria a Tabela "motoresEletricos"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`motoresEletricos` (
 Codigo INT NOT NULL PRIMARY KEY,
 Bateria varchar(45) DEFAULT '',
 FOREIGN KEY (Codigo) REFERENCES motores(Codigo));

 -- Cria a Tabela "motoresHibridos"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`motoresHibridos` (
 Codigo INT NOT NULL PRIMARY KEY,
 Motor_Eletrico int NOT NULL,
 Motor_Combustao int NOT NULL,
 FOREIGN KEY (Motor_Eletrico) REFERENCES motoresEletricos(Codigo),
 FOREIGN KEY (Motor_Combustao) REFERENCES motoresCombustao(Codigo),
 FOREIGN KEY (Codigo) REFERENCES motores(Codigo));

 -- Cria a Tabela "viaturas"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`viaturas` (
 Matricula varchar(45) NOT NULL PRIMARY KEY,
 Pneus varchar(45) DEFAULT '',
 Rodas varchar(45) DEFAULT '',
 Direcao varchar(45) DEFAULT '',
 Injetores varchar(45) DEFAULT '',
 Travoes varchar(45) DEFAULT '',
 Filtro_de_Ar varchar(45) DEFAULT '',
 Ficha_de_veiculo varchar(45) DEFAULT '',
 Motor int NOT NULL,
 FOREIGN KEY (Motor) REFERENCES motores(Codigo));


-- Cria a Tabela "servicos"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`servicos` (
 Id varchar(45) NOT NULL PRIMARY KEY,
 Tipo int DEFAULT 0,
 Descricao varchar(45) DEFAULT '',
 Notificacao BIT NOT NULL,
 ViaturaID varchar(45) DEFAULT '',
 FOREIGN KEY (ViaturaID) REFERENCES viaturas(Matricula));

-- Cria a Tabela "servicos_postoTrabalho"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`servicos_postoTrabalho` (
 IdServico varchar(45) NOT NULL PRIMARY KEY,
 NomePosto varchar(45) DEFAULT '', 
 FOREIGN KEY (IdServico) REFERENCES servicos(Id), 
 FOREIGN KEY (NomePosto) REFERENCES postos(Nome));
 

-- Cria a Tabela "clientes"

CREATE TABLE IF NOT EXISTS `ESIdeal`.`clientes` (
 Nif varchar(45) NOT NULL PRIMARY KEY,
 Nome varchar(45) DEFAULT '',
 Contacto varchar(45) DEFAULT '',
 ViaturaID varchar(45) DEFAULT '',
 FOREIGN KEY(ViaturaID) REFERENCES viaturas(Matricula));
 

-- Para mostrar as tabelas

SELECT * FROM `ESIdeal`.`clientes`;
SELECT * FROM `ESIdeal`.`mecanicos`;
SELECT * FROM `ESIdeal`.`postos`;
SELECT * FROM `ESIdeal`.`servicos`;
SELECT * FROM `ESIdeal`.`servicos_postoTrabalho`;
SELECT * FROM `ESIdeal`.`motores`;
SELECT * FROM `ESIdeal`.`motoresCombustao`;
SELECT * FROM `ESIdeal`.`combustaoDiesel`;
SELECT * FROM `ESIdeal`.`combustaoGasolina`;
SELECT * FROM `ESIdeal`.`motoresEletricos`;
SELECT * FROM `ESIdeal`.`motoresHibridos`;
SELECT * FROM `ESIdeal`.`viaturas`;
