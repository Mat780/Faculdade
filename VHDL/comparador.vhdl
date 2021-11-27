-- Importação das bibliotecas.
library ieee;
use ieee.std_logic_1164.all;

-- Entidade responsável por estabalecer a comparação entre a senha_guardada e a senha_digitada.
entity comparador is
    -- Senha_guardada e senha_digitada recebem algarismos de 0 à 7.
	port (senha_digitada, senha_guardada: IN std_logic_vector (7 downto 0);
                        -- Clock e resultado como portas lógicas.
						clock: in std_logic;
                        -- Estado recebe 2 ou 0.
						estado: in std_logic_vector(2 downto 0);
						resultado: OUT std_logic); 
end comparador;

-- Realiza a comparação entre a senha_guardada e a senha_digitada.
architecture comparar of comparador is
    BEGIN
        process (estado)
        
            -- Se senha_digitada for igual a senha_guardada, então resultado recebe 1;
            if (senha_digitada = senha_guardada) and estado = "010" then
                resultado <= '1';
            
            -- Se estado for igual a 110, então resultado recebe 1.
            elsif estado = "110" then resultado <= '1';
            
            -- Se nenhum dos casos anteriores acontecem, então resultado recebe 0.
            else
                resultado <= '0';

            end if;
        END process;
    END comparar;