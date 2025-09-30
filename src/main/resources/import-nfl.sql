-- Inserir Posicoes
INSERT INTO posicao (id, sigla, nome) VALUES
(1, 'QB', 'Quarterback'),
(2, 'RB', 'Running Back'),
(3, 'WR', 'Wide Receiver'),
(4, 'TE', 'Tight End'),
(5, 'OL', 'Offensive Lineman'),
(6, 'DL', 'Defensive Lineman'),
(7, 'LB', 'Linebacker'),
(8, 'CB', 'Cornerback'),
(9, 'S', 'Safety'),
(10, 'K', 'Kicker'),
(11, 'P', 'Punter');

-- Inserir Estadios e Times
-- Time 1: Green Bay Packers
INSERT INTO Estadio(id, nome, capacidade) VALUES (nextval('Estadio_SEQ'), 'Lambeau Field', 81441);
INSERT INTO Time(id, nome, cidade, conferencia, estadio_id) VALUES (nextval('Time_SEQ'), 'Green Bay Packers', 'Green Bay', 'NFC', currval('Estadio_SEQ'));

-- Time 2: Kansas City Chiefs
INSERT INTO Estadio(id, nome, capacidade) VALUES (nextval('Estadio_SEQ'), 'Arrowhead Stadium', 76416);
INSERT INTO Time(id, nome, cidade, conferencia, estadio_id) VALUES (nextval('Time_SEQ'), 'Kansas City Chiefs', 'Kansas City', 'AFC', currval('Estadio_SEQ'));

-- Time 3: San Francisco 49ers
INSERT INTO Estadio(id, nome, capacidade) VALUES (nextval('Estadio_SEQ'), 'Levi''s Stadium', 68500);
INSERT INTO Time(id, nome, cidade, conferencia, estadio_id) VALUES (nextval('Time_SEQ'), 'San Francisco 49ers', 'Santa Clara', 'NFC', currval('Estadio_SEQ'));

--Time 4: Dallas Cowboys
INSERT INTO Estadio(id, nome, capacidade) VALUES (nextval('Estadio_SEQ'), 'AT&T Stadium', 80000);
INSERT INTO Time(id, nome, cidade, conferencia, estadio_id) VALUES (nextval('Time_SEQ'), 'Dallas Cowboys', 'Dallas', 'NFC', currval('Estadio_SEQ'));

--Time 5: Los Angeles Rams
INSERT INTO Estadio(id, nome, capacidade) VALUES (nextval('Estadio_SEQ'), 'SoFi Stadium', 70240);
INSERT INTO Time(id, nome, cidade, conferencia, estadio_id) VALUES (nextval('Time_SEQ'), 'Los Angeles Rams', 'Los Angeles', 'NFC', currval('Estadio_SEQ'));

--Time 6: Miami Dolphins
INSERT INTO Estadio(id, nome, capacidade) VALUES (nextval('Estadio_SEQ'), 'Hard Rock Stadium', 65326);
INSERT INTO Time(id, nome, cidade, conferencia, estadio_id) VALUES (nextval('Time_SEQ'), 'Miami Dolphins', 'Miami', 'AFC', currval('Estadio_SEQ'));

-- Inserir Jogadores e suas posições (relação Many-to-Many)
-- Assumindo que o Time com id=1 é o Green Bay Packers e a Posição com id=1 é 'QB'
INSERT INTO Jogador(id, nome, numeroCamisa, time_id, dataCriacao) VALUES (nextval('Jogador_SEQ'), 'Aaron Rodgers', 12, 1, NOW());
INSERT INTO jogador_posicao(jogador_id, posicao_id) VALUES (currval('Jogador_SEQ'), 1);

-- Assumindo que o Time com id=2 é o Kansas City Chiefs e a Posição com id=4 é 'TE'
INSERT INTO Jogador(id, nome, numeroCamisa, time_id, dataCriacao) VALUES (nextval('Jogador_SEQ'), 'Travis Kelce', 87, 2, NOW());
INSERT INTO jogador_posicao(jogador_id, posicao_id) VALUES (currval('Jogador_SEQ'), 4);