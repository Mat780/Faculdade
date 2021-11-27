-- Importação das bibliotecas.
library ieee;
use ieee.std_logic_1164.all;

-- Registrador para as teclas de senhas.
entity comparador is
	port (senha_digitada, senha_guardada: IN std_logic_vector (7 downto 0);
						clock: in std_logic;
						estado: in std_logic_vector(2 downto 0);
						resultado: OUT std_logic); 
end comparador;

-- Compara as senhas de teclas 1 com a de teclas2.
architecture comparar of comparador is
    BEGIN
        process(estado)
        BEGIN
            if(senha_digitada = senha_guardada) and estado = "010" then
                resultado <= '1';

            elsif estado = "110" then resultado <= '1';

            else
                resultado <= '0';

            end if;
        END process;
    END comparar;