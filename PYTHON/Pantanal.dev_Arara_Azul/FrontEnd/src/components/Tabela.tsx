import CardTabela from "./CardTabela";
import {useState} from "react";
import axios from "axios";

interface Produto {
    id: number;
    nome: string;
    avaliacao: number;
  }
  

export default function Tabela() {

    const [produto, setProduto] = useState("");
    const [avaliacao, setAvaliacao] = useState<number | undefined>();
    const [produtos, setProdutos] = useState<Produto[]>([]);
    const [isLoading, setIsLoading] = useState(false)
    const pesquisarProduto = () => {
        const pesquisarProdutoAPI = async () => {
            try {
                setIsLoading(true);
                const response = await axios.post(`http://localhost:5000/link`,
                    {
                        link : produto,
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                    });
                const data = await response;
                let novoProduto = {
                    id: produtos.length + 1,
                    nome: data.data.produto,
                    avaliacao: data.data.sentimento || 0
                };
                setProdutos((prevProdutos) => [...prevProdutos, novoProduto]);
                setProduto("");
                setAvaliacao(undefined);
            } catch (error) {
                // @ts-ignore
                alert("Erro ao buscar produtos:"+error.response.data.message)
            }finally {
                setIsLoading(false);
            }
        };
        pesquisarProdutoAPI();

    };

    return (
        <div>
            <div className="relative">
                {isLoading && (
                    <div className="fixed inset-0 flex items-center justify-center z-50 bg-gray-900 bg-opacity-50">
                        <div className="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-gray-300"></div>
                    </div>
                )}
            </div>
            <div className="relative bg-gray-900 p-5 rounded-lg h-6/12">
                <input
                    type="text"
                    className="w-full border bg-gray-900 border-gray-300 p-2 rounded-lg mb-4"
                    placeholder="Digite seu produto..."
                    value={produto}
                    onChange={(e) => setProduto(e.target.value)}
                />
                <button
                    className="bg-blue-600 text-white rounded-lg px-4 py-2"
                    onClick={pesquisarProduto}
                >
                    Pesquisar
                </button>
                <table className={`w-full mt-10 ${produtos.length > 0 ? 'h-72' : 'h-6/12'}`}>
                    <thead>
                        <tr>
                            <th className="border p-2 py-4">Produto</th>
                            <th className="border p-2 py-4">Avaliação Média</th>
                        </tr>
                    </thead>
                        <tbody>
                            {produtos.map((produto) => (
                            <CardTabela
                                key={produto.id}
                                produto={produto.nome}
                                avaliacao={produto.avaliacao}
                            />
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }