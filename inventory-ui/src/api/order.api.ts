import type {Order} from "../types/Order.ts";

export async function fetchOrders(authToken: string): Promise<Order[]> {
    const res = await fetch('/api/orders', {
        headers: {
            Authorization: `Basic ${authToken}`,
        },
    });

    if (!res.ok) {
        throw new Error('Failed to fetch orders');
    }

    return res.json();
}