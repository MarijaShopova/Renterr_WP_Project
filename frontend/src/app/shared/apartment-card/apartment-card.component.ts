import {Component, EventEmitter, Input, Output} from '@angular/core';
import {LoginComponent} from '../../modal/login/login.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FavouritesService} from '../../service/favourites.service';
import {AuthenticationService} from '../../service/authentication.service';
import {ApartmentCard} from '../../model/apartment-card';

@Component({
  selector: 'app-apartment-card',
  templateUrl: './apartment-card.component.html',
  styleUrls: ['./apartment-card.component.css']
})
export class ApartmentCardComponent {

  @Input() apartment: ApartmentCard;
  @Input() currency: string;
  @Input() isFavourite = false;

  @Output() favouritesChanged = new EventEmitter();

  constructor(private modalService: NgbModal,
              private favouritesService: FavouritesService,
              private authenticationService: AuthenticationService) {
  }

  onHeartClick(event: MouseEvent) {
    event.preventDefault();
    event.stopPropagation();
    if (this.authenticationService.isUserLoggedIn()) {
      this.favouritesChanged.emit(this.apartment.id);
    } else {
      this.modalService.open(LoginComponent, {size: 'sm', centered: true}).result
        .then(() => this.favouritesChanged.emit(this.apartment.id));
    }
  }
}
