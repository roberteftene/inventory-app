import OrderList from "./OrderList.tsx";
import {useOrders} from "../../hooks/useOrders.tsx";

export default function OrderDashboard() {
    const { orders, loading, error } = useOrders();

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center px-4 py-8">
            <div className="w-full max-w-4xl">
                <h1 className="text-3xl font-bold text-gray-800 mb-6">Orders Dashboard</h1>

                {loading && <div className="text-center text-gray-500">Loading orders...</div>}
                {error && <div className="text-center text-red-500">Error loading orders.</div>}
                {!loading && !error && <OrderList orders={orders} />}
            </div>
        </div>
    );
}