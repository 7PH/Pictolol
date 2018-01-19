import {Category} from './Category';
import {ImageComment} from './ImageComment';

export interface Image {
    id: number;
    title?: String;
    url: String;
    views?: number;
    category?: Category;
    comments?: ImageComment[];
}
