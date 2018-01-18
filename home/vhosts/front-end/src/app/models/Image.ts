import {Category} from './Category';

export interface Image {
    id: number;
    title?: String;
    url: String;
    views?: number;
    category?: Category
}
