import { api, Page } from "..";

export interface Product {
    id: number;
    name: string;
    description: string;
    targetMarket: string;
    technologies: {
        id: number;
        name: string;
    }[];
}

export interface SearchProducts {
    page?: number;
    size?: number;
    name?: string;
    technologies?: string[];
}

export const index = async ({ page = 0, size = 10, name, technologies = [] }: SearchProducts = {}) => {

    const params = new URLSearchParams([
        ['page', page.toString()],
        ['size', size.toString()]
    ]);

    if (name) {
        params.append('name', name);
    }

    if (technologies.length) {
        technologies.forEach(technology => params.append('technologies', technology))
    }

    return api.get<Page<Product>>('/products', {
        params
    });
}

const Products = {
    index
};

export default Products;