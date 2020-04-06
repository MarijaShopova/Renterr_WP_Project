import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbCalendar, NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {Municipalities} from '../model/constant/municipalities';
import {AbstractControl, FormGroup} from '@angular/forms';
import {ApartmentService} from '../service/apartment.service';
import {AuthenticationService} from '../service/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {NgxSpinnerService} from 'ngx-spinner';

@Component({
  templateUrl: './new-listing-page.component.html',
  styleUrls: ['./new-listing-page.component.css']
})
export class NewListingPageComponent implements OnInit {

  municipalityMap = Municipalities;
  apartmentForm: FormGroup;
  mainImage: File;
  mainImageName = 'Click to select an image';
  images: File[] = [];
  editMode: boolean;

  @ViewChild('title', {static: true}) title: ElementRef;

  constructor(private ngbCalendar: NgbCalendar,
              private service: ApartmentService,
              private formatter: NgbDateParserFormatter,
              private authService: AuthenticationService,
              private router: Router,
              private route: ActivatedRoute,
              private spinner: NgxSpinnerService) {
  }

  ngOnInit() {
    this.editMode = this.router.url !== '/new';
    this.apartmentForm = this.service.initialiseApartmentForm(this.editMode);
    this.title.nativeElement.focus();
    if (this.editMode) {
      this.spinner.show();
      this.mainImageName = 'Click to change or add picture';
      const id = +this.route.snapshot.paramMap.get('id');
      this.service.getApartment(id)
        .subscribe(apartment => {
          this.apartmentForm.patchValue(apartment);
          this.apartmentForm.get('owner').patchValue(apartment.owner.id);
          if (apartment.dateAvailable) {
            this.dateAvailable.setValue(this.formatter.parse(apartment.dateAvailable.toString()));
          }
          this.spinner.hide();
        }, err => {
          this.spinner.hide();
          this.router.navigate(['/error'], {queryParams: {status: err.status}, replaceUrl: true});
        });
    }
  }

  formTitle = () => this.editMode ? 'Edit Listing Information' : 'Add New Listing';

  get form(): { [p: string]: AbstractControl } {
    return this.apartmentForm.controls;
  }

  get dateAvailable() {
    return this.apartmentForm.controls.dateAvailable;
  }

  onSelectImages(images) {
    this.images.push(...images.addedFiles);
  }

  onRemoveImage(image) {
    this.images.splice(this.images.indexOf(image), 1);
  }

  onMainFileChanged(event) {
    if (event.target.files.length) {
      this.mainImage = event.target.files[0] as File;
      this.mainImageName = this.mainImage.name.substr(0, 26);
    }
  }

  onSubmit() {
    if (this.apartmentForm.valid) {
      this.spinner.show();
      this.dateAvailable.setValue(this.formatter.format(this.dateAvailable.value));
      const formData = new FormData();
      formData.append('apartment', JSON.stringify(this.apartmentForm.value));
      if (this.images.length > 0) {
        this.images.forEach(image => formData.append('images', image));
      }
      if (this.mainImage) {
        formData.append('mainImage', this.mainImage);
      }
      this.service.addOrUpdateApartment(this.editMode, formData)
        .subscribe(() => {
          this.spinner.hide();
          this.router.navigate(['/my-apartments']);
        }, err => {
          this.spinner.hide();
          this.router.navigate(['/error'], {queryParams: {status: err.status}, replaceUrl: true});
        });
    } else {
      Object.keys(this.apartmentForm.controls)
        .forEach(control => this.apartmentForm.get(control).markAsTouched());
    }
  }
}
