library ieee;
use ieee.std_logic_1164.all;

-- Registrador para as teclas de senhas.
entity comparador is
	port (teclas1, teclas2: IN std_logic_vector (7 downto 0);
						clock: in std_logic;
						estado: in std_logic_vector(1 downto 0);
						resultado: OUT std_logic); 
end comparador;


-- Compara as senhas de teclas 1 com a de teclas2.
architecture comparar of comparador is
    BEGIN
        process(estado)
        BEGIN
            if(teclas1 = teclas2) and estado = "10" then
                resultado <= '1';

            else
                resultado <= '0';

            end if;
        END process;
    END comparar;
