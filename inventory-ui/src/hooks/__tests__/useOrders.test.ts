import { renderHook, waitFor } from '@testing-library/react';
import { setupServer } from 'msw/node';
import { http, HttpResponse } from 'msw';
import type { Order } from '../../types/Order.ts';
import '@testing-library/jest-dom';
import { useOrders } from '../useOrders.tsx';

const mockOrders: Order[] = [
    {
        id: 1,
        orderStatus: 'SUCCESS',
        requestedAt: '2025-06-18T14:00:00Z',
        orderItems: [
            { id: 1, productId: 1, quantityRequested: 2, productName: 'Test Product' }
        ],
    },
];

const server = setupServer(
    http.get('/api/orders', async ({ request }) => {
        const auth = request.headers.get('authorization');
        if (auth === 'Basic ' + btoa('admin:adminpass')) {
            return HttpResponse.json(mockOrders);
        }
        return new HttpResponse(null, { status: 401 });
    })
);

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

describe('useOrders', () => {
    it('fetches and sets orders on success', async () => {
        const { result } = renderHook(() => useOrders());

        await waitFor(() => {
            expect(result.current.loading).toBe(false);
        });

        expect(result.current.orders).toEqual(mockOrders);
        expect(result.current.error).toBeNull();
    });

    it('sets error on fetch failure', async () => {
        server.use(
            http.get('/api/orders', () => {
                return new HttpResponse(null, { status: 500 });
            })
        );

        const { result } = renderHook(() => useOrders());

        await waitFor(() => {
            expect(result.current.loading).toBe(false);
        });

        expect(result.current.orders).toEqual([]);
        expect(result.current.error).toBe('Unknown error');
    });
});