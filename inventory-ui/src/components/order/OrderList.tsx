import OrderItemList from "./OrderItemList";
import type {Order} from "../../types/Order.ts";

interface Props {
    orders: Order[];
}

export default function OrderList({ orders }: Props) {
    if (orders.length === 0) {
        return (
            <div className="text-center text-gray-500 mt-10">
                No orders found.
            </div>
        );
    }

    return (
        <div className="space-y-6">
            {orders.map((order) => (
                <div
                    key={order.id}
                    className="bg-white shadow-md rounded-2xl p-6 border border-gray-200"
                >
                    <div className="flex justify-between items-center mb-4">
                        <div>
                            <h2 className="text-xl font-semibold text-gray-800">Order #{order.id}</h2>
                            <p className="text-sm text-gray-500">
                                Requested at: {new Date(order.requestedAt).toLocaleString()}
                            </p>
                        </div>
                        <span
                            className={`text-sm font-medium px-3 py-1 rounded-full 
                ${order.orderStatus === "SUCCESS" ? "bg-green-100 text-green-700" :
                                order.orderStatus === "FAILED" ? "bg-red-100 text-red-700" :
                                    "bg-yellow-100 text-yellow-700"}`}
                        >
              {order.orderStatus}
            </span>
                    </div>

                    <OrderItemList items={order.orderItems} />
                </div>
            ))}
        </div>
    );
}