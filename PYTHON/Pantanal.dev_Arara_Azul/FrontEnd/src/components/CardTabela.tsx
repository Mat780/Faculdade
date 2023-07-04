interface CardTabelaProps {
    produto: string;
    avaliacao: any;
}

export default function CardTabela(props: CardTabelaProps) {
    return (
                <tr>
                    <td className="border p-2 py-4">{props.produto}</td>
                    <td className="border p-2 py-4">{props.avaliacao}</td>                                        
                </tr>
    )
}