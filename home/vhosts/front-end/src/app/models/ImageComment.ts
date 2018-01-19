import {Image} from './Image';

export interface ImageComment {
    id: number;
    comment: string;
    image?: Image;
}
