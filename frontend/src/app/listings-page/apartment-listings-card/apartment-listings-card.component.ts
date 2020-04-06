import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ApartmentService} from '../../service/apartment.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ApartmentCard} from '../../model/apartment-card';

@Component({
  selector: 'app-apartment-listings-card',
  templateUrl: './apartment-listings-card.component.html',
  styleUrls: ['./apartment-listings-card.component.css']
})
export class ApartmentListingsCardComponent {

  @Input() apartment: ApartmentCard;
  @Input() currency;
  @Input() mobile = false;

  @Output() delete = new EventEmitter();

  loading = false;

  constructor(private apartmentsService: ApartmentService,
              private modal: NgbModal) {
  }

  onEnableDisable() {
    this.loading = true;
    this.apartmentsService.enableApartment(this.apartment.id, !this.apartment.active)
      .subscribe(() => {
        this.loading = false;
        this.apartment.active = !this.apartment.active;
      }, () => this.loading = false);
  }

  getActive = () => this.apartment.active ? 'Active' : 'Inactive';

  openDeleteModal(content) {
    this.modal.open(content, {ariaLabelledBy: 'modal-basic-title', centered: true}).result
      .then(() => this.delete.emit(this.apartment.id), () => null);
  }
}
