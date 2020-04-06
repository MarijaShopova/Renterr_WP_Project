import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {Municipalities} from '../../model/constant/municipalities';
import {NgbCalendar, NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-filters-form',
  templateUrl: './filters-form.component.html',
  styleUrls: ['./filters-form.component.css']
})
export class FiltersFormComponent implements OnInit {

  @Input() formValues;

  @Output() filter = new EventEmitter();
  @Output() back = new EventEmitter();

  municipalityMap = Municipalities;
  filterForm: FormGroup;

  constructor(private ngbCalendar: NgbCalendar,
              private formatter: NgbDateParserFormatter) {
  }

  ngOnInit() {
    this.filterForm = this.initializeFilterForm();
    this.populateForm();
  }

  initializeFilterForm(): FormGroup {
    return new FormGroup({
      city: new FormControl(''),
      municipality: new FormControl(''),
      dateAvailable: new FormControl(),
      numBedrooms: new FormControl(''),
      numTenants: new FormControl(null, Validators.min(1)),
      minPrice: new FormControl(null, Validators.min(1)),
      maxPrice: new FormControl(),
      minArea: new FormControl(null, Validators.min(1)),
      maxArea: new FormControl(),
      heating: new FormControl(false),
      balcony: new FormControl(false),
      parking: new FormControl(false),
      elevator: new FormControl(false)
    }, [this.rangeValidator('Area', 'areaRange'),
      this.rangeValidator('Price', 'priceRange')]);
  }

  populateForm() {
    if (this.formValues.dateAvailable) {
      this.formValues.dateAvailable = this.formatter.parse(this.formValues.dateAvailable);
    }
    this.filterForm.patchValue(this.formValues);
  }

  get form(): { [p: string]: AbstractControl } {
    return this.filterForm.controls;
  }

  rangeValidator(field: string, error: string): ValidatorFn {
    return (formGroup: FormGroup) => {
      const min = formGroup.get('min' + field).value;
      const max = formGroup.get('max' + field).value;
      if (min == null || max == null) {
        return null;
      }
      return min < max ? null : {[error]: true};
    };
  }

  onFiltersChange() {
    if (this.filterForm.valid) {
      this.filter.emit(this.filterForm.value);
    }
  }

  clearFilters() {
    this.filterForm.reset({city: '', municipality: '', numBedrooms: ''});
    this.filter.emit({...this.filterForm.value, page: 0});
  }
}
