-- Importação das bibliotecas.
library ieee;
use ieee.std_logic_1164.all;

-- Entidade responsável por salvar no estado anterior o estado atual, ou seja,
-- informar ao estado passado qual o estado de destino.
entity reg3 is 
	-- Estado recebe 2 ou 0.
	port (estado: IN std_logic_vector(2 downto 0);
			-- Clock e reset como portas lógicas.
			clock, reset: IN std_logic;
			-- Estado_anterior recebe 2 ou 0.
			estado_anterior: OUT std_logic_vector(2 downto 0));
END reg3;

-- Arquitetura de reset da fechadura.
architecture reset of reg3 is
	BEGIN
		process (clock, reset)
		BEGIN
		--Se reset estiver em nível alto, o estado passa a ser 001.
			if reset = '1' then
				estado_anterior <= "001";

			-- Se clock estiver em nível alto, então estado_anterior passa para estado.
			elsif clock'event and clock = '1' then estado_anterior <= estado;
			
			END if;
		END process;
	END reset;
