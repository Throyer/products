import { api, Page } from "..";

export interface Technology {
    id: number;
    name: string;
}

export interface SearchTechnologies {
    page?: number;
    size?: number;
    name?: string;
}

export const index = async ({ page = 0, size = 10, name }: SearchTechnologies = {}) => {

    const params = new URLSearchParams([
        ['page', page.toString()],
        ['size', size.toString()]
    ]);

    if (name) {
        params.append('name', name);
    }

    return api.get<Page<Technology>>('/technologies', {
        params
    });
}

const Technologies = {
    index
};

export default Technologies;