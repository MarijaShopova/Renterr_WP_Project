import {Component, Input} from '@angular/core';
import {Account} from '../../model/account';

@Component({
  selector: 'app-owner-card',
  templateUrl: './owner-card.component.html',
  styleUrls: ['./owner-card.component.css']
})
export class OwnerCardComponent {

  @Input() owner: Account;
}
