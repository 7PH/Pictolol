import { Component } from '@angular/core';
import {User} from './models/User';
import {PictissouService} from './pictissou.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public readonly title = 'Pictissou';

  constructor(public pict: PictissouService) { }
}
