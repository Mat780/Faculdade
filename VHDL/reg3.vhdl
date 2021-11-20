library ieee;
use ieee.std_logic_1164.all;

-- Registrador com 2 Fliflops D.
entity reg3 is 
	port (D: IN std_logic_vector(1 downto 0);
			clock, reset: IN std_logic;
			Q: OUT std_logic_vector(1 downto 0));
END reg3;

-- Modo para resetar o Registrador.
architecture reset of reg3 is
	BEGIN
		process (clock, reset)
		BEGIN
			if reset = '1' then
				Q <= "01";

			elsif clock'event and clock = '1' then Q <= D;
			
			END if;
		END process;
	END reset;

