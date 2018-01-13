import {Component, OnInit} from '@angular/core';
import {PictissouService} from '../../services/PictissouService';

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html'
})
export class AppHeaderComponent {

    constructor(public readonly pict: PictissouService) { }


}
