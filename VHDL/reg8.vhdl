-- Importação das bibliotecas.
library ieee;
use ieee.std_logic_1164.all;

-- Entidade responsável por salvar a senha_digitada na memória
entity reg8 is 
	-- Senha_guardada digitada recebe algarismos de 0 à 7.
	port (senha_digitada: in std_logic_vector(7 downto 0);
			-- Estado recebe 2 ou 0.
			estado: in std_logic_vector(2 downto 0);
			-- modo_op, clock e reset como portas lógicas.
			modo_op, clock, reset: in std_logic;
			-- Senha_aguardade recebe algarismos de 0 à 7.
			senha_guardada: out std_logic_vector(7 downto 0));
end reg8;

-- Arquitetura de reset da fechadura.
architecture reset of reg8 is 
begin 
	process (clock, reset, modo_op)
	begin 
		-- Se reset estiver em nível alto, a fechadura é resetada e a senha_guardada é limpa.
		if reset = '1' then -- Senha padrão: 1 3 , em binario.
			senha_guardada <= "00010011";

		-- Se reset estiver em nível baixo, clock estiver em nível alto, modo_op em nível baixo e
		-- estado em 001, então senha_guardada recebe senha_digitada.
		elsif clock'event and clock = '1' 
			and modo_op = '0' and estado = "001" then senha_guardada <= senha_digitada;
		end if;
	end process;
end reset;