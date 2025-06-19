import type {OrderItem} from "./OrderItem.ts";

export type Order = {
    id: number;
    orderStatus: string;
    requestedAt: string;
    orderItems: OrderItem[];
};
