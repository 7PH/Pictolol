import {Parsable} from './parsable.model';

export class User implements Parsable<User> {
    id: number;
    pseudo: string;

    parse(obj: any): User {
        Object.assign(this, obj);
        // this.foo = new Foo().parse(obj.foo);
        return this;
    }
}
