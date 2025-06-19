import {useEffect, useState} from 'react';
import type {Order} from "../types/Order.ts";
import type {HttpErrorResponse} from "../types/HttpErrorResponse.ts";
import {fetchOrders} from "../api";

function isHttpErrorResponse(err: unknown): err is HttpErrorResponse {
    return typeof err === 'object' && err !== null && 'message' in err;
}

export function useOrders() {
    const [orders, setOrders] = useState<Order[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchOrders(btoa('admin:adminpass'))
            .then(setOrders)
            .catch(err => {
                if (isHttpErrorResponse(err)) {
                    setError(err.message);
                }
                setError('Unknown error');
            })
            .finally(() => setLoading(false));
    }, []);

    return {orders, error, loading};
}