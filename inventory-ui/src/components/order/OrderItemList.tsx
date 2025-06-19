import type {OrderItem} from "../../types/OrderItem.ts";

interface Props {
    items: OrderItem[];
}

export default function OrderItemList({ items }: Props) {
    return (
        <ul className="divide-y divide-gray-100">
            {items.map((item, index) => (
                <li key={index} className="py-2 flex justify-between items-center">
                    <div>
                        <p className="text-gray-800 font-medium">{item.productName}</p>
                        <p className="text-sm text-gray-500">Quantity: {item.quantityRequested}</p>
                    </div>
                </li>
            ))}
        </ul>
    );
}