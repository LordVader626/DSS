-- DROP DATABASE `ESIdeal`;
-- CREATE DATABASE `ESIdeal`;
-- USE `ESIdeal`;


-- DELETE FROM `ESIdeal`.`mecanicos` WHERE Id=;
-- SELECT * FROM `ESIdeal`.`mecanicos`;
INSERT INTO `ESIdeal`.`mecanicos`
(Id,Nome,Password)
VALUES
('123456','Renato Garcia','rengar'),
('654321','Tiago Moita','tiamoi'),
('455342','Ivete Cruz','ivecru'),
('786432','Diogo Barros','diobar'),
('984234','Ricardo Esgaio','ricesg'),
('853265','Artur Cabral','artcab');

-- DELETE FROM `ESIdeal`.`postos` Where Nome='';
-- SELECT * FROM `ESIdeal`.`postos`;
INSERT INTO `ESIdeal`.`postos`
(Nome, Especializacao, Mecanico)
VALUES
('Posto Univ','universal','123456'),
('Posto Mot','combustao','455342'),
('Posto Ele','eletrico','786432'),
('Posto 1','combustao','654321'),
('Posto Check','checkup','853265');

-- DELETE FROM `ESIdeal`.`motores`;
-- SELECT * FROM `ESIdeal`.`motores`;
INSERT INTO `ESIdeal`.`motores`
(Codigo,Tipo_Motor)
VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,2),
(7,3),
(8,1),
(9,3);


-- SELECT * FROM `ESIdeal`.`motoresCombustao`;
INSERT INTO `ESIdeal`.`motoresCombustao`
(Codigo,Oleo,Filtro_Oleo,Combustivel,Ar_do_Motor,Conversor,Bateria_Arranque)
VALUES
(1,'novo','gasto','gasto','novo','gasto','novo'),
(2,'gasto','novo','novo','novo','gasto','novo'),
(6,'novo','novo','gasto','gasto','novo','novo'),
(8,'gasto','gasto','novo','novo','novo','gasto');


INSERT INTO `ESIdeal`.`combustaoDiesel`
(Codigo, Velas_Incandescencia,Filtro_Particulas)
VALUES
(1,'gasto','novo'),
(8,'novo','novo');


INSERT INTO `ESIdeal`.`combustaoGasolina`
(Codigo, Velas_Ignicao,Valvula_Aceleracao)
VALUES
(2,'novo','gasto'),
(6,'gasto','gasto');


INSERT INTO `ESIdeal`.`motoresEletricos`
(Codigo, Bateria)
VALUES
(3,'gasto'),
(7,'novo'),
(9,'gasto');


INSERT INTO `ESIdeal`.`motoresHibridos`
(Codigo, Motor_Eletrico,Motor_Combustao)
VALUES
(4,7,8),
(5,9,6);

INSERT INTO `ESIdeal`.`viaturas`
(Matricula, Pneus,Rodas,Direcao,Injetores,Travoes,Filtro_de_Ar,Ficha_de_veiculo,Motor)
VALUES
('34RT56','gasto','novo','novo','gasto','novo','gasto','reparado',1),
('12AS34','novo','gasto','novo','novo','gasto','gasto','reparado',2),
('75VD90','gasto','novo','novo','gasto','novo','novo','reparado',3),
('78BG12','gasto','gasto','gasto','novo','novo','gasto','reparado',4),
('AU0483','novo','novo','gasto','gasto','gasto','novo','reparado',5);


-- DELETE FROM `ESIdeal`.`clientes`;

INSERT INTO `ESIdeal`.`clientes`
(Nif,Nome,Contacto,ViaturaID)
VALUES
('23045611','Diogo Cunha', '924207603','34RT56'),
('54727495','Rui Naosei', '923957204','12AS34'),
('96462306','Tomas Animes', '945123456','75VD90'),
('54432876','Nuno Naosei', '945823294','78BG12'),
('11122233','Guilherme Rio', '933645141','AU0483');


