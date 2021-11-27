library ieee;
use ieee.std_logic_1164.all;

-- Registrador para teclas
entity reg8 is 
	port (teclas: in std_logic_vector(7 downto 0);
			estado: in std_logic_vector(1 downto 0);
			modo_op, clock, reset: in std_logic;
			senha: out std_logic_vector(7 downto 0));
end reg8;

-- Metodo para resetar o Registrador
architecture reset of reg8 is 
begin 
	process (clock, reset, modo_op)
	begin 
		if reset = '1' then 
			senha<= "00000000";
		elsif clock'event and clock = '1' 
			and modo_op='0' and estado="01" then senha<=teclas;
		end if;
	end process;
end reset;