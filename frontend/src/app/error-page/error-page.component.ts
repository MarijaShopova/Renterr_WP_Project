import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Errors} from '../model/constant/errors';
import {Location} from '@angular/common';

@Component({
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent implements OnInit {

  error: { [k: string]: any; };

  constructor(private route: ActivatedRoute,
              private router: Router,
              private location: Location) {
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.error = Errors[params.status];
      if (!this.error) {
        this.error = {status: 'Oops', message: 'Something went wrong', buttonText: 'Back to Renterr'};
      }
    });
  }

  onErrorButtonClicked() {
    if (this.error.status === 'Oops') {
      this.router.navigate(['/']);
    } else {
      this.location.back();
    }
  }
}
