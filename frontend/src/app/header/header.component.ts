import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';
import {LoginComponent} from '../modal/login/login.component';
import {AuthenticationService} from '../service/authentication.service';
import {Account} from '../model/account';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  user: Account;
  isCollapsed = true;
  mobile: boolean;

  constructor(private modalService: NgbModal,
              private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit() {
    this.mobile = window.innerWidth <= 768;
    this.authenticationService.user
      .subscribe(value => this.user = value ? value.account : null);
  }

  goToListNewApartment() {
    if (this.authenticationService.isUserLoggedIn()) {
      this.router.navigate(['/new']);
    } else {
      this.modalService.open(LoginComponent, {size: 'sm', centered: true}).result
        .then(() => this.router.navigate(['/new']));
    }
  }
}
