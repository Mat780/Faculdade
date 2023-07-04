import Tabela from "../components/Tabela";

export default function Layout() {
    return (
        <div className="flex flex-col items-center justify-start min-h-screen bg-gray-950">
            <div className="w-full p-4 text-center items-center flex justify-center">
                <div className="container justify-center items-center flex relative">
                    <div className="absolute bg-gradient-to-r from-green-600 to-blue-600 blur inset-x-52 inset-y-2"></div>
                    <div className="relative m-3 py-3 border-white rounded-lg w-8/12 bg-gray-900">
                        <h1 className="text-3xl font-bold">Tuiuiu.tech</h1>
                    </div>
                </div>
            </div>
            <div className="flex justify-center items-start mt-2 rounded-lg p-6 min-h-full container relative">
                <div className="absolute bg-gradient-to-r from-green-600 to-blue-600 blur inset-x-5 inset-y-5"></div>
                <div className="relative flex justify-center items-start rounded-lg p-6 pb-64 container border-1 bg-gray-900">
                    <div className="w-1/3 p-4">
                        <p className="text-3xl font-bold mb-2">Descrição</p>
                        <p className="text-xl">O objetivo do nosso trabalho é identificar os sentimentos expressos nas
                            avaliações dos clientes sobre produtos adquiridos em marketplaces.
                            Decidimos focar na Amazon devido à grande quantidade de avaliações disponíveis e à sua API robusta. </p>
                    </div>
                    <div className="w-2/3 p-4 relative rounded-lg">
                        <div className="absolute bg-gradient-to-r from-green-600 to-blue-600 blur inset-x-3 inset-y-3"></div>
                        <Tabela />
                    </div>
                </div>
            </div>
        </div>
    )
}