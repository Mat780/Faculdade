library ieee;
use ieee.std_logic_1164.all;

-- Declaração dos dados de entrada e saída.
entity verifica is
	port(teclas: IN std_logic_vector (7 downto 0);
		modo_op, clock, reset : IN std_logic;
		fechado, blocke : OUT std_logic);

end verifica;

architecture config OF verifica IS
	signal res: std_logic; -- Resultado da verificação.
	signal estado: std_logic_vector(1 downto 0); -- Novo estado de maquina.
	signal est_ant: std_logic_vector(1 downto 0) :="00"; -- Estado anterior da máquina.
	signal password: std_logic_vector(7 downto 0); -- Senha digitada.

	-- Compara se a senha digitada é válida.
	component comparador is
		port (teclas1, teclas2: IN std_logic_vector (7 downto 0);
						clock: IN std_logic;
						estado: IN std_logic_vector (1 downto 0);
						resultado: OUT std_logic);
	END component;

	component reg3 is
		port (D: in std_logic_vector(1 downto 0);
				clock, reset: in std_logic;
				Q: out std_logic_vector(1 downto 0));
	END component;

	component reg8 is
		port (teclas: in std_logic_vector(7 downto 0);
					estado: in std_logic_vector(1 downto 0);
					modo_op, clock, reset: in std_logic;
					senha: out std_logic_vector(7 downto 0));
	END component;

	BEGIN
		fechado <= not ((not est_ant(0) and est_ant(1) and modo_op and res) or (est_ant(0) and est_ant(1) and not reset));
		blocke <= (not est_ant(0) and est_ant(1) and not res) or (est_ant(0) and not est_ant(1) and not reset);
		-- modo <= not ((not est_ant(0) and not est_ant(1) and not modo_op) or (not est_ant(0) and not est_ant(1) and not modo_op) or (est_ant(0) and not est_ant(1) and modo_op and not reset));

		-- Password = Nova senha digitada
		-- Esse registrador ira salvar a senha
		senha_r8: reg8 port map (teclas, est_ant, modo_op, clock, reset, password);
		 
		-- Sinal igual a 1 quando a sua entrada for igual a senha salva
		-- Res = resultado da verificação
		comparar: comparador port map (teclas, password, clock, estado, res); 

		-- Preenchendo o estado da maquina
		-- Verifica se está no estado das configurações

		estado(0) <= (est_ant(0) and not est_ant(1) and not modo_op) or (not est_ant(0) and est_ant(1) and modo_op and not res) or (est_ant(0) and est_ant(1) and not reset) or (est_ant(0) and est_ant(1) and not reset);
		
		estado(1) <= (not est_ant(0) and not est_ant(1) and modo_op) or (not est_ant(0) and est_ant(1) and modo_op and res) or (est_ant(0) and est_ant(1));

		estados_r3: reg3 port map (estado, clock, reset, est_ant);

END config;
