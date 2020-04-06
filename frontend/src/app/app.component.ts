import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {AuthenticationService} from './service/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  showHeaderFooter = true;

  constructor(private location: Location,
              private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    this.authenticationService.autoLogin();
    this.router.events.subscribe(() => this.updateHeaderFooter(this.location.path()));
  }

  updateHeaderFooter(val: string) {
    this.showHeaderFooter = !val.includes('signup')
      && !val.includes('login')
      && !val.includes('error');
  }
}
