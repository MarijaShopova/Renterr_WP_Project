import {Component, OnInit} from '@angular/core';
import {Statistics} from '../model/statistics';
import {StatisticsService} from '../service/statistics.service';
import {FormControl, FormGroup} from '@angular/forms';
import {NgbCalendar, NgbDateParserFormatter, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';
import {AuthenticationService} from '../service/authentication.service';
import {LoginComponent} from '../modal/login/login.component';

@Component({
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  statistics: Statistics[] = [];
  filterForm: FormGroup;

  constructor(private router: Router,
              private statisticsService: StatisticsService,
              private formatter: NgbDateParserFormatter,
              private ngbCalendar: NgbCalendar,
              private authenticationService: AuthenticationService,
              private modal: NgbModal) {
  }

  ngOnInit() {
    this.statisticsService.getStatistics()
      .subscribe(data => this.statistics = data);
    this.filterForm = new FormGroup({
      city: new FormControl(),
      dateAvailable: new FormControl(),
      numTenants: new FormControl(),
    });
  }

  onSearch() {
    const dateAvailableValue = this.filterForm.controls.dateAvailable.value;
    const dateAvailable = dateAvailableValue ? this.formatter.format(dateAvailableValue) : null;
    const queryParams = {
      ...this.filterForm.value,
      dateAvailable,
      page: 0,
      sort: 'datePosted,desc',
      currency: 'EUR'
    };
    this.router.navigate(['/search'], {queryParams});
  }

  onAddNewApartment($event: MouseEvent) {
    $event.preventDefault();
    if (this.authenticationService.isUserLoggedIn()) {
      this.router.navigate(['/new']);
    } else {
      this.modal.open(LoginComponent, {size: 'sm', centered: true}).result
        .then(() => this.router.navigate(['/new']), () => null);
    }
  }
}
