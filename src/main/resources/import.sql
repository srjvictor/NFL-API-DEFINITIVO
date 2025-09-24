-- Inserir Estadios
INSERT INTO estadios (id, nome, capacidade) VALUES
(1, 'Lambeau Field', 81441),
(2, 'AT&T Stadium', 80000),
(3, 'SoFi Stadium', 70240),
(4, 'Allegiant Stadium', 65000),
(5, 'Hard Rock Stadium', 65326),
(6, 'Arrowhead Stadium', 76416);

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

-- Inserir Times
INSERT INTO time (id, nome, cidade, conferencia, estadio_id) VALUES
(1, 'Green Bay Packers', 'Green Bay', 'NFC', 1),
(2, 'Dallas Cowboys', 'Dallas', 'NFC', 2),
(3, 'Los Angeles Rams', 'Los Angeles', 'NFC', 3),
(4, 'Las Vegas Raiders', 'Las Vegas', 'AFC', 4),
(5, 'Miami Dolphins', 'Miami', 'AFC', 5),
(6, 'Kansas City Chiefs', 'Missouri', 'AFC', 6);

-- Inserir Jogadores
INSERT INTO jogador (id, nome, numerocamisa, time_id) VALUES
(1, 'Jordan Love', 10, 1),
(2, 'Josh Jacobs', 8, 1),
(3, 'Dak Prescott', 4, 2),
(4, 'Javonte Williams', 33, 2),
(5, 'Matthew Stafford', 9, 3),
(6, 'Puka Nacua', 12, 3),
(7, 'Geno Smith', 7, 4),
(8, 'Jakobi Meyers', 16, 4),
(9, 'Tua Tagovailoa', 1, 5),
(10, 'Tyreek Hill', 10, 5),
(11, 'Travis Kelce', 87, 6),
(12, 'Patrick Mahomes', 15, 6);

-- Inserir Relacionamentos Jogador-Posicao
INSERT INTO jogador_posicao (jogador_id, posicao_id) VALUES
(1, 1),
(2, 2),
(3, 1),
(4, 2),
(5, 1),
(6, 3),
(7, 1),
(8, 3),
(9, 1),
(10, 3),
(11, 4),
(12, 1);