import axios from 'axios';

export const api = axios.create({
    baseURL: 'http://localhost:8080',
});

export interface Page<T> {
    content: T[];
    page: number;
    size: number;
    totalPages: number;
    totalElements: number;
}