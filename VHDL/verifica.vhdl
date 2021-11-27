-- Importação das bibliotecas.
library ieee;
use ieee.std_logic_1164.all;

-- Declaração das informações de entrada e saída, respectivamente.
-- INPUTS:
-- senha_digitada = É a senha que será digitada tanto na hora da tentativa de desbloquear a porta quanto na hora de salvar a senha na memória.
-- modo_op = O modo de operação da porta. Ela pode estar em "0" que significa modo de configuração onde pode-se alterar a senha da porta, quando em "1" significa modo de operação neste modo a senha_digitada é interpretada como tentativa de abrir a porta.
-- clock = Em resumo, é um relógio para deixar o sistema síncrono.
-- reset = Ele liga ou reinicia o sistema da porta, resetando sua senha para "00000000".

-- OUTPUT: 
-- fechado = Indica se a porta está fechada, enquanto estiver em "1" significa que está fechada, assim que o sinal abaixar para "0" a porta estará aberta.
-- senha_invalida = Representa se a senha_digitada estiver errada.

entity verifica is
	port(senha_digitada: IN std_logic_vector (7 downto 0);
		modo_op: IN std_logic :='1'; 
		clock, reset: IN std_logic;
		fechado, senha_invalida : OUT std_logic);

end verifica;

architecture config OF verifica IS
	signal resultado: std_logic; -- resultado = É o resultado da verificação.
	signal estado: std_logic_vector(2 downto 0); -- estado =  É o novo estado da máquina.
	signal estado_anterior: std_logic_vector(2 downto 0) :="000"; -- estado_anterior = É o estado anterior da máquina.
	signal senha_guardada: std_logic_vector(7 downto 0); -- senha_guardada = É a senha digitada e guardada.
	signal sinal_fechado: std_logic;  -- sinal_fechado = É o sinal para fechado.

	-- Componente responsável por comprarar a senha_digitada com a senha_guardada.
	component comparador is
		port (senha_digitada, senha_guardada: IN std_logic_vector (7 downto 0);
						clock: IN std_logic;
						estado: IN std_logic_vector (2 downto 0);
						resultado: OUT std_logic);
	END component;

	-- Componente responsável por salvar no estado anterior o estado atual, ou seja,
	-- informar ao estado passado qual o estado de destino.
	component reg3 is
		port (estado: in std_logic_vector(2 downto 0);
				clock, reset: in std_logic;
				estado_anterior: out std_logic_vector(2 downto 0));
	END component;

	-- Componente responsável por salvar a senha_digitada na memória.
	component reg8 is
		port (senha_digitada: in std_logic_vector(7 downto 0);
					estado: in std_logic_vector(2 downto 0);
					modo_op, clock, reset: in std_logic;
					senha_guardada: out std_logic_vector(7 downto 0));
	END component;

	BEGIN
		-- Se fechado estiver em nível alto, a fechadura continua trancada.
		-- Se fechado estiver em nível baixo, a fechadura é aberta.
		fechado <= not ((not estado_anterior(0) and estado_anterior(1) and modo_op and resultado) or (estado_anterior(0) and estado_anterior(1) and not reset));
		
		-- Se sinal_fechado estiver em nível alto, o sinal de fechado é salvo como 1.
		-- Se sinal_fechado estiver em nível baixo, o sinal de fechado é salvo como 0.
		sinal_fechado <= not ((not estado_anterior(0) and estado_anterior(1) and modo_op and resultado) or (estado_anterior(0) and estado_anterior(1) and not reset));
		
		-- Se senha_invalida estiver em nível alto, a senha_digitada difere da senha_guardada,
		-- portanto a senha_digitada é inválida e a fechadura não se abre.
		-- Se senha_invalida estiver em nível baixo, a senha_digitada é igual a senha_guardada,
		-- portanto a senha_digitada é válida e a fechadura se abre.
		senha_invalida <= (not estado_anterior(0) and estado_anterior(1) and not resultado) or (estado_anterior(0) and not estado_anterior(1) and not reset);

		-- Chamada do componente reg8 e o define dentro de verifica.vhdl como senha_r8.
		senha_r8: reg8 port map (senha_digitada, estado_anterior, modo_op, clock, reset, senha_guardada);
		 
		-- Chamada do componente comparador e o define dentro de verifica.vhdl como comparar.
		comparar: comparador port map (senha_digitada, senha_guardada, clock, estado, resultado); 

		-- Mudanças de estados.
		estado(0) <= (estado_anterior(0) and not estado_anterior(1) and not modo_op) or (not estado_anterior(0) and estado_anterior(1) and modo_op and not resultado) or (estado_anterior(0) and estado_anterior(1) and not reset) or (estado_anterior(0) and estado_anterior(1) and not reset);
		
		estado(1) <= (not estado_anterior(0) and not estado_anterior(1) and modo_op) or (not estado_anterior(0) and estado_anterior(1) and modo_op and resultado) or (estado_anterior(0) and estado_anterior(1));

		estado(2) <= (not sinal_fechado);

		-- Chamada do componente reg3 e o define dentro de verifica.vhdl como estados_r3.
		estados_r3: reg3 port map (estado, clock, reset, estado_anterior);

END config;